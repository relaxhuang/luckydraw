package com.relax.luckydraw.controller;

import com.relax.luckydraw.dto.DrawResponse;
import com.relax.luckydraw.model.DrawRecord;
import com.relax.luckydraw.service.LuckyDrawService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/api/lucky-draw", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
public class LuckyDrawController {
    private final LuckyDrawService luckyDrawService;

    public LuckyDrawController(LuckyDrawService luckyDrawService) {
        this.luckyDrawService = luckyDrawService;
    }

    @PostMapping(value = "/draw", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public ResponseEntity<DrawResponse> draw(@RequestParam String userId) {
        try {
            DrawRecord result = luckyDrawService.draw(userId);
            boolean isWinner = !"No Prize".equals(result.getPrize().getName());
            String message = isWinner ? 
                "恭喜您中獎了！" : 
                "很抱歉，這次沒有中獎，請再接再厲！";
            
            DrawResponse response = new DrawResponse(
                message,
                result.getPrize().getName(),
                result.getPrize().getDescription(),
                result.getDrawTime(),
                isWinner
            );
            
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
        } catch (RuntimeException e) {
            String errorMessage;
            if (e.getMessage().contains("You can only draw once per day")) {
                errorMessage = "您今天已經抽過獎了，請明天再來試試手氣！";
            } else if (e.getMessage().contains("No prizes available")) {
                errorMessage = "很抱歉，目前所有獎品都已經被抽完了！";
            } else if (e.getMessage().contains("Prize out of stock")) {
                errorMessage = "很抱歉，該獎品已經被抽完了，請重新抽獎！";
            } else {
                errorMessage = "系統發生錯誤，請稍後再試！";
            }
            
            DrawResponse response = new DrawResponse(
                errorMessage,
                null,
                null,
                LocalDateTime.now(),
                false
            );
            
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
        }
    }
} 