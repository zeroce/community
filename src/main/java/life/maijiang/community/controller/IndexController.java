package life.maijiang.community.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import life.maijiang.community.mapper.UserMapper;
import life.maijiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/index")
    public String index(HttpServletRequest request,
                        @RequestParam(name = "name", required = false, defaultValue = "world") String name,
                        Model model) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length != 0 ) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (null != user) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        return "index";
    }
}
