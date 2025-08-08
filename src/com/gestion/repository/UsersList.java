package com.gestion.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.gestion.models.Action;
import com.gestion.models.Roles;
import com.gestion.models.User;
import com.gestion.services.AuthService;

public class UsersList {
    List<User> usersList = new ArrayList<>();
    static ActionsList actionsList = new ActionsList();

    public User searchUser(int id){
        if(!usersList.isEmpty()){
            User user = usersList.stream().filter(a -> a.getID()==id).findFirst().orElse(null);
            if(user != null)actionsList.createRegister(id, new Action(user,"Se buscó al usuario " + user.getUsername(), LocalDateTime.now()));
            return user;
        }
        return null;
    }

    public User searchUser(String username){
        if(!usersList.isEmpty()){
            User user = usersList.stream().filter(a -> a.getUsername().equals(username)).findFirst().orElse(null);
            if(user != null)actionsList.createRegister(user.getID(), new Action(user,"Se buscó al usuario " + user.getUsername(), LocalDateTime.now()));
            return user;
        }
        return null;
    }

    public boolean createUser(User user){
        User u = searchUser(user.getUsername());
        if(u == null){

            usersList.add(user);
            actionsList.createRegister(user.getID());
            actionsList.createRegister(user.getID(),new Action(user,"Se creó el usuario " + user.getUsername(), LocalDateTime.now()));
            return true;
        }
        return false;
    }

    public void removeUser(User user){
            actionsList.createRegister(user.getID(),new Action(user,"Se eliminó el usuario " + user.getUsername(), LocalDateTime.now()));
            usersList.remove(user);
    }

    public void updateRole(User user, Roles newRole){
            user.setRole(newRole);
            actionsList.createRegister(user.getID(),new Action(user,"Se actualizó el rol de" + user.getUsername(), LocalDateTime.now()));
    }

    public boolean updatePassword(User user, String oldPassword, String newPassword){
        if(oldPassword.equals(user.getPassword())){
         user.setPassword(newPassword);
         actionsList.createRegister(user.getID(),new Action(user,"Se actualizó la contraseña del usuario " + user.getUsername(), LocalDateTime.now()));
         return true;
        }
        return false;
    }

    public ActionsList getActionsList(){
        return actionsList;
    }

    public void updateName(User user, String newName){
        user.setFullname(newName);
        actionsList.createRegister(user.getID(),new Action(user,"Se actualizó el nombre del usuario " + user.getUsername(), LocalDateTime.now()));
    }

    public void blockUser(User user){
       actionsList.createRegister(user.getID(), new Action(user,"Se bloqueó al usuario " + user.getUsername() + " por fallar el inicio de sesión 3 veces", LocalDateTime.now()));
    }
}
