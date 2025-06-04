package com.relax.luckydraw.repository;

import com.relax.luckydraw.model.Prize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;

public interface PrizeRepository extends JpaRepository<Prize, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Prize p WHERE p.id = ?1")
    Prize findByIdWithLock(Long id);
    
    List<Prize> findByRemainingQuantityGreaterThan(Integer quantity);
} 