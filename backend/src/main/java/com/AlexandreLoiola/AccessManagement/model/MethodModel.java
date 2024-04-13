package com.AlexandreLoiola.AccessManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name="TB_METHOD")
public class MethodModel {
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
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @Version
    @Column(name = "version", nullable = false)
    private long version;

    @JsonBackReference
    @ManyToMany(mappedBy = "methods")
    private Set<AuthorizationModel> authorizations = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodModel)) return false;
        MethodModel that = (MethodModel) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}