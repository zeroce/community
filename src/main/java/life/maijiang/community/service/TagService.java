package life.maijiang.community.service;

import life.maijiang.community.mapper.TagMapper;
import life.maijiang.community.model.Tag;
import life.maijiang.community.model.TagExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagMapper tagMapper;

    public void createOrUpdate(Tag tag) {
        List<Tag> tagList = getListByName(tag.getName());
        if (null == tagList || 0 == tagList.size()) {
            // 插入记录
            tagMapper.insert(tag);
        } else {
            // 更新记录
            TagExample example = new TagExample();
            example.createCriteria().andNameEqualTo(tag.getName());
            tagMapper.updateByExampleSelective(tag, example);

        }

    }

    public List<Tag> getListByName(String name) {
        TagExample example = new TagExample();
        example.createCriteria().andNameEqualTo(name);
        
        List<Tag> tags = tagMapper.selectByExample(example);
        return tags;
    }

    public List<Tag> getListByType(Long type) {
        TagExample example = new TagExample();
        example.createCriteria().andTypeEqualTo(type);
        return tagMapper.selectByExample(example);
    }
}
