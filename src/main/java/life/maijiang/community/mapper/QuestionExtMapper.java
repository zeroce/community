package life.maijiang.community.mapper;

import life.maijiang.community.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int intCommentCount(Question record);
}