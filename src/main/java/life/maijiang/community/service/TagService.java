package life.maijiang.community.service;

import life.maijiang.community.dto.TagDTO;
import life.maijiang.community.dto.TagSendDTO;
import life.maijiang.community.mapper.TagFieldMapper;
import life.maijiang.community.mapper.TagMapper;
import life.maijiang.community.model.Tag;
import life.maijiang.community.model.TagExample;
import life.maijiang.community.model.TagField;
import life.maijiang.community.model.TagFieldExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagFieldMapper tagFieldMapper;

    @Autowired
    private PublishService publishService;

    public void createOrUpdate(TagSendDTO tagSendDTO) {
        Tag tag = new Tag();
        tag.setName(tagSendDTO.getTagName());
        tag.setGmtCreate(System.currentTimeMillis());
        tag.setGmtModified(tag.getGmtCreate());
        List<Tag> tagList = getListByName(tag.getName());
        if (null == tagList || 0 == tagList.size()) {
            // 插入记录
            TagFieldExample example = new TagFieldExample();
            example.createCriteria().andNameEqualTo(tagSendDTO.getTagTypeName());
            List<TagField> tagFields = tagFieldMapper.selectByExample(example);
            tag.setType(tagFields.get(0).getId());
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

    public String filterIsValid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDTO<Tag>> tagDTOS = publishService.getAllTags();
        List<Tag> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }

}
