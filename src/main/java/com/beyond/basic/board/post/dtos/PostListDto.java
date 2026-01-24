package com.beyond.basic.board.post.dtos;

import com.beyond.basic.board.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListDto {
    private Long id;
    private String title;
    private String category;
    private String authorEmail;
    public static PostListDto fromEntity(Post p) {
        return PostListDto.builder().id(p.getId()).title(p.getTitle()).category(p.getCategory()).authorEmail(p.getAuthor().getEmail()).build();
    }
}
