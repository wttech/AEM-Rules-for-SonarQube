Injector can be closed using try-with-resources Java 7 feature.
== Example
``
try (InjectorWithContext injector = InjectorUtil.getInjector(INJECTOR_NAME, request)) {
    // do stuff
}
``
