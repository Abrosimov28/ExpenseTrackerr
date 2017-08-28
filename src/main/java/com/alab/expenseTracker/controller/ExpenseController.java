package com.alab.expenseTracker.controller;

import com.alab.expenseTracker.entities.Expense;
import com.alab.expenseTracker.service.ExpenseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

@Controller
@Api(value = "Expense controller", description = "Handles adding, removing, editing and searching of expenses")
public class ExpenseController {

        @Autowired
        private ExpenseService expenseService;

        @RequestMapping(value = "/", method = RequestMethod.GET)
        @ApiOperation(value = "Find all Expenses this year", notes = "Finds all Expenses this year")
        String index(Model model) throws ParseException {
            model.addAttribute("yearlyPerUsers", expenseService.yearlyPerUsers());
//            model.addAttribute("categoriesCurrentYearPerUser", expenseService.yearlyCategoriesPerUser());
            return "index";
        }

        @RequestMapping(value = "expenses" , method = RequestMethod.GET)
        @ApiOperation(value = "Find all Expenses", notes = "Finds all Expenses")
        public String getAllExpenses(Model model) {
            model.addAttribute("expenses", expenseService.findAllExpenses());
            model.addAttribute("totalCost", expenseService.getTotalCost(expenseService.findAllExpenses()));
            model.addAttribute("users", expenseService.findAllUsers());
            return "expenses";
        }


        @RequestMapping(value = "expensesByChosenDate", method = RequestMethod.GET )
        @ApiOperation(value = "Find Expenses by chosen date", notes = "Finds Expenses by chosen date")
        public String getAllExpensesByChosenDate(@RequestParam("dateFrom") String dateFrom,
                                                 @RequestParam("dateTo") String dateTo,
                                                 @RequestParam("type") String type,
                                                 @RequestParam("paidBy") String paidBy,
                                                 Model model) throws ParseException {
            model.addAttribute("expenses", expenseService.findAllExpensesByChosenDate(dateFrom, dateTo, type, paidBy));
            model.addAttribute("totalCost", expenseService.getTotalCost(expenseService.
                    findAllExpensesByChosenDate(dateFrom, dateTo, type, paidBy)));
            model.addAttribute("users", expenseService.findAllUsers());
            return "expenses";
        }


        @RequestMapping(value = "expense/{id}", method = RequestMethod.GET)
        @ApiOperation(value = "Find a Expense by ID", notes = "Finds a Expense by ID")
        public String getExpense(@PathVariable Integer id,  Model model){
            model.addAttribute("expense", expenseService.findById(id));
            return "expenseGet";
        }

        @RequestMapping(value = "expense/new", method = RequestMethod.GET)
        @ApiOperation(value = "Get the form for a new Expense", notes = "Gets the form for a new Expense")
        public String newExpense(Model model) {
            model.addAttribute("expense", new Expense());
            return "expenseform";
        }

        @RequestMapping(value = "expense/edit/{id}", method = RequestMethod.GET)
        @ApiOperation(value = "Find the Expense for editing", notes = "Finds the Expense for editing")
        public String edit(@PathVariable Integer id, Model model) {
            model.addAttribute("expense", expenseService.findById(id));
            return "expenseform";
        }

        @RequestMapping(value = "expense", method = RequestMethod.POST)
        @ApiOperation(value = "Save an edited or new Expense", notes = "Saves an edited or new Expense")
        public String saveExpense(Expense expense) throws ParseException {
            expenseService.saveExpense(expense);
            return "redirect:/expenses";
        }

        @RequestMapping(value = "expense/delete/{id}", method={RequestMethod.GET})
        @ApiOperation(value = "Delete an Expense", notes = "Deletes an Expense")
        public String deleteExpense(@PathVariable Integer id) {
            expenseService.deleteExpense(id);
            return "redirect:/expenses";
        }

        @RequestMapping(value = "/login", method = RequestMethod.GET)
        public String login(){
            return "login";
        }
}
