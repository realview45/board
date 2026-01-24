package com.beyond.basic.board.author.dtos;

import com.beyond.basic.board.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorListDto {
    private Long id;
    private String name;
    private String email;

    public static AuthorListDto fromEntity(Author a) {
        return AuthorListDto.builder().id(a.getId()).name(a.getName()).email(a.getEmail()).build();
    }
}
