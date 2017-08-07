package com.alab.expenseTracker;

import com.alab.expenseTracker.entities.Expense;
import com.alab.expenseTracker.repositories.ExpenseRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

// AUTO LOADNE UDAJE DO DATABASY NA TESTING
@Component
public class ExpenseLoader implements ApplicationListener<ContextRefreshedEvent> {

    private ExpenseRepository expenseRepository;

    private Logger log = Logger.getLogger(ExpenseLoader.class);

    @Autowired
    public void setProductRepository(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

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
}