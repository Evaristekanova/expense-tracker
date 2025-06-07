package org.example.service;

import org.example.model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    List<Expense> getUserExpenses(String userId);
    List<Expense> getExpenseByDay(String day, String userId);
    List<Expense> getExpenseByCategoryAndMonth(String category, String month, String userId );
    List<String> getExpenseCategories(String userId);
    List<Expense> geExpenses();

    Optional<Expense> getExpenseById(Long id, String userId);
    Expense addExpense(Expense expense, String userId);
    boolean updateExpense(Expense expense, String userId);
    boolean deleteExpense(Long id, String userId);
}
