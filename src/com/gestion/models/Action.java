package com.gestion.models;

import java.time.LocalDateTime;

public class Action {
    private  User user;
    private final String action;
    private final LocalDateTime localDateTime;
    public Action(User user,String action, LocalDateTime localDateTime){
        this.user = user;
        this.action = action;
        this.localDateTime = localDateTime;
    }
    public User getUser(){
        return user;
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
