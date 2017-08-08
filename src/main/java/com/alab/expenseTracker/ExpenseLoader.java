package com.alab.expenseTracker;

import com.alab.expenseTracker.entities.Expense;
import com.alab.expenseTracker.repositories.ExpenseRepository;
import com.alab.expenseTracker.security.entities.Role;
import com.alab.expenseTracker.security.entities.User;
import com.alab.expenseTracker.security.service.RoleService;
import com.alab.expenseTracker.security.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

// AUTO LOADNE UDAJE DO DATABASY NA TESTING
@Component
public class ExpenseLoader implements ApplicationListener<ContextRefreshedEvent> {

    private ExpenseRepository expenseRepository;
    private UserService userService;
    private RoleService roleService;

    private Logger log = Logger.getLogger(ExpenseLoader.class);

    @Autowired
    public void setProductRepository(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadExpenses();
        loadUsers();
        loadRoles();
        assignUsersToUserRole();
        assignUsersToAdminRole();
    }
    private void loadExpenses() {
        Expense expense1 = new Expense();
        expense1.setCost(1000);
        expense1.setDescription("Rema 1000");
        expense1.setDate("01.07.17");
        expense1.setType("Food");
        expenseRepository.save(expense1);

        Expense expense2 = new Expense();
        expense2.setCost(2000);
        expense2.setDescription("H&M");
        expense2.setDate("01.08.17");
        expense2.setType("Clothes");
        expenseRepository.save(expense2);

        Expense expense3 = new Expense();
        expense3.setCost(3000);
        expense3.setDescription("Rent");
        expense3.setDate("01.09.17");
        expense3.setType("House");
        expenseRepository.save(expense3);

        Expense expense4 = new Expense();
        expense4.setCost(4000);
        expense4.setDescription("Stavanger");
        expense4.setDate("01.09.17");
        expense4.setType("Holiday");
        expenseRepository.save(expense4);
    }

    private void loadUsers() {
        User user1 = new User();
        user1.setUsername("user");
        user1.setPassword("user");
        user1.setDateCreated(new Date());
        userService.saveOrUpdate(user1);

        User user2 = new User();
        user2.setUsername("admin");
        user2.setPassword("admin");
        userService.saveOrUpdate(user2);

    }

    private void loadRoles() {
        Role role = new Role();
        role.setRole("USER");
        roleService.saveOrUpdate(role);
        log.info("Saved role" + role.getRole());
        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        roleService.saveOrUpdate(adminRole);
        log.info("Saved role" + adminRole.getRole());
    }
    private void assignUsersToUserRole() {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("USER")) {
                users.forEach(user -> {
                    if (user.getUsername().equals("user")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }
    private void assignUsersToAdminRole() {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ADMIN")) {
                users.forEach(user -> {
                    if (user.getUsername().equals("admin")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }

}