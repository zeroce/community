package life.maijiang.community.mapper;

import life.maijiang.community.model.Comment;

public interface CommentExtMapper {
    int incLikeCount(Comment comment);
    int incCommentedCount(Comment comment);
}
