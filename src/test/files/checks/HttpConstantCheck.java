package com.example;

public class HttpConstantCheck {

    private String httpLiteral = "aaa"; // Noncompliant {{Using http literal hardcoded makes it difficult to switch to https later on.}}

    protected void protectedMethod(String test) {
        String httpProtocol = "http"; // Noncompliant
    }
}
