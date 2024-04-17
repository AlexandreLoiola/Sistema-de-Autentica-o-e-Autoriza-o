package com.AlexandreLoiola.AccessManagement.rest.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailForm {
    private String toEmail;
    private String subject;
    private String message;
}
