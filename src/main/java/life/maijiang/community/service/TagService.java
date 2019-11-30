package life.maijiang.community.service;

import life.maijiang.community.dto.TagSendDTO;
import life.maijiang.community.model.Tag;

import java.util.List;
import java.util.Map;

public interface TagService {
    /**
     * 创建或修改标签
     * @param tagSendDTO
     */
    public void createOrUpdate(TagSendDTO tagSendDTO);

    /**
     * 根据标签名查找
     * @param name
     * @return
     */
    public List<Tag> getListByName(String name);

    /**
     * 根据领域查找
     * @param type
     * @return
     */
    public List<Tag> getListByType(Long type);

    /**
     * 标签过滤
     * @param tags
     * @return
     */
    public String filterIsValid(String tags);

    /**
     * 标签优先级排序
     * @param tags
     * @return
     */
//    public List<String> updateHotTags(Map<String, Integer> tags);


}
