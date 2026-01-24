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
public class PostDetailDto {
    private Long id;
    private String title;
    private String contents;
    private String category;
    private String authorEmail;
    public static PostDetailDto fromEntity(Post p) {
        return PostDetailDto.builder().id(p.getId()).title(p.getTitle()).contents(p.getContents()).category(p.getCategory()).authorEmail(p.getAuthor().getEmail()).build();
    }
}
