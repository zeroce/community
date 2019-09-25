package life.maijiang.community.service;

import life.maijiang.community.mapper.UserMapper;
import life.maijiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 登录/注册
     * @param user
     */
    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if (null == userMapper.findByAccountId(user.getAccountId())) {
            // 未注册，注册并登陆
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            // 已注册，直接登录
            user.setGmtModified(System.currentTimeMillis());
            userMapper.update(user);
        }
    }
}
