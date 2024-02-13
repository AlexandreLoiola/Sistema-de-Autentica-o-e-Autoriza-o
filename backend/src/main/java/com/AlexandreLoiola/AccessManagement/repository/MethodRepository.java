package com.AlexandreLoiola.AccessManagement.repository;

import com.AlexandreLoiola.AccessManagement.model.MethodModel;
import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MethodRepository extends JpaRepository<MethodModel, UUID> {

    Optional<MethodModel> findByDescription(String description);
    Optional<MethodModel> findByDescriptionAndIsActiveTrue(String description);
    List<MethodModel> findByIsActiveTrue();

}
