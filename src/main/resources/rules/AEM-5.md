Injector can be closed using try-with-resources Java 7 feature.
== Compliant Solution
``
try (InjectorWithContext injector = InjectorUtil.getInjector(INJECTOR_NAME, request)) {
    // ...
}
``
