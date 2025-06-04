package com.relax.luckydraw.dto;

import java.time.LocalDateTime;

public class DrawResponse {
    private String message;
    private String prizeName;
    private String description;
    private LocalDateTime drawTime;
    private boolean isWinner;

    public DrawResponse(String message, String prizeName, String description, LocalDateTime drawTime, boolean isWinner) {
        this.message = message;
        this.prizeName = prizeName;
        this.description = description;
        this.drawTime = drawTime;
        this.isWinner = isWinner;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(LocalDateTime drawTime) {
        this.drawTime = drawTime;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }
} 