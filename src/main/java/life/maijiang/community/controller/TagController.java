package life.maijiang.community.controller;

import life.maijiang.community.dto.ResultDTO;
import life.maijiang.community.dto.TagSendDTO;
import life.maijiang.community.model.Tag;
import life.maijiang.community.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/tag/create")
    @ResponseBody
    public Object add(@RequestBody TagSendDTO tagSendDTO) {
        Tag tag = new Tag();
        tag.setName(tagSendDTO.getTagName());
        tag.setType(tagSendDTO.getTagType());
        tag.setGmtCreate(System.currentTimeMillis());
        tag.setGmtModified(tag.getGmtCreate());
        tagService.createOrUpdate(tag);
        return ResultDTO.okOf();
    }

    @GetMapping("/tag/list")
    public void get(@RequestParam(name = "type") Long type) {
        List<Tag> tagList = tagService.getListByType(type);

    }

}
