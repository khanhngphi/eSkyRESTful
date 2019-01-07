package com.lyonguyen.esky.logic.enums;

public enum Role {
    LEARNER,
    CONTRIBUTOR,
    MANAGER,
    ADMIN;

    public int compare(Role role) {
        return this.ordinal() - role.ordinal();
    }

    public boolean isGreaterEqual(Role role) {
        if(this.compare(role) >= 0) {
            return true;
        }
        return false;
    }
}
