package com.petro.scope104.presentation.select;

import java.io.Serializable;

public class SelectItem implements Serializable {
    private final String title;
    private boolean value;
    private final Serializable object;

    public SelectItem(String title, boolean value, Serializable object) {
        this.title = title;
        this.value = value;
        this.object = object;
    }

    public String getTitle() {
        return title;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public Serializable getObject() {
        return object;
    }
}
