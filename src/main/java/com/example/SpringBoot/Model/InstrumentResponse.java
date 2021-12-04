package com.example.SpringBoot.Model;

public class InstrumentResponse {
    private InstrumentDescription data;
    private String type;

    public InstrumentDescription getData() {
        return data;
    }

    public void setData(InstrumentDescription data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
