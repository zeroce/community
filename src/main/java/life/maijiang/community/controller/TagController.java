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

    @ResponseBody
    @RequestMapping(value = "/tag/create", method = RequestMethod.POST)
    public Object add(@RequestBody TagSendDTO tagSendDTO) {
        if (tagSendDTO.getTagName().equals("")) {
            return ResultDTO.failOf("标题不能为空！请重新尝试并规范创建！");
        }
        tagService.createOrUpdate(tagSendDTO);
        return ResultDTO.okOf();
    }

    @GetMapping("/tag/list")
    public void get(@RequestParam(name = "type") Long type) {
        List<Tag> tagList = tagService.getListByType(type);

    }

}
