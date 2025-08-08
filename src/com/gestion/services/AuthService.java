package com.gestion.services;
import com.gestion.models.User;
import com.gestion.repository.UsersList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthService {
    List<User> blockedUsers = new ArrayList<>();
    Map<User,Integer> failureCount = new HashMap<>();
    private UsersList usersList;

    public AuthService(UsersList usersList){
        this.usersList = usersList;
    }

    public Map<User,Integer> getFailureCount(){
        return failureCount;
    }
    public void setFailureCount(User user,int count){
        failureCount.put(user,count);
    }

    public boolean login(String username, String password){
        User user = usersList.searchUser(username);
        if(blockedUsers.contains(user)) return false;
        return user != null && user.getPassword().equals(password);
    }

    public List<User> getBlockedList(){
        return blockedUsers;
    }

    public void blockUser(User user){
        blockedUsers.add(user);
        usersList.blockUser(user);
    }

    public boolean logout(){
        return false;
    }
}
