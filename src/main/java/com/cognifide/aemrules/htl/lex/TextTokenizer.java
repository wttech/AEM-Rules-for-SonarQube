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

import java.util.List;
import org.sonar.channel.CodeReader;
import org.sonar.channel.EndMatcher;
import org.sonar.plugins.html.node.Node;
import org.sonar.plugins.html.node.NodeType;
import org.sonar.plugins.html.node.TagNode;
import org.sonar.plugins.html.node.TextNode;

class TextTokenizer extends AbstractTokenizer<List<Node>> {

    public TextTokenizer() {
        super("", "");
    }

    private static boolean inScript(List<Node> nodeList) {
        if (!nodeList.isEmpty()) {
            Node node = nodeList.get(nodeList.size() - 1);
            if (node.getNodeType() == NodeType.TAG) {
                TagNode tag = (TagNode) node;
                return !tag.isEndElement() && "script".equalsIgnoreCase(tag.getNodeName());
            }
        }
        return false;
    }

    @Override
    public boolean consume(CodeReader codeReader, List<Node> nodeList) {
        Node node = createNode();

        setStartPosition(codeReader, node);

        StringBuilder stringBuilder = new StringBuilder();
        if (inScript(nodeList)) {
            codeReader.popTo(new EndScriptMatcher(codeReader), stringBuilder);
        } else {
            codeReader.popTo(new EndTokenMatcher(codeReader), stringBuilder);
        }
        node.setCode(stringBuilder.toString());
        setEndPosition(codeReader, node);

        nodeList.add(node);

        return true;
    }

    @Override
    Node createNode() {
        return new TextNode();
    }

    private static final class EndTokenMatcher implements EndMatcher {

        private final CodeReader codeReader;

        private EndTokenMatcher(CodeReader codeReader) {
            this.codeReader = codeReader;
        }

        @Override
        public boolean match(int endFlag) {
            boolean match = false;
            if ('<' == endFlag || equalsIgnoreCase(codeReader.peek("${".length()), "${".toCharArray())) {
                match = true;
            }

            return match;
        }
    }

    /**
     * Checks for the end of a script block
     */
    private static class EndScriptMatcher implements EndMatcher {

        private static final String END_SCRIPT = "</script>";
        private final CodeReader codeReader;

        public EndScriptMatcher(CodeReader codeReader) {
            this.codeReader = codeReader;
        }

        @Override
        public boolean match(int endFlag) {

            // return true on end of file
            if (endFlag == (char) -1) {
                return true;
            }

            // check for end script
            return (char) endFlag == '<' && END_SCRIPT.equalsIgnoreCase(new String(codeReader.peek(END_SCRIPT.length())));
        }
    }

}