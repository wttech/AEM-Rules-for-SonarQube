/*-
 * #%L
 * AEM Rules for SonarQube
 * %%
 * Copyright (C) 2015-2018 Cognifide Limited
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
package com.cognifide.aemrules.htl.lex;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.List;
import org.sonar.plugins.html.node.Attribute;
import org.sonar.plugins.html.node.DirectiveNode;
import org.sonar.plugins.html.node.Node;

class DoctypeTokenizer extends AbstractTokenizer<List<Node>> {

    public DoctypeTokenizer(String startToken, String endToken) {
        super(startToken, endToken);
    }

    private static void parseToken(DirectiveNode node) {
        String code = node.getCode();
        try(Reader reader = new StringReader(code)) {
            StreamTokenizer tokenizer = new StreamTokenizer(reader);
            tokenizer.quoteChar('"');
            while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
                if (tokenizer.sval != null) {
                    if (node.getNodeName() == null) {
                        node.setNodeName(tokenizer.sval);
                    } else {
                        node.getAttributes().add(new Attribute(tokenizer.sval));
                    }
                }
            }
        } catch (IOException e) {
            // ignore
        }
    }

    @Override
    protected void addNode(List<Node> nodeList, Node node) {
        super.addNode(nodeList, node);
        parseToken((DirectiveNode) node);
    }

    @Override
    Node createNode() {
        return new DirectiveNode();
    }
}