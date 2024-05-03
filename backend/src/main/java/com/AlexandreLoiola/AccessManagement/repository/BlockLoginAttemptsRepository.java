package com.AlexandreLoiola.AccessManagement.repository;

import com.AlexandreLoiola.AccessManagement.model.BlockLoginAttemptsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlockLoginAttemptsRepository extends JpaRepository<BlockLoginAttemptsModel, UUID> {
    Optional<BlockLoginAttemptsModel> findByLoginAndIsBlockTrue(String login);
}
