package life.maijiang.community.service.impl;

import life.maijiang.community.dto.TagDTO;
import life.maijiang.community.mapper.TagFieldMapper;
import life.maijiang.community.mapper.TagMapper;
import life.maijiang.community.model.Tag;
import life.maijiang.community.model.TagExample;
import life.maijiang.community.model.TagField;
import life.maijiang.community.model.TagFieldExample;
import life.maijiang.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublishServiceImpl implements PublishService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagFieldMapper tagFieldMapper;

    public List<TagDTO<Tag>> getAllTags() {
        List<TagDTO<Tag>> tagDTOList = new ArrayList<>();
        List<TagField> tagFieldList = tagFieldMapper.selectByExample(new TagFieldExample());
        for (TagField tagField : tagFieldList) {
            TagExample example = new TagExample();
            example.createCriteria().andTypeEqualTo(tagField.getId());
            List<Tag> tagList = tagMapper.selectByExample(example);
            TagDTO<Tag> tagDTO = new TagDTO<>();
            tagDTO.setCatagoryName(tagField.getName());
            tagDTO.setTags(tagList);
            tagDTOList.add(tagDTO);
        }
        return tagDTOList;
    }
}
