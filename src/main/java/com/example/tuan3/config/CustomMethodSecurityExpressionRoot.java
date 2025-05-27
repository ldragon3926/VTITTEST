package com.example.tuan3.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private final RolePermissionConfig rolePermissionConfig;
    private Object filterObject;
    private Object returnObject;
    private Object thisObject;

    public CustomMethodSecurityExpressionRoot(Authentication authentication, RolePermissionConfig service) {
        super(authentication);
        this.rolePermissionConfig = service;
    }

    public boolean fileRole(HttpServletRequest request) {
        String uri = request.getRequestURI().replaceFirst("^/", "");
        String requiredRole = rolePermissionConfig.getRequiredRole(uri);
        if (requiredRole == null) return false;

        return this.getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(requiredRole));
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    public void setThis(Object thisObject) {
        this.thisObject = thisObject;
    }

    @Override
    public Object getThis() {
        return this.thisObject;
    }
}
