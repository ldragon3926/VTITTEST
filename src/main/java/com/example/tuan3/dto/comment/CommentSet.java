package com.example.tuan3.dto.comment;

import com.example.tuan3.entity.Comment;
import com.example.tuan3.entity.Post;
import com.example.tuan3.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSet {
    private Long id;
    @NotBlank(message = "Không được để mã trống")
    private String content;
    @NotBlank(message = "Không được để postID trống")
    private Long postID;
    @NotBlank(message = "Không được để userID trống")
    private Long userID;
    private boolean status;

    public Comment dto(Comment comment) {
        comment.setId(this.getId());
        comment.setContent(this.getContent());
        comment.setPost(Post.builder().id(this.postID).build());
        comment.setUser(User.builder().id(this.userID).build());
        comment.setStatus(isStatus());
        return comment;
    }
}
