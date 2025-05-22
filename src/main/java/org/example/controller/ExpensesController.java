package org.example.controller;

import org.example.model.Expense;
import org.example.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.geExpenses());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getExpenseCategories() {
        List<String> expenseCategories = expenseService.getExpenseCategories();
        if (expenseCategories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(expenseCategories);
    }

    @GetMapping("/expenses/categories/{category}/month")
    public ResponseEntity<List<Expense>> getExpensesBy(@PathVariable() String category, @RequestParam String month) {
        return ResponseEntity.ok(expenseService.getExpenseByCategoryAndMonth(category, month));
    }

    @GetMapping("/expenses/{date}")
    public ResponseEntity<List<Expense>> getExpenses(@PathVariable String date) {
        return ResponseEntity.ok(expenseService.getExpenseByDay(date));
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
        Expense newExpense = expenseService.addExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(newExpense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense updatedExpense, @PathVariable Long id) {
        boolean isUpdated = expenseService.updateExpense(updatedExpense);
        return isUpdated ? ResponseEntity.ok(updatedExpense) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Expense> deleteExpense(@PathVariable Long id) {
        boolean isDeleted = expenseService.deleteExpense(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
