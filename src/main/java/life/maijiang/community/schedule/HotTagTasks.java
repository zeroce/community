package life.maijiang.community.schedule;

import life.maijiang.community.cache.HotTagCache;
import life.maijiang.community.mapper.QuestionMapper;
import life.maijiang.community.model.Question;
import life.maijiang.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 20000)
    public void hotTagSchedule() {
        int offset = 0;
        int limit = 5;
        List<Question> list = new ArrayList<>();
        Map<String, Integer> prioritiesTag = new HashMap<>();
        while (offset == 0 || list.size() == limit) {
            QuestionExample example = new QuestionExample();
            list = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, limit));
            for (Question question : list) {

                String tag = question.getTag();
                String[] tags = StringUtils.split(tag, ",");
                for (String s : tags) {
                    Integer priority = prioritiesTag.get(tag);
                    if (priority != null) {
                        prioritiesTag.put(tag, priority + 5 + question.getCommentCount());
                    } else {
                        prioritiesTag.put(tag, 5 + question.getCommentCount());
                    }
                }
            }
            offset += limit;
        }

        hotTagCache.updateTags(prioritiesTag);
    }
}
