package com.AlexandreLoiola.AccessManagement.repository;

import com.AlexandreLoiola.AccessManagement.model.BlockLoginAttemptsModel;
import com.AlexandreLoiola.AccessManagement.model.BlockTimeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlockTimeRepository extends JpaRepository<BlockTimeModel, UUID> {
    Optional<BlockTimeModel> findByTimeAndIsActiveTrue(String login);
}
