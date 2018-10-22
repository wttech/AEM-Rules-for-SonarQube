/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015 Cognifide Limited
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.cognifide.aemrules.htl.visitors;

import com.cognifide.aemrules.extensions.RulesLoader;
import com.google.common.collect.Lists;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.sling.scripting.sightly.compiler.SightlyCompilerException;
import org.apache.sling.scripting.sightly.compiler.expression.Expression;
import org.apache.sling.scripting.sightly.impl.compiler.Syntax;
import org.apache.sling.scripting.sightly.impl.compiler.frontend.ExpressionParser;
import org.apache.sling.scripting.sightly.impl.compiler.frontend.Fragment;
import org.apache.sling.scripting.sightly.impl.compiler.frontend.Interpolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.html.node.Attribute;
import org.sonar.plugins.html.node.CommentNode;
import org.sonar.plugins.html.node.DirectiveNode;
import org.sonar.plugins.html.node.ExpressionNode;
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.node.TagNode;
import org.sonar.plugins.html.node.TextNode;
import org.sonar.plugins.html.visitor.CharsetAwareVisitor;
import org.sonar.plugins.html.visitor.HtmlSourceCode;

public class HtlScanner {

	private static final Logger LOG = LoggerFactory.getLogger(RulesLoader.class);

	private static final ExpressionParser expressionParser = new ExpressionParser();

	private final List<DefaultNodeVisitor> metricVisitors;
	private final List<DefaultNodeVisitor> checkVisitors = Lists.newArrayList();

	public HtlScanner() {
		this(Collections.emptyList());
	}

	public HtlScanner(List<DefaultNodeVisitor> metricVisitors) {
		this.metricVisitors = metricVisitors;
	}

	private static void scanElementTag(DefaultNodeVisitor visitor, TagNode node) {
		if (!node.isEndElement()) {
			visitor.startElement(node);
			if (isHtlTag(node)) {
				List<Expression> expressions =
						node.getAttributes().stream()
								.map(Attribute::getValue)
								.filter(HtlScanner::hasHtlExpression)
								.flatMap(HtlScanner::getExpressions)
								.peek(expression -> visitor.htlExpression(expression, node))
								.collect(Collectors.toList());
				visitor.startHtlElement(expressions, node);
			}
		}
		if (node.isEndElement() || node.hasEnd()) {
			visitor.endElement(node);
		}
	}

	private static boolean isHtlTag(TagNode node) {
		boolean hasDataSlyAttribute = node.getAttributes().stream()
				.map(Attribute::getName)
				.anyMatch(Syntax::isPluginAttribute);
		boolean isSlyTag = "sly".equalsIgnoreCase(node.getNodeName());
		return isSlyTag || hasDataSlyAttribute;
	}

	private static Stream<Expression> getExpressions(String code) {

		Stream<Expression> expressionStream = Stream.empty();

		Interpolation interpolation = null;
		try {
			interpolation = expressionParser.parseInterpolation(code);
			expressionStream = StreamSupport
					.stream(interpolation.getFragments().spliterator(), false)
					.filter(Fragment::isExpression)
					.map(Fragment::getExpression);
		} catch (SightlyCompilerException ex) {
			LOG.error("Could not parse expression", ex);
		}
		return expressionStream;
	}

	private static boolean hasHtlExpression(String code) {
		return getExpressions(code)
				.findAny()
				.isPresent();
	}

	/**
	 * Add a visitor to the list of visitors.
	 */
	public void addVisitor(DefaultNodeVisitor visitor) {
		checkVisitors.add(visitor);
		visitor.init();
	}

	/**
	 * Scan a list of Nodes and send events to the visitors.
	 */
	public void scan(List<Node> nodeList, HtmlSourceCode htmlSourceCode, Charset charset) {
		scan(nodeList, htmlSourceCode, charset, metricVisitors);
		scan(nodeList, htmlSourceCode, charset, checkVisitors);
	}

	private void scan(List<Node> nodeList, HtmlSourceCode htmlSourceCode, Charset charset,
			List<DefaultNodeVisitor> visitors) {
		// prepare the visitors
		for (DefaultNodeVisitor visitor : visitors) {
			visitor.setSourceCode(htmlSourceCode);

			if (visitor instanceof CharsetAwareVisitor) {
				((CharsetAwareVisitor) visitor).setCharset(charset);
			}
		}

		// notify visitors for a new document
		for (DefaultNodeVisitor visitor : visitors) {
			visitor.startDocument(nodeList);
		}

		// notify the visitors for start and end of element
		for (Node node : nodeList) {
			for (DefaultNodeVisitor visitor : visitors) {
				scanElement(visitor, node);
			}
		}

		// notify visitors for end of document
		for (DefaultNodeVisitor visitor : visitors) {
			visitor.endDocument();
		}
	}

	/**
	 * Scan a single element and send appropriate event: start element, end element, characters,
	 * comment, expression or directive.
	 */
	private void scanElement(DefaultNodeVisitor visitor, Node node) {
		switch (node.getNodeType()) {
			case TAG:
				scanElementTag(visitor, (TagNode) node);
				break;
			case TEXT:
				visitor.characters((TextNode) node);
				break;
			case COMMENT:
				visitor.comment((CommentNode) node);
				if (Syntax.isSightlyComment(node.getCode())) {
					visitor.htlComment((CommentNode) node);
				}
				break;
			case EXPRESSION:
				visitor.expression((ExpressionNode) node);
				if (hasHtlExpression(node.getCode())) {
					getExpressions(node.getCode())
							.forEach(expression -> visitor.htlExpression(expression, node));
				}
				break;
			case DIRECTIVE:
				visitor.directive((DirectiveNode) node);
				break;
			default:
				break;
		}
	}

}
