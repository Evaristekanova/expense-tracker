package org.example.service;

import org.example.model.Expense;
import org.example.repository.ExpenseRepository;
import org.example.utils.ExpenseDataLoader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getExpenseByDay(String data) {
        return expenseRepository.findAll().stream().filter(expense -> expense.getDate().equalsIgnoreCase(data)).toList();
    }

    @Override
    public List<Expense> getExpenseByCategoryAndMonth(String category, String month) {
        return expenseRepository.findAll().stream().filter(expense -> expense.getCategory().equalsIgnoreCase(category) && expense.getDate().startsWith(month)).toList();
    }

    @Override
    public List<String> getExpenseCategories() {
        return expenseRepository.findAll().stream().map(Expense::getCategory).distinct().toList();
    }

    @Override
    public List<Expense> geExpenses() {
        return expenseRepository.findAll().stream().toList();
    }

    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(String.valueOf(id)).stream().filter(expense -> Objects.equals(expense.getId(), id)).findFirst();
    }

    @Override
    public Expense addExpense(Expense expense) {
        expenseRepository.save(expense);
        return expense;
    }

    @Override
    public boolean updateExpense(Expense expense) {
        if (expenseRepository.existsById(String.valueOf(expense.getId()))) {
            expenseRepository.save(expense);
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean deleteExpense(Long id) {
        if (expenseRepository.existsById(String.valueOf(id))) {
            expenseRepository.deleteById(String.valueOf(id));
            return true;
        }else{
            return false;
        }
    }
}
