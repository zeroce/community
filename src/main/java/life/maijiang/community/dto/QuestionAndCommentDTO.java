package life.maijiang.community.dto;

import life.maijiang.community.model.Comment;
import life.maijiang.community.model.Question;
import lombok.Data;

@Data
public class QuestionAndCommentDTO {
    private Question question;
    private Comment comment;
}
