package com.gestion.models;

public class User {
    private static int contador=1;
    private String fullname;
    private int ID;
    private final String username;
    private String password;
    private Roles role;

    public User(String fullname, String username, String password, Roles role) {
        this.fullname = fullname;
        this.ID = contador;
        this.username = username;
        this.password = password;
        this.role = role;
        contador++;
    }

    public String getFullname() {
        return fullname;
    }
    public int getID() {
        return ID;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public Roles getRole() {
        return role;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public String toString(){
        return "Id:" + ID + " - Nombre: " + fullname + " - Username: " + username + " - Rol: " + role.name();
    }
}
