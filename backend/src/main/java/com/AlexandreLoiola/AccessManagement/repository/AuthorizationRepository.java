package com.AlexandreLoiola.AccessManagement.repository;

import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorizationRepository extends JpaRepository<AuthorizationModel, UUID> {

    Optional<AuthorizationModel> findByDescription(String description);
    Optional<AuthorizationModel> findByDescriptionAndIsActiveTrue(String description);
    List<AuthorizationModel> findByIsActiveTrue();

}
