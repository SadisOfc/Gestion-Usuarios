package com.gestion.models;

public enum Roles {
    OWNER(0),ADMIN(1),USER(2),GUEST(3);
    private final int priority;
    Roles(int priority){
        this.priority = priority;
    }
    public int getPriority(){
        return priority;
    }
    public static Roles getRol(int priority){
        switch(priority){
            case 1:
                return ADMIN;
            case 2:
                return USER;
            case 3:
                return GUEST;
            default:
                return null;
        }
    }
}
