package life.maijiang.community.controller;

import life.maijiang.community.dto.FavourSetDTO;
import life.maijiang.community.dto.ResultDTO;
import life.maijiang.community.model.Favour;
import life.maijiang.community.model.User;
import life.maijiang.community.service.FavourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FavourController {

    @Autowired
    private FavourService favourService;

    /**
     * 点赞记录生成/修改
     * @param favourSetDTO
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/favour", method = RequestMethod.POST)
    public Object post(@RequestBody FavourSetDTO favourSetDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Favour favour = new Favour();
        favour.setStatus(favourSetDTO.getStatus());
        favour.setUserAccountId(user.getAccountId());
        favour.setType(favourSetDTO.getTargetType());
        favour.setTypeId(favourSetDTO.getTargetId());
        favour.setGmtCreate(String.valueOf(System.currentTimeMillis()));
        favour.setGmtModified(favour.getGmtCreate());
        favourService.insertOrUpdateFavour(favour);
        return ResultDTO.okOf();
    }

    /**
     * 评论的点赞状态
     * @param favourSetDTO
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/favour/status", method = RequestMethod.POST)
    public Object validateState(@RequestBody FavourSetDTO favourSetDTO,
                                HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.okOf();
        }
        Favour result = favourService.validateState(favourSetDTO.getTargetId(), favourSetDTO.getTargetType());
        return ResultDTO.okOf(result.getStatus());
    }

}
