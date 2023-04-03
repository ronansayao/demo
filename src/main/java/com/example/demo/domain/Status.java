package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {
    @JsonProperty("disponivel")
    AVAILABLE("disponivel"),
    @JsonProperty("adotado")
    ADOPTED("adotado");

    private String text;

    Status(String text) {
        this.text = text;
    }
    @JsonValue
    public String getText() {
        return this.text;
    }

    @JsonCreator
    public static Status fromString(String text) {
        for (Status status : Status.values()) {
            if (status.text.equalsIgnoreCase(text)) {
                return status;
            }
        }
        return null;
    }

}
