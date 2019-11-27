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

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询帖子
     * @param page
     * @param size
     * @param sortedBy
     * @return
     */
    public PaginationDTO listBySorted(Integer page, Integer size, String sortedBy) {
        PaginationDTO paginationDTO = new PaginationDTO();
        // 设置查询条件
        QuestionExample questionExample = new QuestionExample();

        long currentTimeMillis = System.currentTimeMillis();
        if (null == sortedBy || sortedBy.equals("")) {
        } else if (sortedBy.equals("weekly-popular")) {
            questionExample.createCriteria().andGmtCreateBetween(currentTimeMillis - 7*24*60*60*1000L, currentTimeMillis);
        } else if (sortedBy.equals("monthly-popular")) {
            questionExample.createCriteria().andGmtCreateBetween(currentTimeMillis - 30*24*60*60*1000L, currentTimeMillis);
        } else if (sortedBy.equals("eliminate-zero-recovery")) {
            questionExample.createCriteria().andCommentCountEqualTo(0);
        }
        // 总行数
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
        // 计算分页参数
        paginationDTO.setPagination(totalCount, page, size);
        // 分页位置设置
        page = (page < 1) ? 1 : ((page > paginationDTO.getTotalPage()) ? paginationDTO.getTotalPage() : page);
        // size(page-1)
        Integer offset = size * (page - 1);
        // 排序条件
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    /**
     * 个人分页查询帖子
     * @param userAccountId
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(String userAccountId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        // 总行数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorAccountEqualTo(userAccountId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
        // 计算分页参数
        paginationDTO.setPagination(totalCount, page, size);
        // 分页位置设置
        page = (page < 1) ? 1 : ((page > paginationDTO.getTotalPage()) ? paginationDTO.getTotalPage() : page);

        // size(page-1)
        Integer offset = size * (page - 1);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorAccountEqualTo(userAccountId);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    /**
     * 搜索功能
     * @param page
     * @param size
     * @param search
     * @param tag
     * @return
     */
    public PaginationDTO list(Integer page, Integer size, String search, String tag) {
        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        // 总行数
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        questionQueryDTO.setTag(tag);
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
        // 计算分页参数
        paginationDTO.setPagination(totalCount, page, size);
        // 分页位置设置
        page = (page < 1) ? 1 : ((page > paginationDTO.getTotalPage()) ? paginationDTO.getTotalPage() : page);

        // size(page-1)
        Integer offset = size * (page - 1);
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);

        return paginationDTO;
    }

    /**
     * 问题的基本信息
     * @param id
     * @return
     */
    public Question findById(Long id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    /**
     * 问题详情
     * @param id
     * @return
     */
    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (null == question) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    /**
     * 问题帖子保存更新
     * @param question
     */
    public void createOrUpdate(Question question) {
        if (null == question.getId()) {
            // 创建帖子
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);

            questionMapper.insert(question);
        } else {
            // 更新
            Question updateQuestion = new Question();
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setGmtModified(System.currentTimeMillis());
            QuestionExample qusetionExample = new QuestionExample();
            qusetionExample.createCriteria().andIdEqualTo(question.getId());
            int update = questionMapper.updateByExampleSelective(updateQuestion, qusetionExample);
            if (update != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

        }
    }

    /**
     * 浏览次数计算
     * @param id
     */
    public void increaseView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> selectByRelatedTag(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionExtMapper.selectByRelatedTag(question);
        List<QuestionDTO> questionDTOs = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOs;
    }

    public List<Question> selectByCreatorAccount(String creatorAccount) {
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorAccountEqualTo(creatorAccount);
        return questionMapper.selectByExample(example);
    }

}
