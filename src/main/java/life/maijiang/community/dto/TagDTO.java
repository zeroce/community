package life.maijiang.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class TagDTO<T> {
    private String catagoryName;
    private List<T> tags;
}
