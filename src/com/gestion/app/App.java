package com.gestion.app;

import com.gestion.models.Roles;
import com.gestion.models.User;
import com.gestion.repository.UsersList;
import com.gestion.utils.Actions;
import com.gestion.services.AuthService;
import com.gestion.services.UserService;

import com.gestion.utils.Permissions;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class App {
    public static void main(String[] args){
        UsersList usersList = new UsersList();
        AuthService authService = new AuthService(usersList);
        Scanner l = new Scanner(System.in);
        boolean login = false;
        User user,activeUser = null;
        boolean b;
        String nombre,u_name,pass,nuevoNombre;
        int id, rol,blockedUser;
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
            activeUser = usersList.searchUser(username);
            if(authService.getBlockedList().contains(activeUser)){
                System.out.println("Usuario Bloqueado por fallar múltiples veces el inicio de sesión");
                continue;
            }
            if(login){
                authService.setFailureCount(activeUser,0);
                while(login){
                    Set<Actions> actionsUser = Permissions.accionesRoles.get(activeUser.getRole());
                    b = false;
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
                    if(actionsUser.contains(Actions.MODIFICAR_DATOS_DE_OTRO_USUARIO)) System.out.println("9. Modificar los datos de otro Usuario");
                    if(actionsUser.contains(Actions.DESBLOQUEAR_USUARIO)) System.out.println("10. Desbloquear Usuario");

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
                                        if (userService.updateRole(activeUser.getRole(), user, Roles.getRol(rol))) {
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
                                        if (userService.updateRole(activeUser.getRole(), user, Roles.getRol(rol))) {
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
                                System.out.println("-- Nuevo nombre completo:");
                                nuevoNombre = l.nextLine();
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
                                System.out.println("-- Contraseña actual:");
                                String actual = l.nextLine();
                                System.out.println("Nueva contraseña:");
                                String nueva = l.nextLine();
                                if (userService.updatePassword(activeUser.getRole(), activeUser, actual, nueva)) {
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
                                System.out.println("-- Historial Completo --");
                                usersList.getActionsList().printAllUsers();
                            }
                            break;
                        case 9:
                            if(actionsUser.contains(Actions.MODIFICAR_DATOS_DE_OTRO_USUARIO)){
                                System.out.println("-- Modificar Usuario Existente --");
                                System.out.println("Ingrese el username: ");
                                username = l.nextLine();
                                user = usersList.searchUser(username);
                                if(user != null){
                                    System.out.println("Opciones");
                                    System.out.println("1. Modificar nombre");
                                    System.out.println("2. Modificar contraseña");
                                    menu = Integer.parseInt(l.nextLine());
                                    switch (menu){
                                        case 1:
                                            System.out.println("Nuevo nombre:");
                                            nuevoNombre = l.nextLine();
                                            b = userService.updateName(activeUser.getRole(),user,nuevoNombre);
                                            if(b) System.out.println("Nombre modificado: " + b);
                                            break;
                                        case 2:
                                            System.out.println("Nueva Contraseña:");
                                            pass = l.nextLine();
                                            b = userService.updatePassword(activeUser.getRole(),user,user.getPassword(),pass);
                                            if(b) System.out.println("contraseña modificado: " + b);
                                            break;
                                        default:
                                            break;
                                    }
                                }else{
                                    System.out.println("No se encontró ese usuario");
                                }
                            }else{
                                System.out.println("No tienes permiso para realizar esta acción");
                            }
                            break;
                        case 10:
                            if(actionsUser.contains(Actions.DESBLOQUEAR_USUARIO)){
                                int contador = 1;
                                List<User> blockedUsers = authService.getBlockedList();
                                System.out.println("Lista de Bloqueados");
                                for(User u : blockedUsers){
                                    System.out.println(contador + ": " + u);
                                    contador++;
                                }
                                System.out.println("Ingrese el índice del usuario a desbloquear");
                                blockedUser = Integer.parseInt(l.nextLine()) - 1;
                                user = usersList.searchUser(blockedUsers.get(blockedUser).getUsername());
                                if(user != null){
                                    blockedUsers.remove(user);
                                    authService.setFailureCount(user,0);
                                    System.out.println("Usuario desbloqueado");
                                }else{
                                    System.out.println("Usuario inválido");
                                }
                            }else{
                                System.out.println("No tienes permiso para realizar esta acción");
                            }
                            break;
                        default:
                            System.out.println("Opción inválida");
                            break;
                    }
                }
            }else if(activeUser!= null){
                System.out.println("Usuario o Contraseña inválida");
                int count = authService.getFailureCount().getOrDefault(activeUser,0);
                if(count==2) authService.blockUser(activeUser);
                authService.setFailureCount(activeUser,(count+1));
            }else{
                System.out.println("Usuario inexistente");
            }
        }
    }
}
