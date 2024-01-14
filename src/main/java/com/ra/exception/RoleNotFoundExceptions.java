package com.ra.exception;

public class RoleNotFoundExceptions extends Throwable {
    public RoleNotFoundExceptions(String roleNotFound) {
        super(roleNotFound);
    }
}
