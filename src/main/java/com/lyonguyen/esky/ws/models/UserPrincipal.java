package com.lyonguyen.esky.ws.models;

import com.lyonguyen.esky.logic.enums.Role;

import java.security.Principal;

public class UserPrincipal implements Principal {

    private String id;

    private Role role;

    public UserPrincipal(String id, Role role) {
        this.id = id;
        this.role = role;
    }

    @Override
    public String getName() {
        return id;
    }

    public String getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }
}
