package com.petro.scope104.ui.list;

public enum Gender {
    MALE("male"), FEMALE("female"), UNKNOWN(null, "unknown");

    final String serverName;
    final String displayName;

    Gender(String serverName, String displayName){
        this.serverName = serverName;
        this.displayName = displayName;
    }

    Gender(String universalName){
        this.serverName = universalName;
        this.displayName = universalName;
    }
}
