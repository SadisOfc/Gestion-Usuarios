package com.gestion.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.gestion.models.Action;
import com.gestion.models.Roles;
import com.gestion.models.User;

public class UsersList {
    List<User> usersList = new ArrayList<>();
    static ActionsList actionsList = new ActionsList();

    public User buscarUsuario(int id){
        if(!usersList.isEmpty()){
            User user = usersList.stream().filter(a -> a.getID()==id).findFirst().orElse(null);
            if(user != null)actionsList.crearRegistro(id, new Action("Se buscó al usuario " + user.getUsername(), LocalDateTime.now()));
            return user;
        }
        return null;
    }
    public User buscarUsuario(String username){
        if(!usersList.isEmpty()){
            User user = usersList.stream().filter(a -> a.getUsername().equals(username)).findFirst().orElse(null);
            if(user != null)actionsList.crearRegistro(user.getID(), new Action("Se buscó al usuario " + user.getUsername(), LocalDateTime.now()));
            return user;
        }
        return null;
    }
    public boolean crearUsuario(User usuario){
        User u = buscarUsuario(usuario.getUsername());
        if(u != null){
            System.out.println("Usuario existente");
            return false;
        }
            usersList.add(usuario);
            actionsList.crearRegistro(usuario.getID());
            actionsList.crearRegistro(usuario.getID(),new Action("Se creó el usuario " + usuario.getUsername(), LocalDateTime.now()));
            System.out.println("Usuario creado");
        return true;
    }

    public void eliminarUsuario(User user){
            actionsList.crearRegistro(user.getID(),new Action("Se eliminó el usuario " + user.getUsername(), LocalDateTime.now()));
            usersList.remove(user);
    }

    public void actualizarRol(User user, Roles newRole){
            user.setRole(newRole);
            actionsList.crearRegistro(user.getID(),new Action("Se actualizó el rol de" + user.getUsername(), LocalDateTime.now()));
    }

    public boolean actualizarContraseña(User user, String passwordVieja, String passwordNueva){
        boolean b=false;
        if(passwordVieja.equals(user.getPassword())){
         user.setPassword(passwordNueva);
         b = true;
         actionsList.crearRegistro(user.getID(),new Action("Se actualizó la contraseña del usuario " + user.getUsername(), LocalDateTime.now()));
        }
        return b;
    }
    public ActionsList getActionsList(){
        return actionsList;
    }
    public void actualizarNombre(User user, String nuevoNombre){
        user.setFullname(nuevoNombre);
        actionsList.crearRegistro(user.getID(),new Action("Se actualizó el nombre del usuario " + user.getUsername(), LocalDateTime.now()));
    }
}
