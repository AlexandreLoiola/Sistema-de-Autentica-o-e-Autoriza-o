package com.AlexandreLoiola.AccessManagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "TB_PASSWORD_ATTEMPTS")
@Data
@EqualsAndHashCode
public class PasswordAttemptsModel {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "id_block_login_attempts", referencedColumnName = "id")
    private BlockLoginAttemptsModel blockLoginAttempts;
}
