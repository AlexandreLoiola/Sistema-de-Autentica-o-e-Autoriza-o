package com.AlexandreLoiola.AccessManagement.repository;

import com.AlexandreLoiola.AccessManagement.model.PasswordAttemptsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface PasswordAttemptsRepository extends JpaRepository<PasswordAttemptsModel, UUID> {
    Set<PasswordAttemptsModel> findAllByBlockLoginAttempts_Id(UUID blockLoginAttemptsModelId);
}