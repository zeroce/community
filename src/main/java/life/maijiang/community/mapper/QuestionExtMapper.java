package life.maijiang.community.mapper;

import life.maijiang.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);
    int intCommentCount(Question record);
    List<Question> selectByRelatedTag(Question record);
}