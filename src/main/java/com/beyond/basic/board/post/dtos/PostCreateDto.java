package com.beyond.basic.board.post.dtos;

import com.beyond.basic.board.author.domain.Author;
import com.beyond.basic.board.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String contents;
    private String category;
    @NotBlank//로그인해야 게시글 작성가능
//    private String authorEmail;

    public Post toEntity(Author author) {
        return Post.builder().title(this.title).contents(this.contents).category(this.category).author(author).build();
    }
}
