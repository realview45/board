package com.beyond.basic.board.post.domain;

import com.beyond.basic.board.author.domain.Author;
import com.beyond.basic.board.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity//jpa가 관리하도록 엔티티 위임
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length=3000)
    private String contents;
    private String category;
    @ManyToOne(fetch=FetchType.LAZY)//default:EAGER
    //fk설정시 아래옵션 Default NO_CONSTRAINT는 fk설정X 객체만 받아옴, nullable설정 db에 실제 author_id 생성
    //@JoinColumn(name="author_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT), nullable = false)
    private Author author;
    @Builder.Default
    private String delYn = "N";

    public void delete() {
        this.delYn="Y";
    }
}
