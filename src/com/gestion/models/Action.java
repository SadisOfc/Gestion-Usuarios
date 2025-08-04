package com.gestion.models;

import java.time.LocalDateTime;

public class Action {
    private final String action;
    private final LocalDateTime localDateTime;
    public Action(String action, LocalDateTime localDateTime){
        this.action = action;
        this.localDateTime = localDateTime;
    }
    public String getAction() {
        return action;
    }
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public String toString(){
        return action + " - " + localDateTime;
    }
}
