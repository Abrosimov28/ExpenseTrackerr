package com.alab.expenseTracker.service;

import com.alab.expenseTracker.entities.Expense;
import com.alab.expenseTracker.repositories.ExpenseRepository;
import com.alab.expenseTracker.security.entities.User;
import com.alab.expenseTracker.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExpenseService {

    private final String ALL = "all";

    @Autowired
    private ExpenseRepository expenseDao;

    SimpleDateFormat sdfOutput = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
    SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Autowired
    private UserRepository users;

    public Iterable<User> findAllUsers() {
        return users.findAll();

    }

    public Expense findById(int id) {
        return expenseDao.findOne(id);
    }

    public List<Expense> findAllExpenses() {
        return sort(expenseDao.findAll());
    }

    public void deleteExpense(int id) {
        expenseDao.delete(id);
    }

    ;

    public void saveExpense(Expense expense) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        expense.setPaidBy(name);

        Date date = sdfInput.parse(expense.getDate());
        expense.setDate(sdfOutput.format(date));
        expenseDao.save(expense);
    }

    public List<Expense> findAllExpensesByChosenDate(String dateFrom,
                                                     String dateTo,
                                                     String type,
                                                     String paidBy) throws ParseException {
        List<Expense> listByChosenDate = new LinkedList<Expense>();
        Date dateF = sdfInput.parse(dateFrom);
        Date dateT = sdfInput.parse(dateTo);

        for (Expense expense : filterByType(type)) {
            Date dateOfExpense = sdfOutput.parse(expense.getDate());
            if (dateOfExpense.after(dateF) && dateOfExpense.before(dateT)) {
                listByChosenDate.add(expense);
            }
        }
        listByChosenDate = filterByUser(listByChosenDate, paidBy);
        return sort(listByChosenDate);

    }

    private List<Expense> filterByUser(List<Expense> listByChosenDate, String paidBy) {
        if (paidBy.equals(ALL)) {
            return listByChosenDate;
        }
        List<Expense> listByUser = new LinkedList<Expense>();
        for (Expense expense : listByChosenDate) {
            if (expense.getPaidBy().equals(paidBy)) {
                listByUser.add(expense);
            }
        }
        return listByUser;
    }

    public List sort(List list) {
        Collections.sort(list, new Comparator<Expense>() {
            @Override
            public int compare(Expense r1, Expense r2) {
                try {
                    return sdfOutput.parse(r1.getDate()).compareTo(sdfOutput.parse(r2.getDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        return list;
    }

    private List<Expense> filterByType(String type) {
        if (type.equals(ALL)) {
            return expenseDao.findAll();
        }
        List<Expense> listByType = new LinkedList<Expense>();
        for (Expense expense : expenseDao.findAll()) {
            if (expense.getType().equals(type)) {
                listByType.add(expense);
            }
        }
        return listByType;
    }


    public int getTotalCost(List<Expense> allExpensesByChosenDate) {
        int totalCost = 0;
        for (Expense expense : allExpensesByChosenDate) {
            totalCost += expense.getCost();
        }
        return totalCost;
    }

      // Method which will return a sum of what each user has paid totally
    public Map<String, Integer> yearlyPerUsers() throws ParseException {
        Map<String, Integer> map = new HashMap<>();

        for (Expense exp: expensesThisYear()) {
            if (!map.containsKey(exp.getPaidBy())) {
                Integer sum = exp.getCost();
                map.put(exp.getPaidBy(), sum);
            } else {
                map.put(exp.getPaidBy(),  map.get(exp.getPaidBy()) + exp.getCost());
            }
        }
        return map;
    }

    public List<Expense> expensesThisYear() throws ParseException {
        List<Expense> expenses = new LinkedList<Expense>();
        Date today = new Date();
        int thisYear = today.getYear();

        for (Expense exp : findAllExpenses()) {
            if (sdfOutput.parse(exp.getDate()).getYear() == thisYear) {
                expenses.add(exp);
            }
        }
        return expenses;
    }
    // method which will return what has each user paid in each category
//    public Map<String, Map<String, Integer>> yearlyCategoriesPerUser() throws ParseException {
//        Map<String, Map<String, Integer>> map = new HashMap<>();
//
//        for (Expense exp: expensesThisYear()) {
//            if (!map.containsKey(exp.getPaidBy())) {
//                map.put(exp.getPaidBy(), new HashMap<String, Integer>());
//            } else {
//                if (!map.get(exp.getPaidBy()).containsKey(exp.getType())) {
//                    int sum = exp.getCost();
//                    map.get(exp.getPaidBy()).put(exp.getType(), sum);
//                } else {
//                    map.get(exp.getPaidBy()).put(exp.getType(), exp.getCost() + map.get(exp.getPaidBy()).get(exp.getType()));
//                }
//            }
//        }
//        return map;
//    }

}
