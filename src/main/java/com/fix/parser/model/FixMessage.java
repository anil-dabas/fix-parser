package com.fix.parser.model;

import java.util.HashMap;
import java.util.Map;

public class FixMessage {
    private final Map<String, String> fields;

    public FixMessage() {
        this.fields = new HashMap<>();
    }

    public void addField(String tag, String value) {
        fields.put(tag, value);
    }

    public String getField(String tag) {
        return fields.get(tag);
    }

    public Map<String, String> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return fields.toString();
    }
}

