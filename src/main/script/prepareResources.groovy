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


def engine = new groovy.text.SimpleTemplateEngine();
def template = engine.createTemplate('\t\texistingValues.put("${key}", "${value}");\n')

def classes = [com.day.cq.commons.jcr.JcrConstants.class,
               com.day.cq.dam.api.DamConstants.class,
               com.day.cq.wcm.api.NameConstants.class,
               com.day.cq.wcm.webservicesupport.ConfigurationConstants.class,
               org.osgi.framework.Constants.class,
               org.osgi.service.event.EventConstants.class,
               org.apache.sling.api.SlingConstants.class,
               org.apache.sling.api.servlets.HttpConstants.class,
               org.apache.sling.jcr.resource.JcrResourceConstants.class,
               org.apache.sling.servlets.resolver.internal.ServletResolverConstants.class,
               org.apache.sling.engine.EngineConstants.class
];

def constantCheckerPath = project.properties['constantCheckerPath']
def constantCheckerFile = new File(constantCheckerPath);


def processFileInplace(file, Closure processText) {
    def text = file.text
    file.write(processText(text))
}

def getResult(template, classes) {
    def result = "// generated code starts here\n"
    classes.each { clazz ->
        result += "\t\t// " + clazz.toString() + "\n";
        clazz.getDeclaredFields().eachWithIndex { it, i ->
            it.
                    result += template.make(['key': it.get(String.class), 'value': clazz.toString() + '.' + it.getName()]);
        }
    }
    result += "\t\t// generated code ends here"
}

//processFileInplace(constantCheckerFile) { text ->
//    text.replaceAll(/(?s)\/\/ generated code starts here.*\/\/ generated code ends here/, getResult(template, classes))
//}


