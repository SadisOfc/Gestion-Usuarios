package com.gestion.services;
import com.gestion.models.User;
import com.gestion.repository.UsersList;

public class AuthService {
    private UsersList usersList;
    public AuthService(UsersList usersList){
        this.usersList = usersList;
    }
    public boolean login(String username, String password){
        User user = usersList.searchUser(username);
        return user != null && user.getPassword().equals(password);
    }
    public boolean logout(){
        return false;
    }
}
