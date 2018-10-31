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

import com.google.common.collect.ImmutableSet;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.sonar.channel.CodeReader;
import org.sonar.channel.EndMatcher;
import org.sonar.plugins.html.node.Attribute;
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.node.TagNode;

class ElementTokenizer extends AbstractTokenizer<List<Node>> {

    private static EndQNameMatcher endQNameMatcher = new EndQNameMatcher();

    private static EndTokenMatcher endTokenMatcher = new EndTokenMatcher();

    private static EndUnquotedAttributeMatcher endUnquotedAttributeMatcher = new EndUnquotedAttributeMatcher();

    public ElementTokenizer(String startToken, String endToken) {
        super(startToken, endToken);
    }

    private static void nestedTag(TagNode element, CodeReader codeReader, ParseMode mode) {
        // found a nested tag
        if (mode == ParseMode.BEFORE_ATTRIBUTE_NAME) {
            parseNestedTag(codeReader, element);
        } else {
            codeReader.pop();
        }
    }

    /**
     * Parse a nested tag with HtlLexer. The nested tag is added as an attribute to its parent element.
     */
    private static void parseNestedTag(CodeReader codeReader, TagNode element) {
        HtlLexer nestedPageLexer = new HtlLexer();
        List<Node> nodeList = nestedPageLexer.nestedParse(codeReader);

        // add the nested tags as attribute.
        for (Node node : nodeList) {
            element.getAttributes().add(new Attribute(node.getCode()));
        }
    }

    private static void handleBeforeAttributeValue(CodeReader codeReader, TagNode element) {
        Attribute attribute;
        if (!element.getAttributes().isEmpty()) {
            attribute = element.getAttributes().get(element.getAttributes().size() - 1);
            StringBuilder sbValue = new StringBuilder();
            int ch = codeReader.peek();

            if (isQuote((char) ch)) {
                codeReader.pop();
                if (codeReader.peek() != ch) {
                    codeReader.popTo(new QuoteMatcher((char) ch), sbValue);
                    attribute.setValue(unescapeQuotes(sbValue.toString(), (char) ch));
                }
                codeReader.pop();
                attribute.setQuoteChar((char) ch);
            } else {
                codeReader.popTo(endUnquotedAttributeMatcher, sbValue);
                attribute.setValue(sbValue.toString().trim());
            }
        }
    }

    private static void handleBeforeAttributeName(CodeReader codeReader, TagNode element) {
        Attribute attribute;
        StringBuilder sbQName = new StringBuilder();
        codeReader.popTo(endQNameMatcher, sbQName);
        attribute = new Attribute(sbQName.toString().trim());
        attribute.setLine(codeReader.getLinePosition() + element.getStartLinePosition() - 1);
        element.getAttributes().add(attribute);
    }

    private static void handleBeforeNodeName(CodeReader codeReader, TagNode element) {
        StringBuilder sbNodeName = new StringBuilder();
        codeReader.popTo(endTokenMatcher, sbNodeName);
        element.setNodeName(sbNodeName.toString());
    }

    /**
     * Unescape the quotes from the attribute value.
     */
    private static String unescapeQuotes(String value, char ch) {
        return StringUtils.replace(value, "\\" + ch, Character.toString(ch));
    }

    private static boolean isQuote(char c) {
        return c == '\'' || c == '"';
    }

    @Override
    protected void addNode(List<Node> nodeList, Node node) {
        super.addNode(nodeList, node);
        parseToken(node);
    }

    @Override
    Node createNode() {
        return new TagNode();
    }

    private void parseToken(Node node) {
        TagNode element = (TagNode) node;

        CodeReader codeReader = new CodeReader(node.getCode());

        ParseMode mode = ParseMode.BEFORE_NODE_NAME;
        for (int ch = codeReader.peek(); ch != -1; ch = codeReader.peek()) {

            // handle white space
            if (Character.isWhitespace(ch)) {
                codeReader.pop();
                continue;
            }

            // handle special characters
            switch (ch) {
                case '=':
                    mode = ParseMode.BEFORE_ATTRIBUTE_VALUE;
                    codeReader.pop();
                    continue;
                case '<':
                    nestedTag(element, codeReader, mode);
                    continue;
                case '>':
                case '/':
                case '%':
                case '@':
                    codeReader.pop();
                    continue;
                default:
                    break;
            }

            mode = parseToken(mode, codeReader, element);
        }
    }

    private ParseMode parseToken(ParseMode mode, CodeReader codeReader, TagNode element) {
        ParseMode result = ParseMode.BEFORE_NODE_NAME;
        switch (mode) {
            case BEFORE_NODE_NAME:
                handleBeforeNodeName(codeReader, element);
                result = ParseMode.BEFORE_ATTRIBUTE_NAME;
                break;
            case BEFORE_ATTRIBUTE_NAME:
                handleBeforeAttributeName(codeReader, element);
                result = ParseMode.BEFORE_ATTRIBUTE_NAME;
                break;
            case BEFORE_ATTRIBUTE_VALUE:
                handleBeforeAttributeValue(codeReader, element);
                result = ParseMode.BEFORE_ATTRIBUTE_NAME;
                break;
            default:
                break;
        }
        return result;
    }

    private enum ParseMode {
        BEFORE_ATTRIBUTE_NAME, BEFORE_ATTRIBUTE_VALUE, BEFORE_NODE_NAME
    }

    private static final class EndQNameMatcher implements EndMatcher {

        @Override
        public boolean match(int character) {
            return character == '=' || character == '>' || Character.isWhitespace(character);
        }
    }

    private static final class EndUnquotedAttributeMatcher implements EndMatcher {

        private static final Set<Character> FORBIDDEN = ImmutableSet.of(
            '"',
            '\'',
            '=',
            '<',
            '>',
            '`'
        );

        @Override
        public boolean match(int character) {
            return Character.isWhitespace(character) || FORBIDDEN.contains((char) character);
        }
    }

    private static final class EndTokenMatcher implements EndMatcher {

        @Override
        public boolean match(int character) {
            switch (character) {
                case '/':
                case '>':
                    return true;
                default:
                    break;
            }
            return Character.isWhitespace(character);
        }
    }

    private static final class QuoteMatcher implements EndMatcher {

        private static final char SINGLE_QUOTE = '\'';
        private static final char DOUBLE_QUOTE = '"';
        private final Deque<Character> startChars = new ArrayDeque<>();
        private int previousChar;

        QuoteMatcher(char startChar) {
            this.startChars.addFirst(startChar);
        }

        @Override
        public boolean match(int character) {
            boolean result = false;
            if ((character == SINGLE_QUOTE || character == DOUBLE_QUOTE) && previousChar != '\\') {
                if (startChars.peekFirst() == (char) character) {
                    startChars.removeFirst();
                } else {
                    startChars.addFirst((char) character);
                }
                result = startChars.isEmpty();
            }
            previousChar = character;
            return result;
        }
    }

}