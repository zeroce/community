package life.maijiang.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class TagDTO {
    private String catagoryName;
    private List<String> tags;
}
