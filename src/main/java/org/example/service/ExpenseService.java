package org.example.service;

import org.example.model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    List<Expense> getExpenseByDay(String day);
    List<Expense> getExpenseByCategoryAndMonth(String category, String month);
    List<String> getExpenseCategories();
    List<Expense> geExpenses();

    Optional<Expense> getExpenseById(Long id);
    Expense addExpense(Expense expense);
    boolean updateExpense(Expense expense);
    boolean deleteExpense(Long id);
}
