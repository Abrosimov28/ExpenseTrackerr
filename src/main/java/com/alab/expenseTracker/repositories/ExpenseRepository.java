package com.alab.expenseTracker.repositories;

import com.alab.expenseTracker.entities.Expense;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExpenseRepository extends CrudRepository<Expense, Integer>{
    List<Expense> findAll();
}
