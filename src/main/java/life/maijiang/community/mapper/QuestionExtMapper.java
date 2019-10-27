package life.maijiang.community.mapper;

import life.maijiang.community.dto.QuestionQueryDTO;
import life.maijiang.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    // 增加被阅读数
    int incView(Question record);
    // 增加被评论数
    int intCommentCount(Question record);
    // 标签相近
    List<Question> selectByRelatedTag(Question record);
    // 搜索结果数量
    Integer countBySearch(QuestionQueryDTO questionQueryDTO);
    // 搜索结果
    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}