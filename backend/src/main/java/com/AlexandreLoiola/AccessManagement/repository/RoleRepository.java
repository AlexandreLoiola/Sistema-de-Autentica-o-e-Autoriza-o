package com.AlexandreLoiola.AccessManagement.repository;

import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

    Optional<RoleModel> findByDescription(String description);
    Optional<RoleModel> findByDescriptionAndIsActiveTrue(String description);
    List<RoleModel> findByIsActiveTrue();

}
