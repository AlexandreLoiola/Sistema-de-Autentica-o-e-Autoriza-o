package com.AlexandreLoiola.AccessManagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "TB_BLOCK_LOGIN_ATTEMPTS")
@Data
@EqualsAndHashCode
public class BlockLoginAttemptsModel {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "ip", length = 40, nullable = false)
    private String ip;

    @Column(name = "login", length = 100, nullable = false)
    private String login;

    @ManyToOne
    @JoinColumn(name = "id_block_time", referencedColumnName = "id")
    private BlockTimeModel blockTimeModel;

    @Column(name = "is_active", nullable = false)
    private boolean isBlocked;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private long version;
}
