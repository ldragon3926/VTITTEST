package com.example.tuan3.dto.post;

import com.example.tuan3.entity.Comment;
import com.example.tuan3.entity.Post;
import com.example.tuan3.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSet {
    private Long id;
    @NotBlank(message = "Không được để mã trống")
    private String title;
    @NotBlank(message = "Không được để mã trống")
    private String content;
    @NotBlank(message = "Không được để mã trống")
    private String publisher;
    @NotBlank(message = "Không được để userID trống")
    private Long userID;
    private boolean status;

    public Post dto(Post post) {
        post.setId(this.getId());
        post.setTitle(this.getContent());
        post.setContent(this.getContent());
        post.setPublisher(this.getPublisher());
        post.setUser(User.builder().id(this.userID).build());
        post.setStatus(isStatus());
        return post;
    }
}
