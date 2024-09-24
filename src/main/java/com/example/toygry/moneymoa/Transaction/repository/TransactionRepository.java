package com.example.toygry.moneymoa.Transaction.repository;

import com.example.toygry.moneymoa.Transaction.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, UUID> {

    // 해당 년,월,일 에 해당하는 하루치 값 구하기
    @Query("select t from Transactions t where t.userId = :userId and Date(t.createdDate) = :date")
    Optional<List<Transactions>> findByUserIdAndCreatedDate(@Param("userId") String userId, @Param("date") LocalDate date);

    // 해당 년,월 에 해당하는 하루치 값 구하기
    @Query("select coalesce(sum(t.amount), 0) from Transactions t where t.userId = :userId and t.type = 'DEPOSIT' and extract(YEAR from t.createdDate) = :year and extract(MONTH from t.createdDate) = :month")
    BigDecimal getTotalDepositForMonthAndUser(@Param("userId") String userId, @Param("year") int year, @Param("month") int month);

    @Query("select coalesce(sum(t.amount), 0) from Transactions t where t.userId = :userId and t.type = 'WITHDRAWAL' and extract(YEAR from t.createdDate) = :year and extract(MONTH from t.createdDate) = :month")
    BigDecimal getTotalWithdrawalForMonthAndUser(@Param("userId") String userId, @Param("year") int year, @Param("month") int month);

    @Query("select t from Transactions t where t.userId = :userId and t.createdDate >= :startDate and t.createdDate < :endDate")
    Optional<List<Transactions>> findTransactionsBetweenDate(@Param("userId") String userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
