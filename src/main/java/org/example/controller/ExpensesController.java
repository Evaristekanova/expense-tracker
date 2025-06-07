package org.example.controller;

import org.example.model.AppUser;
import org.example.model.Expense;
import org.example.service.ExpenseService;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {

    private final ExpenseService expenseService;
    private final UserService userService;

    public ExpensesController(UserService userService, ExpenseService expenseService) {
        this.userService = userService;
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.geExpenses());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getExpenseCategories(Authentication authentication) {
        final String email = authentication.getName();
        AppUser user = userService.findUserByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<String> expenseCategories = expenseService.getExpenseCategories(user.getId());

        return ResponseEntity.ok(expenseCategories);
    }

    @GetMapping("/expenses/categories/{category}/month")
    public ResponseEntity<List<Expense>> getExpensesBy(@PathVariable() String category, @RequestParam String month, Authentication authentication) {
        final String email = authentication.getName();
        Optional<AppUser> user = userService.findUserByEmail(email);
        return ResponseEntity.ok(expenseService.getExpenseByCategoryAndMonth(category, month, user.get().getId()));
    }

    @GetMapping("/expenses/{date}")
    public ResponseEntity<List<Expense>> getExpenses(@PathVariable String date, Authentication authentication) {
        final String email = authentication.getName();
        Optional<AppUser> user = userService.findUserByEmail(email);
        return ResponseEntity.ok(expenseService.getExpenseByDay(date, user.get().getId()));
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense, Authentication authentication) {
        final String email = authentication.getName();
        Optional<AppUser> user = userService.findUserByEmail(email);
        Expense newExpense = expenseService.addExpense(expense, user.get().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newExpense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense updatedExpense, @PathVariable Long id, Authentication authentication) {
        final String email = authentication.getName();
        Optional<AppUser> user = userService.findUserByEmail(email);
        boolean isUpdated = expenseService.updateExpense(updatedExpense, user.get().getId());
        return isUpdated ? ResponseEntity.ok(updatedExpense) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Expense> deleteExpense(@PathVariable Long id, Authentication authentication) {
        final String email = authentication.getName();
        Optional<AppUser> user = userService.findUserByEmail(email);
        boolean isDeleted = expenseService.deleteExpense(id, user.get().getId());
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
