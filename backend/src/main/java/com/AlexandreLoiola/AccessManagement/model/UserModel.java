package com.AlexandreLoiola.AccessManagement.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name="TB_USER")
public class UserModel {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition="uuid")
    private UUID id;

    @Column(name = "description", nullable = false, unique = true)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @Column(name = "version", nullable = false)
    private String version;
}
