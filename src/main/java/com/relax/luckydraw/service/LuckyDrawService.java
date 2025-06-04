package com.relax.luckydraw.service;

import com.relax.luckydraw.model.Prize;
import com.relax.luckydraw.model.DrawRecord;
import com.relax.luckydraw.repository.PrizeRepository;
import com.relax.luckydraw.repository.DrawRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class LuckyDrawService {
    private final PrizeRepository prizeRepository;
    private final DrawRecordRepository drawRecordRepository;
    private final Random random = new Random();

    public LuckyDrawService(PrizeRepository prizeRepository, DrawRecordRepository drawRecordRepository) {
        this.prizeRepository = prizeRepository;
        this.drawRecordRepository = drawRecordRepository;
    }

    @Transactional
    public DrawRecord draw(String userId) {
        // Check if user has drawn within 24 hours
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDayAgo = now.minusHours(24);
        if (drawRecordRepository.existsByUserIdAndDrawTimeBetween(userId, oneDayAgo, now)) {
            throw new RuntimeException("You can only draw once per day");
        }

        // Get available prizes
        List<Prize> availablePrizes = prizeRepository.findByRemainingQuantityGreaterThan(0);
        if (availablePrizes.isEmpty()) {
            throw new RuntimeException("No prizes available");
        }

        // Calculate total probability
        double totalProbability = availablePrizes.stream()
                .mapToDouble(Prize::getProbability)
                .sum();

        // Generate random number
        double randomValue = random.nextDouble() * totalProbability;

        // Select prize
        Prize selectedPrize = null;
        double accumulatedProbability = 0.0;
        for (Prize prize : availablePrizes) {
            accumulatedProbability += prize.getProbability();
            if (randomValue <= accumulatedProbability) {
                selectedPrize = prize;
                break;
            }
        }

        if (selectedPrize == null) {
            throw new RuntimeException("Failed to select prize");
        }

        // Lock and update prize quantity
        Prize lockedPrize = prizeRepository.findByIdWithLock(selectedPrize.getId());
        if (lockedPrize.getRemainingQuantity() <= 0) {
            throw new RuntimeException("Prize out of stock");
        }
        lockedPrize.setRemainingQuantity(lockedPrize.getRemainingQuantity() - 1);
        prizeRepository.save(lockedPrize);

        // Create draw record
        DrawRecord drawRecord = new DrawRecord();
        drawRecord.setUserId(userId);
        drawRecord.setPrize(lockedPrize);
        return drawRecordRepository.save(drawRecord);
    }
} 