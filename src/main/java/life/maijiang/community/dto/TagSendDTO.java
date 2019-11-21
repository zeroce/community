package life.maijiang.community.dto;

import lombok.Data;

@Data
public class TagSendDTO {
    private String tagName;
    private Long tagType;
    private String tagDescription;
}
