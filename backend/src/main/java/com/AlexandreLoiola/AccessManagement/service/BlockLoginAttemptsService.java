package com.AlexandreLoiola.AccessManagement.service;

import com.AlexandreLoiola.AccessManagement.model.BlockLoginAttemptsModel;
import com.AlexandreLoiola.AccessManagement.model.PasswordAttemptsModel;
import com.AlexandreLoiola.AccessManagement.repository.BlockLoginAttemptsRepository;
import com.AlexandreLoiola.AccessManagement.repository.BlockTimeRepository;
import com.AlexandreLoiola.AccessManagement.repository.PasswordAttemptsRepository;
import com.AlexandreLoiola.AccessManagement.rest.form.BruteForceAttackerForm;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class BlockLoginAttemptsService {

    private static final int MAX_ATTEMPT = 5;
    private final LoadingCache<String, Integer> attemptsCache;

    private final BlockLoginAttemptsRepository blockLoginAttemptsRepository;
    private final BlockTimeRepository blockTimeRepository;
    private final PasswordAttemptsRepository passwordAttemptsRepository;

    public BlockLoginAttemptsService(BlockLoginAttemptsRepository blockLoginAttemptsRepository, BlockTimeRepository blockTimeRepository, PasswordAttemptsRepository passwordAttemptsRepository) {
        this.blockLoginAttemptsRepository = blockLoginAttemptsRepository;
        this.blockTimeRepository = blockTimeRepository;
        this.passwordAttemptsRepository = passwordAttemptsRepository;
        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public boolean isUnderBruteForceAttack(String ipAddress) {
        int attempts = attemptsCache.getUnchecked(ipAddress);
        attempts++;
        attemptsCache.put(ipAddress, attempts);
        if (attempts >= MAX_ATTEMPT) {
            return true;
        }
        return false;
    }

    BlockLoginAttemptsModel findBlockLoginAttemptsModelByLogin(String login) {
        return blockLoginAttemptsRepository.findByLoginAndIsBlockTrue(login)
                .orElseThrow(() -> new NotFoundException("Not found block to login"));
    }

    Set<PasswordAttemptsModel> findPasswordAttemptsByIdBlockLogin(UUID blockLoginAttemptsModelId) {
        return passwordAttemptsRepository.findAllByBlockLoginAttempts_Id(blockLoginAttemptsModelId);
    }

    void blockIpAddress(BruteForceAttackerForm bruteForceAttackerForm) {
        BlockLoginAttemptsModel blockLoginAttemptsModel = findBlockLoginAttemptsModelByLogin(bruteForceAttackerForm.getLogin());
        setBlockTime(blockLoginAttemptsModel.getId());
        registerPassword(blockLoginAttemptsModel, bruteForceAttackerForm.getPassword());
    }

    public void registerAttack(BruteForceAttackerForm bruteForceAttackerForm) {

    }

    @Transactional
    void registerPassword(BlockLoginAttemptsModel blockLoginAttemptsModel, String password) {
        PasswordAttemptsModel passwordAttemptsModel = new PasswordAttemptsModel();
        passwordAttemptsModel.setPassword(password);
        passwordAttemptsModel.setCreatedAt(new Date());
        passwordAttemptsModel.setBlockLoginAttempts(blockLoginAttemptsModel);
        passwordAttemptsRepository.save(passwordAttemptsModel);
    }

    @Transactional
    void setBlockTime(UUID blockLoginAttemptsModelId) {
        // busca no banco de dados o ultimo tempo em que o usuário foi bloqueado.
        // Caso seja nulo, setta a primeira
    }
}
