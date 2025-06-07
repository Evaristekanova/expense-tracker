package org.example.service;

import org.example.model.AppUser;
import org.example.model.Expense;
import org.example.repository.ExpenseRepository;
import org.example.utils.ExpenseDataLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
    }

    @Override
    public List<Expense> getUserExpenses(String userId) {
        return new ArrayList<>(expenseRepository.findByUserIdOrderByDateDesc(userId));
    }

    @Override
    public List<Expense> getExpenseByDay(String date, String userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId).stream().filter(expense -> expense.getDate().equals(date)).toList();
    }

    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month, String userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId).stream().filter(expense -> expense.getCategory().equalsIgnoreCase(category) && expense.getDate().startsWith(month)).toList();
    }

    @Override
    public List<String> getExpenseCategories(String userId) {
        return expenseRepository.findByUserIdOrderByDateDesc(userId).stream().map(Expense::getCategory).distinct().toList();
    }

    @Override
    public List<Expense> geExpenses() {
        return expenseRepository.findAll().stream().toList();
    }

    @Override
    public Optional<Expense> getExpenseById(Long id, String userId) {
        return expenseRepository.findExpenseByIdAndUserId(id, userId).stream().filter(expense -> Objects.equals(expense.getId(), id)).findFirst();
    }

    @Override
    public Expense addExpense(Expense expense, String userId) {
        Optional<AppUser> userOptional = userService.findUserById(userId);
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();
            expense.setUser(user);
            return expenseRepository.save(expense);
        }else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public boolean updateExpense(Expense expense, String userId) {
        Optional<Expense> existingExpense = expenseRepository.findExpenseByIdAndUserId(expense.getId(), userId);
        if (existingExpense.isPresent()) {
            expense.setUser(existingExpense.get().getUser());
            expenseRepository.save(expense);
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean deleteExpense(Long id, String userId) {
        Optional<Expense> existingExpense = expenseRepository.findExpenseByIdAndUserId(id, userId);
        if (existingExpense.isPresent()) {

            expenseRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}
