package com.beyond.basic.board.post.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity//jpa가 관리하도록 엔티티 위임
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length=3000)
    private String contents;
    private String category;
    //private Author author;
    @Builder.Default
    private String delYn = "N";
}
