package com.relax.luckydraw.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "draw_records")
public class DrawRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String userId;
    
    @ManyToOne
    @JoinColumn(name = "prize_id")
    private Prize prize;
    
    private LocalDateTime drawTime;
    
    @PrePersist
    public void prePersist() {
        if (drawTime == null) {
            drawTime = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Prize getPrize() {
        return prize;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public LocalDateTime getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(LocalDateTime drawTime) {
        this.drawTime = drawTime;
    }
} 