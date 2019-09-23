package life.maijiang.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        // 总页数
        totalPage = (totalCount % size == 0) ? (totalCount / size) : (totalCount / size + 1);
        // 传递分页数
        page = (page < 1) ? 1 : ((page > totalPage) ? totalPage : page);
        this.page = page;

        // 集合分页选择
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        // 是否展示上一页
        showPrevious = (page == 1) ? false : true;
        // 是否展示下一页
        showNext = (page == totalPage) ? false : true;
        // 是否展示第一页
        showFirstPage = (pages.contains(1)) ? false : true;
        // 是否展示最后一页
        showEndPage = (pages.contains(totalPage)) ? false : true;
    }
}
