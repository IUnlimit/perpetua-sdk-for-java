package com.illtamer.perpetua.sdk.handler;

public abstract class AbstractAPIHandler<T> implements APIHandler<T> {

    private transient final String endpoint;

    public AbstractAPIHandler(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

}
