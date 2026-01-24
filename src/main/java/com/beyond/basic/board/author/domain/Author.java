package com.beyond.basic.board.author.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String email;
    private String password;
    private Role role;
}
