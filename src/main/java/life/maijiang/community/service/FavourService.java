package life.maijiang.community.service;

import life.maijiang.community.model.Favour;

public interface FavourService {
    /**
     * 点赞记录保存
     * @param favour
     */
    void insertOrUpdateFavour(Favour favour);

    /**
     * 点赞状态验证
     * @param targetId
     * @param targetType
     * @return
     */

    Favour validateState(Long targetId, Integer targetType);
}
