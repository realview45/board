package com.beyond.basic.board.author.dtos;

import com.beyond.basic.board.author.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDetailDto {
    private Long id;
    private String name;
    private String email;
    private int postCount;
    private Role role;
}
