package com.petro.scope104.ui.select;

import java.io.Serializable;

public class SelectItem implements Serializable {
    private final String title;
    private boolean value;

    public SelectItem(String title, boolean value) {
        this.title = title;
        this.value = value;
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
}
