package com.gestion.services;

import com.gestion.models.Roles;
import com.gestion.models.User;
import com.gestion.repository.UsersList;

public class UserService {
    private UsersList usersList;
    public UserService(UsersList usersList){
        this.usersList = usersList;
    }

    public boolean crear(Roles roleActualy,String fullname, String USERNAME, String password, Roles role){
        User user = new User(fullname, USERNAME, password, role);
        switch (roleActualy){
            case OWNER, ADMIN:
                return usersList.crearUsuario(user);
            default:
                return false;
        }
    }
    public boolean eliminar(Roles roleActualy,User user){
        switch (roleActualy){
            case OWNER,ADMIN:
                usersList.eliminarUsuario(user);
                return true;
            default:
                    return false;
        }
    }
    public boolean actualizarRol(Roles roleActually, User user, Roles newRole){
        if(user.getRole()!=Roles.OWNER && (roleActually==Roles.ADMIN || roleActually==Roles.OWNER)){
            usersList.actualizarRol(user,newRole);
            return true;
        }
        return false;
    }

    public boolean actualizarContraseña(Roles roleActually,User user, String passwordVieja, String passwordNueva){
        switch (roleActually){
            case OWNER,ADMIN,USER:
                return usersList.actualizarContraseña(user, passwordVieja, passwordNueva);
            default:
                return false;
        }
    }

    public boolean actualizarNombre(Roles roleActualy,User user, String nuevoNombre){
        switch (roleActualy){
            case OWNER,ADMIN,USER:
                usersList.actualizarNombre(user, nuevoNombre);
                return true;
            default:
                return false;
        }
    }
}
