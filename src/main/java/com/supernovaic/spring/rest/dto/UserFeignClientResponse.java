package com.supernovaic.spring.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFeignClientResponse {
    String id;
    String name;
    String surname;
    Date dateOfBirth;
    String username;
}
