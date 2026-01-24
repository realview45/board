package com.beyond.basic.board.author.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity//jpa가 관리하도록 엔티티 위임
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Author {
    @Id//primary key설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment설정
    private Long id;
    private String name;
    @Column(length=50, unique = true, nullable = false)
    private String email;
    private String password;
    @Builder.Default
    private Role role = Role.USER;
    //private List<Post> postList;
}
