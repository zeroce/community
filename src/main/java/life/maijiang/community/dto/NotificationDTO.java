package life.maijiang.community.dto;

import life.maijiang.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private String notifier;
    private String notifierName;
    private Long outerId;
    private String outerTitle;
    private Integer type;
    private String typeName;
}
