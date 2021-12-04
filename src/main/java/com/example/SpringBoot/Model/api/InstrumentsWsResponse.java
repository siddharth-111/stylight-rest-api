package com.example.SpringBoot.Model.api;

import com.example.SpringBoot.Model.enums.InstrumentsEventType;

public class InstrumentsWsResponse {
    private InstrumentsDataResponse instrumentsDataResponse;
    private InstrumentsEventType type;

    public InstrumentsDataResponse getInstrumentsDataResponse() {
        return instrumentsDataResponse;
    }

    public void setInstrumentsDataResponse(InstrumentsDataResponse instrumentsDataResponse) {
        this.instrumentsDataResponse = instrumentsDataResponse;
    }

    public InstrumentsEventType getType() {
        return type;
    }

    public void setType(InstrumentsEventType type) {
        this.type = type;
    }
}
