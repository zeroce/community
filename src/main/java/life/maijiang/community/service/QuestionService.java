package life.maijiang.community.service;

import life.maijiang.community.dto.PaginationDTO;
import life.maijiang.community.dto.QuestionDTO;
import life.maijiang.community.mapper.QuestionMapper;
import life.maijiang.community.mapper.UserMapper;
import life.maijiang.community.model.Question;
import life.maijiang.community.model.QuestionExample;
import life.maijiang.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询帖子
     *
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        // 总行数
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
        // 计算分页参数
        paginationDTO.setPagination(totalCount, page, size);
        // 分页位置设置
        page = (page < 1) ? 1 : ((page > paginationDTO.getTotalPage()) ? paginationDTO.getTotalPage() : page);

        // size(page-1)
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    /**
     * 个人分页查询帖子
     *
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
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    /**
     * 问题详情
     *
     * @param id
     * @return
     */
    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (null == question.getId()) {
            // 创建帖子
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
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
            questionMapper.updateByExampleSelective(updateQuestion, qusetionExample);

        }
    }

}
