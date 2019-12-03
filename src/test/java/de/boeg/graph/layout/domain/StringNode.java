package de.boeg.graph.layout.domain;

import java.util.UUID;

public class StringNode extends Node<String> {

    private final String id;

    public String getIdentifier() {
        return id;
    }

    public StringNode() {
        super();
        id = UUID.randomUUID().toString();
    }
}
