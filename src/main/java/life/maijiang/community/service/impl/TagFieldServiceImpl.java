package life.maijiang.community.service.impl;

import life.maijiang.community.mapper.TagFieldMapper;
import life.maijiang.community.model.TagField;
import life.maijiang.community.model.TagFieldExample;
import life.maijiang.community.service.TagFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagFieldServiceImpl implements TagFieldService {

    @Autowired
    private TagFieldMapper tagFieldMapper;

    public List<TagField> getAllList() {
        return tagFieldMapper.selectByExample(new TagFieldExample());
    }

}
