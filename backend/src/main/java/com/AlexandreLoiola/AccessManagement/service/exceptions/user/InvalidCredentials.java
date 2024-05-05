package com.AlexandreLoiola.AccessManagement.service.exceptions.user;

import com.AlexandreLoiola.AccessManagement.rest.form.BruteForceAttackerForm;
import com.AlexandreLoiola.AccessManagement.service.BlockLoginAttemptsService;
import org.springframework.security.core.AuthenticationException;

public class InvalidCredentials  extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    public InvalidCredentials(String msg) { super(msg); }

    public InvalidCredentials(String msg, BlockLoginAttemptsService blockLoginAttemptsService, String login, String password, String ipAddress) {
        super(msg);
        checkBruteForceAttack(blockLoginAttemptsService, login, password, ipAddress);
    }

    private void checkBruteForceAttack(BlockLoginAttemptsService blockLoginAttemptsService, String login, String password, String ipAddress) {
        if (blockLoginAttemptsService.isUnderBruteForceAttack(ipAddress)) {
            BruteForceAttackerForm bruteForceAttackerForm = new BruteForceAttackerForm();
            bruteForceAttackerForm.setLogin(login);
            bruteForceAttackerForm.setPassword(password);
            bruteForceAttackerForm.setIpAddress(ipAddress);
            blockLoginAttemptsService.registerAttack(bruteForceAttackerForm);
        }
    }
}

