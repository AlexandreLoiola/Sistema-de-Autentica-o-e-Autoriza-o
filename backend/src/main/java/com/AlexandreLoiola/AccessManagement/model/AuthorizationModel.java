package com.AlexandreLoiola.AccessManagement.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name="TB_AUTHORIZATION")
public class AuthorizationModel {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition="uuid")
    private UUID id;

    @Column(name = "description", length = 100, nullable = false, unique = true)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @Version
    @Column(name = "version", nullable = false)
    private long version;

    @ManyToMany
    @JoinTable(
            name="TB_AUTHORIZATION_METHOD",
            joinColumns = {@JoinColumn(name = "id_authorization", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "id_method")
    )
    private Set<MethodModel> methods = new HashSet<>();

    @ManyToMany(mappedBy = "authorizations")
    private Set<RoleModel> roles = new HashSet<>();
}