package com.example.tuan3.config;

import jakarta.servlet.http.HttpServletRequest;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private RolePermissionConfig rolePermissionConfig;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new DefaultMethodSecurityExpressionHandler() {
            @Override
            protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
                    Authentication authentication, MethodInvocation invocation) {

                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attr != null ? attr.getRequest() : null;

                CustomMethodSecurityExpressionRoot root =
                        new CustomMethodSecurityExpressionRoot(authentication, rolePermissionConfig);

                root.setThis(invocation.getThis());
                return root;
            }
        };
    }
}
