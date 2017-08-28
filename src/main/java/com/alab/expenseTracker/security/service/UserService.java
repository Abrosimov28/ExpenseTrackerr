package com.alab.expenseTracker.security.service;

import com.alab.expenseTracker.security.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends CRUDService<User> {
    User findByUsername(String username);
}
