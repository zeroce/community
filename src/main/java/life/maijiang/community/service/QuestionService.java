package life.maijiang.community.service;

import life.maijiang.community.dto.PaginationDTO;
import life.maijiang.community.dto.QuestionDTO;
import life.maijiang.community.dto.QuestionQueryDTO;
import life.maijiang.community.exception.CustomizeErrorCode;
import life.maijiang.community.exception.CustomizeException;
import life.maijiang.community.mapper.QuestionExtMapper;
import life.maijiang.community.mapper.QuestionMapper;
import life.maijiang.community.mapper.UserMapper;
import life.maijiang.community.model.Question;
import life.maijiang.community.model.QuestionExample;
import life.maijiang.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface QuestionService {

    /**
     * 分页查询帖子
     * @param page
     * @param size
     * @param sortedBy
     * @return
     */
    public PaginationDTO listBySorted(Integer page, Integer size, String sortedBy);
    /**
     * 个人分页查询帖子
     * @param userAccountId
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(String userAccountId, Integer page, Integer size);
    /**
     * 搜索功能
     * @param page
     * @param size
     * @param search
     * @param tag
     * @return
     */
    public PaginationDTO list(Integer page, Integer size, String search, String tag);
    /**
     * 问题的基本信息
     * @param id
     * @return
     */
    public Question findById(Long id);
    /**
     * 问题详情
     * @param id
     * @return
     */
    public QuestionDTO getById(Long id);
    /**
     * 问题帖子保存更新
     * @param question
     */
    public void createOrUpdate(Question question);
    /**
     * 浏览次数计算
     * @param id
     */
    public void increaseView(Long id);
    /**
     * 根据标签查找
     * @param queryDTO
     * @return
     */
    public List<QuestionDTO> selectByRelatedTag(QuestionDTO queryDTO);
    /**
     * 根据问题发帖人ID查找
     * @param creatorAccount
     * @return
     */
    public List<Question> selectByCreatorAccount(String creatorAccount);

}
