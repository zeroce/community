package life.maijiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SettingsController {

    @RequestMapping(value = "/account/setting/basic", method = RequestMethod.GET)
    public String settingBasic() {

        return "basic";
    }
}
