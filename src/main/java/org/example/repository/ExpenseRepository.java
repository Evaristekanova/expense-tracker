package org.example.repository;

import org.example.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense>  findByUserIdOrderByDateDesc(String userId);
    Optional<Expense> findExpenseByIdAndUserId(Long id, String userId);

}
