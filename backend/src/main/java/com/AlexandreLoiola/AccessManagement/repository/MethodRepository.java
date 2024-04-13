package com.AlexandreLoiola.AccessManagement.repository;

import com.AlexandreLoiola.AccessManagement.model.MethodModel;
import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MethodRepository extends JpaRepository<MethodModel, UUID> {

    Optional<MethodModel> findByIdAndIsActiveTrue(UUID id);
    Optional<MethodModel> findByDescription(String description);
    Optional<MethodModel> findByDescriptionAndIsActiveTrue(String description);
    Set<MethodModel> findByIsActiveTrue();

}
