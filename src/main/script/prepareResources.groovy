

def engine = new groovy.text.SimpleTemplateEngine();
def template = engine.createTemplate('\t\texistingValues.put("${key}", "${value}");\n')

def classes = [ com.day.cq.commons.jcr.JcrConstants.class,
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
        clazz.getDeclaredFields().eachWithIndex{ it, i ->
            it.
            result += template.make(['key': it.get(String.class), 'value': clazz.toString() + '.' + it.getName()]);
        }
    }
    result += "\t\t// generated code ends here"
}

//processFileInplace(constantCheckerFile) { text ->
//    text.replaceAll(/(?s)\/\/ generated code starts here.*\/\/ generated code ends here/, getResult(template, classes))
//}


