package com.kiwadev.mocktest.models.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    public String username;
    public String password;
    public String name;
    public Long dateBirth;
}
