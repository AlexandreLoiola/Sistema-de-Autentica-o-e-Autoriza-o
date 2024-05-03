package com.AlexandreLoiola.AccessManagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name="TB_BLOCK_TIME")
public class BlockTimeModel {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "time", length = 10, nullable = false, unique = true)
    private int time;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}
