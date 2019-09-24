package life.maijiang.community.mapper;

import life.maijiang.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title, description, gmt_create, gmt_modified, creator, tag, creator_account) values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag}, #{creatorAccount})")
    void create(Question question);

    @Select("select * from question limit #{offset}, #{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator_account = #{userAccountId} limit #{offset}, #{size}")
    List<Question> listByUserId(@Param(value = "userAccountId") String userAccountId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question where creator_account = #{userAccountId}")
    Integer countByUserId(@Param(value = "userAccountId") String userAccountId);
}
