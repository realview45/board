package com.beyond.basic.board.author.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorCreateDto {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
