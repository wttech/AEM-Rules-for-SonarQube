Injectors are created in the context of either request or resource resolver.
To restore its initial state after using injector, it should be closed in finally block or created as a resource within try block.
== Noncompliant Code Example
``
InjectorWithContext injector = InjectorUtil.getInjector(INJECTOR_NAME, resourceResolver); // Noncompliant; injector should be closed
return injector.getInstance(ModelProvider.class);
``
``
try {
    ModelProvider modelProvider = injector.getInstance(ModelProvider.class); // Noncompliant; injector should be closed
} catch (Exception e) {
    // ...
}
``
``
try {
    ModelProvider modelProvider = injector.getInstance(ModelProvider.class); // Noncompliant; injector should be closed in finally block
    injector.close();
} catch (Exception e) {
    // ...
}
``
== Compliant Solution
``
// Java 6
InjectorWithContext injector = InjectorUtil.getInjector(INJECTOR_NAME, request)
try {
    ModelProvider modelProvider = injector.getInstance(ModelProvider.class);
    SimpleModel simpleModel = modelProvider.get(SimpleModel.class, resource);
    // ...
} finally {
    injector.close();
}
``
``
// Java 7
try (InjectorWithContext injector = InjectorUtil.getInjector(INJECTOR_NAME, request)) {
    ModelProvider modelProvider = injector.getInstance(ModelProvider.class);
    SimpleModel simpleModel = modelProvider.get(SimpleModel.class, resource);
    // ...
}
``