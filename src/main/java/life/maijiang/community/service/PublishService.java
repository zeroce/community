package life.maijiang.community.service;

import life.maijiang.community.dto.TagDTO;
import life.maijiang.community.model.Tag;

import java.util.List;

public interface PublishService {

    /**
     * 获取所有标签及其领域
     * @return
     */
    public List<TagDTO<Tag>> getAllTags();
}
