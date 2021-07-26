package com.xyvo.defi.standalone;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude
public class ShutdownSignal {

    @JsonProperty(required = true, defaultValue = "")
    private String signal;

    public ShutdownSignal() {
    }

    @JsonCreator
    public ShutdownSignal(String signal) {
        this.signal = signal;
    }


    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }
}
