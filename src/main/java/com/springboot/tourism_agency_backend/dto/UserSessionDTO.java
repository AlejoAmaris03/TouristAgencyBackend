package com.springboot.tourism_agency_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserSessionDTO {
    private Long dni;
    private String name;
    private String surname;
    private String token;
}
