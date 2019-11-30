package life.maijiang.community.service.impl;

import life.maijiang.community.mapper.UserMapper;
import life.maijiang.community.model.User;
import life.maijiang.community.model.UserExample;
import life.maijiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录/注册
     * @param user
     */
    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            // 未注册，注册并登陆
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            // 已注册，直接登录

            user.setGmtModified(System.currentTimeMillis());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(users.get(0).getId());
            userMapper.updateByExampleSelective(user, example);
        }
    }
}
