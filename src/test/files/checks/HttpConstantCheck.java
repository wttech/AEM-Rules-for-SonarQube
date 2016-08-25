package com.example;

public class HttpConstantCheck {

    private String httpLiteral = "http"; // Noncompliant {{Using http literal hardcoded makes it difficult to switch to https later on.}}

    private String httpLiteralUppercase = "HTTP"; // Noncompliant {{Using http literal hardcoded makes it difficult to switch to https later on.}}

    private String otherLiteral = "other";

    protected void protectedMethod(String test) {
        String httpProtocol = "http"; // Noncompliant
    }
}
