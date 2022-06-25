package com.project.board.domain.file;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.board.domain.comment.web.Comment;
import com.project.board.global.audit.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "comment_file_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class CommentFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_file_id")
    private Long id;

    // 전송 받은 파일 이름
    @Column(name = "comment_file_upload_file_name")
    private String uploadFileName;

    // UUID가 적용된 서버 저장용 파일 이름
    @Column(name = "comment_file_store_file_name")
    private String storeFileName;

    // 파일 저장 경로
    @Column(name = "comment_file_file_url")
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("commentfile-comment")
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    public CommentFile(Long id, String uploadFileName, String storeFileName, String fileUrl, Comment comment) {
        this.id = id;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.fileUrl = fileUrl;
        this.comment = comment;
    }
}
