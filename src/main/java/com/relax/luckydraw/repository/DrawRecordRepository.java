package com.relax.luckydraw.repository;

import com.relax.luckydraw.model.DrawRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface DrawRecordRepository extends JpaRepository<DrawRecord, Long> {
    boolean existsByUserIdAndDrawTimeBetween(String userId, LocalDateTime start, LocalDateTime end);
} 