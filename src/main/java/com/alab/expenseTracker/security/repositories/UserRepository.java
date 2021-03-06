package com.alab.expenseTracker.security.repositories;

import com.alab.expenseTracker.security.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    User findByUsername(String username);
}
