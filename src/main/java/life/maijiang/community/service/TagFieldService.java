package life.maijiang.community.service;

import life.maijiang.community.model.TagField;

import java.util.List;

public interface TagFieldService {
    /**
     * 获取标签可选的所有领域
     * @return
     */
    public List<TagField> getAllList();

}
