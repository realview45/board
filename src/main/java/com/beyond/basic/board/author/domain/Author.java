package com.beyond.basic.board.author.domain;

import com.beyond.basic.board.common.domain.BaseTimeEntity;
import com.beyond.basic.board.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity//jpa가 관리하도록 엔티티 위임
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Author extends BaseTimeEntity {
    @Id//primary key설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto_increment설정
    private Long id;
    private String name;
    @Column(length=50, unique = true, nullable = false)
    private String email;
    private String password;
    //    enum타입은 내부적으로 숫자값을 가지고 있으나, 문자형태로 저장하겠다는 어노테이션
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)//, cascade = CascadeType.ALL)//defaultLAZY
    private List<Post> postList;

    private String profileImageUrl;
    public void updatePw(String password) {
        this.password = password;
    }

    public void updateProfileImageUrl(String url) {
        profileImageUrl = url;
    }
}
