package com.alab.expenseTracker;

import com.alab.expenseTracker.entities.Expense;
import com.alab.expenseTracker.service.ExpenseService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Transactional

public class ExpenseServiceTest extends AbstractTest {

    private final int TEST_ID_EXISTS = 10;
    private final int TEST_ID_NOT_EXISTS = -1;

    @Autowired
    private ExpenseService expenseService;

    @Test
    public void testFindAll() {
        List<Expense> list = expenseService.findAllExpenses();

        Assert.assertNotNull("Failure - expected not null", list);
        Assert.assertNotEquals("Failure - expected other than 0", 0, list.size());
    }

    @Test
    public void testFindOne() {
        int id = TEST_ID_EXISTS;

        Expense expense = expenseService.findById(id);
        Assert.assertNotNull("Failure - expected not null", expense);
        Assert.assertEquals("Failure - expected id attribute match", id, expense.getId());
    }

    @Test
    public void testFindOneNotFound() {
        Integer id = Integer.MAX_VALUE;

        Expense expense = expenseService.findById(id);
        Assert.assertNull("Failure - expected null", expense);
    }

    @Test
    public void testCreate() throws ParseException {

        Expense expense = new Expense();
        expense.setId(TEST_ID_NOT_EXISTS);
        expense.setType("Food");
        expense.setPaidBy("Alexander");
        expense.setDate("01-07-17");
        expense.setCost(1);
        expense.setDescription("JTest");
        expenseService.saveExpense(expense);
        System.out.println(expense.getId());
        expense = expenseService.findById(expense.getId());
        System.out.println(expense.getId());

        Assert.assertNotNull("Failure - expected not null", expense);
        Assert.assertNotNull("Failure - expected id attribute not null", expense.getId());
        Assert.assertEquals("Failure - expected text match", "JTest", expense.getDescription());
    }

    @Test
    public void testDelete() throws ParseException {
        Expense expense = new Expense();
        expense.setId(TEST_ID_NOT_EXISTS);
        expense.setType("Food");
        expense.setPaidBy("Alexander");
        expense.setDate("01-07-17");
        expense.setCost(1);
        expense.setDescription("JTest");

        expenseService.saveExpense(expense);

        expenseService.deleteExpense(TEST_ID_NOT_EXISTS);
        Expense deletedExpense = expenseService.findById(TEST_ID_NOT_EXISTS);

        Assert.assertNull("Failure - expected entity to be deleted", deletedExpense);
    }



}
