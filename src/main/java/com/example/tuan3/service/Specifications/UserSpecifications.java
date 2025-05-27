package com.example.tuan3.service.Specifications;

import com.example.tuan3.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> hasUsername(String username) {

        return  ((root, query, cb) ->
                username  == null ? null : cb.like(cb.lower(root.get("username")),"%" + username.toLowerCase() + "%"));
    }
    public static Specification<User> hasFullName(String fullname) {
        return  ((root, query, cb) ->
                fullname  == null ? null : cb.like(cb.lower(root.get("fullname")),"%" + fullname.toLowerCase() + "%"));
    }
    public static Specification<User> hasAge(Integer age) {
        return  ((root, query, cb) ->
                age  == null ? null : cb.equal(root.get("age"), age));
    }
    public static Specification<User> hasAddress(String address) {
        return  ((root, query, cb) ->
                address  == null ? null : cb.like((cb.lower(root.get("address"))), "%" + address.toLowerCase() + "%"));
    }
}
