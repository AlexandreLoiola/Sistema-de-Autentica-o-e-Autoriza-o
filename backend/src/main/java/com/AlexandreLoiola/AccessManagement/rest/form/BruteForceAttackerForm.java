package com.AlexandreLoiola.AccessManagement.rest.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BruteForceAttackerForm {
    private String login;
    private String ipAddress;
    private String password;
}
