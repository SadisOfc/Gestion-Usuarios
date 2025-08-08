package com.gestion.utils;

import com.gestion.models.Roles;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class Permissions {
    public static final Map<Roles, Set<Actions>> accionesRoles = Map.of(
            Roles.OWNER, EnumSet.allOf(Actions.class),
            Roles.ADMIN, EnumSet.allOf(Actions.class),
            Roles.USER, EnumSet.of(Actions.IMPRIMIR_HISTORIA,
                    Actions.ACTUALIZAR_NOMBRE,Actions.ACTUALIZAR_CONTRASEÑA),
            Roles.GUEST,EnumSet.noneOf(Actions.class)
    );
}
