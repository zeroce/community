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

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

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
                             Long outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationTypeEnum.getType());
        notification.setOuterId(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiverAccountId);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);

        notificationMapper.insert(notification);
    }

    public PaginationDTO list(String userAccountId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        // 总行数
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userAccountId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
        // 计算分页参数
        paginationDTO.setPagination(totalCount, page, size);
        // 分页位置设置
        page = (page < 1) ? 1 : ((page > paginationDTO.getTotalPage()) ? paginationDTO.getTotalPage() : page);

        // size(page-1)
        Integer offset = size * (page - 1);
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userAccountId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        // 如果查不到，直接返回
        if (notifications.size() == 0) {
            return paginationDTO;
        }
        // 返回模型
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
//        Set<String> disUserAccountIds = notifications.stream().map(notify -> notify.getNotifier()).collect(Collectors.toSet());
//        List<String> userAccountIds = new ArrayList<>(disUserAccountIds);
//        UserExample userExample = new UserExample();
//        userExample.createCriteria().andAccountIdIn(userAccountIds);
//        List<User> users = userMapper.selectByExample(userExample);
//        Map<String, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getAccountId(), u -> u));


        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long unreadCount(String accountId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(accountId).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (null == notification) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getAccountId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        // 修改未读状态为已读状态
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
