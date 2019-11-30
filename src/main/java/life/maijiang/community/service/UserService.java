package life.maijiang.community.service;

import life.maijiang.community.model.User;

public interface UserService {
    /**
     * 登录/注册
     * @param user
     */
    public void createOrUpdate(User user);
}
