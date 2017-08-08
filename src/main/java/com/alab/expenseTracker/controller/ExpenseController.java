package com.alab.expenseTracker.controller;

import com.alab.expenseTracker.entities.Expense;
import com.alab.expenseTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

@Controller
public class ExpenseController {

        @Autowired
        private ExpenseService expenseService;

        @RequestMapping("/")
        String index() {
            return "index";
        }

        @RequestMapping(value = "expenses" , method = RequestMethod.GET)
        public String getAllExpenses(Model model) {
            model.addAttribute("expenses", expenseService.findAllExpenses());
            model.addAttribute("totalCost", expenseService.getTotalCost(expenseService.findAllExpenses()));
            return "expenses";
        }

//        @RequestMapping(value = "expensesByCurrentMonth", method = RequestMethod.GET)
//        public String getAllExpensesByCurrentMonth(@RequestParam("type") String type,
//                                                   Model model) throws ParseException {
//            model.addAttribute("expenses", expenseService.getAllExpensesByCurrentMonth(type));
//            model.addAttribute("totalCost", expenseService.getTotalCost(expenseService.getAllExpensesByCurrentMonth(type)));
//            return "expenses";
//        }

        @RequestMapping(value = "expensesByChosenDate", method = RequestMethod.GET)
        public String getAllExpensesByChosenDate(@RequestParam("dateFrom") String dateFrom,
                                                 @RequestParam("dateTo") String dateTo,
                                                 @RequestParam("type") String type,
                                                 Model model) throws ParseException {
            model.addAttribute("expenses", expenseService.getAllExpensesByChosenDate(dateFrom, dateTo, type));
            model.addAttribute("totalCost", expenseService.getTotalCost(expenseService.
                    getAllExpensesByChosenDate(dateFrom, dateTo, type)));
            return "expenses";
        }


        @RequestMapping(value = "expense/{id}", method = RequestMethod.GET)
        public String getExpense(@PathVariable Integer id,  Model model){
            model.addAttribute("expense", expenseService.findById(id));
            return "expenseGet";
        }

        @RequestMapping(value = "expense/new")
        public String newExpense(Model model) {
            model.addAttribute("expense", new Expense());
            return "expenseform";
        }

        @RequestMapping(value = "expense/edit/{id}")
        public String edit(@PathVariable Integer id, Model model) {
            model.addAttribute("expense", expenseService.findById(id));
            return "expenseform";
        }

        @RequestMapping(value = "expense")
        public String saveExpense(Expense expense) throws ParseException {
            expenseService.saveExpense(expense);
            return "redirect:/expenses";
        }

        @RequestMapping(value = "expense/delete/{id}")
        public String deleteExpense(@PathVariable Integer id) {
            expenseService.deleteExpense(id);
            return "redirect:/expenses";
        }

        @RequestMapping(value = "/login", method = RequestMethod.GET)
        public String login(){
            return "login";
        }
}
