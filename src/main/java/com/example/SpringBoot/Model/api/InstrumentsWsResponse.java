package com.example.SpringBoot.Model.api;

import com.example.SpringBoot.Model.enums.InstrumentsEventType;

public class InstrumentsWsResponse {
    private InstrumentsDataResponse data;
    private InstrumentsEventType type;

    public InstrumentsDataResponse getData() {
        return data;
    }

    public void setData(InstrumentsDataResponse data) {
        this.data = data;
    }

    public InstrumentsEventType getType() {
        return type;
    }

    public void setType(InstrumentsEventType type) {
        this.type = type;
    }
}
