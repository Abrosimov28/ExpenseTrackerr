package com.alab.expenseTracker.security.service;

import com.alab.expenseTracker.security.entities.User;
import com.alab.expenseTracker.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Profile("springdatajpa")
public class UserServiceImpl implements UserService {

   private UserRepository userRepository;

   @Autowired
    public void setUserRepository(UserRepository userRepository) {
       this.userRepository = userRepository;
   }

   private EncryptionService encryptionService;

   @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
       this.encryptionService = encryptionService;
   }

   @Override
    public List<?> listAll() {
       List<User> users = new ArrayList<>();
       userRepository.findAll().forEach(users::add);
       return users;
   }

   @Override
    public User getById(Integer id) {
       return userRepository.findOne(id);
   }

   @Override
   public User saveOrUpdate(User entityObject) {
       if(entityObject.getPassword() != null){
           entityObject.setEncryptedPassword(encryptionService.encryptString(entityObject.getPassword()));
       }
       return userRepository.save(entityObject);
   }
    @Override
    @Transactional
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
}
