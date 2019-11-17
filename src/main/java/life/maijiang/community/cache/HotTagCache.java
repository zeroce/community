package life.maijiang.community.cache;

import life.maijiang.community.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class HotTagCache {
    private List<String> hots = new ArrayList<>();

    // 优先队列获得 TOP N
    public void updateTags(Map<String, Integer> tags) {
        // 优先队列的大小
        int max = 5;
        // 优先队列
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);
        tags.forEach((name, priority) -> {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            // 队列未满
            if (priorityQueue.size() < 5) {
                priorityQueue.add(hotTagDTO);
            } else {
                HotTagDTO miniHot = priorityQueue.peek();
                // 当前标签的权重 > 队列首元素（权重最小的元素）
                if (hotTagDTO.compareTo(miniHot) > 0) {
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });
        List<String> hotTags = new ArrayList<>();
        while (priorityQueue.size() != 0) {
            HotTagDTO poll = priorityQueue.poll();
            hotTags.add(0, poll.getName());
        }
        hots = hotTags;
    }
}
