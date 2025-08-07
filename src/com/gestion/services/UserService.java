package com.gestion.services;

import com.gestion.models.Roles;
import com.gestion.models.User;
import com.gestion.repository.UsersList;

public class UserService {
    private UsersList usersList;
    public UserService(UsersList usersList){
        this.usersList = usersList;
    }

    public boolean create(Roles currentRole, String fullname, String username, String password, Roles role){
        User user = new User(fullname, username, password, role);
        switch (currentRole){
            case OWNER, ADMIN:
                return usersList.createUser(user);
            default:
                return false;
        }
    }
    public boolean remove(Roles roleActualy, User user){
        switch (roleActualy){
            case OWNER,ADMIN:
                usersList.removeUser(user);
                return true;
            default:
                return false;
        }
    }
    public boolean updateRol(Roles roleActually, User user, Roles newRole){
        if(user.getRole()!=Roles.OWNER && (roleActually==Roles.ADMIN || roleActually==Roles.OWNER)){
            usersList.updateRole(user,newRole);
            return true;
        }
        return false;
    }

    public boolean updateRol(Roles roleActually, User user, String oldPassword, String newPassword){
        switch (roleActually){
            case OWNER,ADMIN,USER:
                return usersList.updatePassword(user, oldPassword, newPassword);
            default:
                return false;
        }
    }

    public boolean updateName(Roles roleActualy, User user, String newName){
        switch (roleActualy){
            case OWNER,ADMIN,USER:
                usersList.updateName(user, newName);
                return true;
            default:
                return false;
        }
    }
}
