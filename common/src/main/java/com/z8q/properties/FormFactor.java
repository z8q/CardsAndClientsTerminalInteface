package com.z8q.properties;

public enum FormFactor {
    REAL(1),
    VIRTUAL(2);

    int code;

    FormFactor(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }


}
