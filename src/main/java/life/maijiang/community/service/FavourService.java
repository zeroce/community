package life.maijiang.community.service;

import life.maijiang.community.mapper.FavourMapper;
import life.maijiang.community.model.Favour;
import life.maijiang.community.model.FavourExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavourService {

    @Autowired
    private FavourMapper favourMapper;

    /**
     * 点赞记录保存
     * @param favour
     */
    public void insertOrUpdateFavour(Favour favour) {
        FavourExample example = new FavourExample();
        example.createCriteria().andTypeEqualTo(favour.getType()).andTypeIdEqualTo(favour.getTypeId());
        List<Favour> favourList = favourMapper.selectByExample(example);
        if (null == favourList || 0 == favourList.size()) {
            // 生成记录并保存
            favourMapper.insert(favour);
        } else {
            // 更新记录
            favour.setGmtModified(String.valueOf(System.currentTimeMillis()));
            FavourExample updateExample = new FavourExample();
            Favour targetFavour = favourList.get(0);
            updateExample.createCriteria().andIdEqualTo(targetFavour.getId());
            Favour updateFavour = new Favour();
            updateFavour.setStatus(favour.getStatus());
            updateFavour.setGmtModified(String.valueOf(System.currentTimeMillis()));
            favourMapper.updateByExampleSelective(updateFavour, updateExample);
        }
    }

    /**
     * 点赞状态验证
     * @param targetId
     * @param targetType
     * @return
     */
    public Favour validateState(Long targetId, Integer targetType) {
        FavourExample example = new FavourExample();
        example.createCriteria().andTypeIdEqualTo(targetId).andTypeEqualTo(targetType);
        List<Favour> favourList = favourMapper.selectByExample(example);
        return favourList.get(0);
    }
}
