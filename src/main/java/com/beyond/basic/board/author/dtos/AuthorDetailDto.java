package com.beyond.basic.board.author.dtos;

import com.beyond.basic.board.author.domain.Author;
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

    public static AuthorDetailDto fromEntity(Author author) {
        return AuthorDetailDto.builder().id(author.getId()).name(author.getName()).email(author.getEmail())
                .postCount(author.getPostList().size())
                .role(author.getRole()).build();
    }
}
