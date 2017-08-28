package com.alab.expenseTracker.repositories;

import com.alab.expenseTracker.entities.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Integer>{
    List<Expense> findAll();
    Expense findById(int id);
}
