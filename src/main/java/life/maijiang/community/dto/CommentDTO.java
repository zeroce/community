package life.maijiang.community.dto;

import life.maijiang.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private String commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Long commentedCount;
    private String content;
    private User user;
}
