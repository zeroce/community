package life.maijiang.community.service;

import life.maijiang.community.dto.NotificationDTO;
import life.maijiang.community.dto.PaginationDTO;
import life.maijiang.community.enums.NotificationStatusEnum;
import life.maijiang.community.enums.NotificationTypeEnum;
import life.maijiang.community.exception.CustomizeErrorCode;
import life.maijiang.community.exception.CustomizeException;
import life.maijiang.community.mapper.NotificationMapper;
import life.maijiang.community.mapper.UserMapper;
import life.maijiang.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

public interface NotificationService {
    /**
     * 保存消息通知记录
     * @param comment
     * @param receiverAccountId
     * @param notifierName
     * @param outerTitle
     * @param notificationTypeEnum
     * @param outerId
     */
    public void createNotify(Comment comment, String receiverAccountId, String notifierName,
                             String outerTitle, NotificationTypeEnum notificationTypeEnum,
                             Long outerId);
    /**
     * 消息通知列表
     * @param userAccountId
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(String userAccountId, Integer page, Integer size);
    /**
     * 消息未读
     * @param accountId
     * @return
     */
    public Long unreadCount(String accountId);
    /**
     * 消息已读
     * @param id
     * @param user
     * @return
     */
    public NotificationDTO read(Long id, User user);
}
