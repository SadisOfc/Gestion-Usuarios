package com.gestion.app;

import com.gestion.models.Roles;
import com.gestion.models.User;
import com.gestion.repository.UsersList;
import com.gestion.utils.Actions;
import com.gestion.services.AuthService;
import com.gestion.services.UserService;

import com.gestion.utils.Permissions;
import java.util.Scanner;
import java.util.Set;

public class App {
    public static void main(String[] args){
        UsersList usersList = new UsersList();
        AuthService authService = new AuthService(usersList);
        Scanner l = new Scanner(System.in);
        boolean login = false;
        User user;
        String nombre,u_name,pass;
        int id,rol;
        UserService userService = new UserService(usersList);

        User owner = new User("Juan Dueñas","Sadisxz","contraseña123", Roles.OWNER);
        usersList.createUser(owner);
        while(true){
            System.out.println("-- Inicio de Sesión --");
            System.out.println("Ingrese su Usuario");
            String username = l.nextLine();
            System.out.println("Ingrese su contraseña");
            String contraseña = l.nextLine();
            login = authService.login(username,contraseña);
            User activeUser = usersList.searchUser(username);
            if(login){
                Set<Actions> actionsUser = Permissions.accionesRoles.get(activeUser.getRole());
                while(login){
                    System.out.println("-- Menú --");
                    if(activeUser.getRole()==Roles.GUEST) System.out.println("No tiene permisos para ver el menú");
                    System.out.println("0. Cerrar Sesión");
                    if(actionsUser.contains(Actions.CREAR_USUARIO)) System.out.println("1. Crear Usuario");
                    if(actionsUser.contains(Actions.ELIMINAR_USUARIO)) System.out.println("2. Eliminar Usuario");
                    if(actionsUser.contains(Actions.MODIFICAR_ROL)) System.out.println("3. Modificar Rol Usuario");
                    if(actionsUser.contains(Actions.BUSCAR_USUARIO)) System.out.println("4. Buscar Usuario");
                    if(actionsUser.contains(Actions.IMPRIMIR_HISTORIA)) System.out.println("5. Imprimir Historia Usuario");
                    if(actionsUser.contains(Actions.ACTUALIZAR_NOMBRE)) System.out.println("6. Actualizar Nombre");
                    if(actionsUser.contains(Actions.ACTUALIZAR_CONTRASEÑA)) System.out.println("7. Actualizar Contraseña");
                    if(actionsUser.contains(Actions.IMPRIMIR_HISTORIAL_COMPLETO)) System.out.println("8. Imprimir Historial de Todos los Usuarios");

                    int menu = Integer.parseInt(l.nextLine());
                    System.out.println();
                    switch(menu){
                        case 0:
                            System.out.println("Cerrando sesión...");
                            login = authService.logout();
                            break;
                        case 1:
                            if(actionsUser.contains(Actions.CREAR_USUARIO)){
                                System.out.println("-- Crear Usuario --");
                                System.out.println("Nombre Completo:");
                                nombre = l.nextLine();
                                System.out.println("Nombre de Usuario:");
                                u_name = l.nextLine();
                                System.out.println("Contraseña:");
                                pass = l.nextLine();
                                System.out.println("Rol (1.ADMIN - 2.USER - 3.GUEST)");
                                rol = Integer.parseInt(l.nextLine());
                                if(rol<1 || rol>3){
                                    System.out.println("Rol inválido.");
                                    break;
                                }
                                userService.create(activeUser.getRole(),nombre,u_name,pass,Roles.getRol(rol));
                            }
                            break;
                        case 2:
                            if(actionsUser.contains(Actions.ELIMINAR_USUARIO)){
                                l.nextLine();
                                System.out.println("-- Eliminar Usuario --");
                                System.out.println("1.Username - 2.Id");
                                menu = Integer.parseInt(l.nextLine());
                                switch (menu){
                                    case 1:
                                        System.out.println("Ingrese el username");
                                        u_name = l.nextLine();
                                        user = usersList.searchUser(u_name);
                                        if(user != null && user.getRole()!=Roles.OWNER){
                                            if(userService.remove(activeUser.getRole(),user)) System.out.println("Se eliminó correctamente");
                                        }
                                        break;
                                    case 2:
                                        System.out.println("Ingrese el id");
                                        id = Integer.parseInt(l.nextLine());
                                        user = usersList.searchUser(id);
                                        if(user != null && user.getRole()!=Roles.OWNER){
                                            if(userService.remove(activeUser.getRole(),user)) System.out.println("Se eliminó correctamente");
                                        }
                                        break;
                                    default:
                                        System.out.println("Opción inválida");
                                        break;
                                }
                            }else{
                                System.out.println("No tienes permiso para realizar esta acción");
                            }
                            break;

                        case 3:
                            if(actionsUser.contains(Actions.MODIFICAR_ROL)){
                                System.out.println("-- Actualizar Rol Usuario --");
                                System.out.println("1.Username - 2.Id");
                                menu = Integer.parseInt(l.nextLine());
                                switch (menu) {
                                    case 1:
                                        System.out.println("Ingrese el username");
                                        u_name = l.nextLine();
                                        user = usersList.searchUser(u_name);
                                        System.out.println("Nuevo Rol (1.ADMIN - 2.USER - 3.GUEST)");
                                        rol = Integer.parseInt(l.nextLine());
                                        if (rol < 1 || rol > 3) {
                                            System.out.println("Rol inválido.");
                                            break;
                                        }
                                        if (userService.updateRol(activeUser.getRole(), user, Roles.getRol(rol))) {
                                            System.out.println("Se ha modificado el rol");
                                            break;
                                        }
                                        System.out.println("No se ha podido modificar el rol");
                                        break;
                                    case 2:
                                        System.out.println("Ingrese el id");
                                        id = Integer.parseInt(l.nextLine());
                                        user = usersList.searchUser(id);
                                        System.out.println("Nuevo Rol (1.ADMIN - 2.USER - 3.GUEST)");
                                        rol = Integer.parseInt(l.nextLine());
                                        if (rol < 1 || rol > 3) {
                                            System.out.println("Rol inválido.");
                                            break;
                                        }
                                        if (userService.updateRol(activeUser.getRole(), user, Roles.getRol(rol))) {
                                            System.out.println("Se ha modificado el rol");
                                            break;
                                        }
                                        System.out.println("No se ha podido modificar el rol");
                                        break;
                                    default:
                                        System.out.println("Opción inválida");
                                        break;
                                }
                            }else{
                                System.out.println("No tienes permiso para realizar esta acción");
                            }
                            break;
                        case 4:
                            if(actionsUser.contains(Actions.BUSCAR_USUARIO)){
                                System.out.println("-- Buscar Usuario --");
                                System.out.println("1.Username - 2.Id");
                                menu = Integer.parseInt(l.nextLine());
                                switch (menu){
                                    case 1:
                                        System.out.println("Ingrese el username");
                                        u_name = l.nextLine();
                                        user = usersList.searchUser(u_name);
                                        if(user != null){
                                            System.out.println(user);
                                            break;
                                        }
                                        System.out.println("No se encontró al usuario");
                                        break;
                                    case 2:
                                        System.out.println("Ingrese el Id");
                                        id = Integer.parseInt(l.nextLine());
                                        user = usersList.searchUser(id);
                                        if(user != null){
                                            System.out.println(user);
                                            break;
                                        }
                                        System.out.println("No se encontró al usuario");
                                        break;
                                    default:
                                        System.out.println("Opción inválida");
                                        break;
                                }
                            }else{
                                System.out.println("No tienes permiso para realizar esta acción");
                            }
                            break;
                        case 5:
                            if(actionsUser.contains(Actions.IMPRIMIR_HISTORIA)){
                                System.out.println("-- Historial Acciones--");
                                usersList.getActionsList().printUser(activeUser.getID());
                            }else{
                                System.out.println("No tienes permiso para realizar esta acción");
                            }

                            break;
                        case 6: // Actualizar nombre
                            if (actionsUser.contains(Actions.ACTUALIZAR_NOMBRE)) {
                                System.out.println("Nuevo nombre completo:");
                                String nuevoNombre = l.nextLine();
                                if (userService.updateName(activeUser.getRole(), activeUser, nuevoNombre)) {
                                    System.out.println("Nombre actualizado correctamente.");
                                } else {
                                    System.out.println("No tienes permiso o el nombre no se pudo actualizar.");
                                }
                            } else {
                                System.out.println("No tienes permiso para realizar esta acción.");
                            }
                            break;

                        case 7:
                            if (actionsUser.contains(Actions.ACTUALIZAR_CONTRASEÑA)) {
                                System.out.println("Contraseña actual:");
                                String actual = l.nextLine();
                                System.out.println("Nueva contraseña:");
                                String nueva = l.nextLine();
                                if (userService.updateRol(activeUser.getRole(), activeUser, actual, nueva)) {
                                    System.out.println("Contraseña actualizada correctamente.");
                                } else {
                                    System.out.println("Contraseña incorrecta o no tienes permiso.");
                                }
                            } else {
                                System.out.println("No tienes permiso para realizar esta acción.");
                            }
                            break;
                        case 8:
                            if(actionsUser.contains(Actions.IMPRIMIR_HISTORIAL_COMPLETO)){
                                System.out.println("Historial Completo");
                                usersList.getActionsList().printAllUsers();
                            }
                            break;
                        default:
                            System.out.println("Opción inválida");
                            break;
                    }
                }
            }else{
                System.out.println("Usuario o Contraseña inválida");
            }
        }
    }
}
