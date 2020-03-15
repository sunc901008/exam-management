package com.exam.base.level;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Getter;

import java.util.List;

/**
 * 难度分析设置
 *
 * @author sunc
 * @date 2020/3/14 17:54
 */
@Getter
public class LevelDesign {

    private int level;

    private LevelDesign(int level) {
        this.level = level;
    }

    /**
     * 0~4 分别对应难度: 易,较易,适中,较难,难
     * 根据整体难度, 分配各个难度的比例
     * 比如:
     * 整体难度为 0 (易), 则50%是易的题目, 40%是较易题目, 10%是适中题目, 0%是较难题目, 0%是难题目
     * 整体难度为 1 (较易), 则40%是易的题目, 30%是较易题目, 20%是适中题目, 10%是较难题目, 0%是难题目
     * 整体难度为 2 (适中), 则10%是易的题目, 10%是较易题目, 60%是适中题目, 10%是较难题目, 10%是难题目
     * 整体难度为 3 (较难), 则10%是易的题目, 10%是较易题目, 30%是适中题目, 30%是较难题目, 20%是难题目
     * 整体难度为 4 (难), 则0%是易的题目, 10%是较易题目, 20%是适中题目, 40%是较难题目, 30%是难题目
     */
    private List<LevelCount> level0Count(Integer count) {
        List<LevelCount> res = CollectionUtil.newArrayList();
        LevelCount levelCount0 = new LevelCount(0, Math.round(count * 0.5f));
        res.add(levelCount0);
        count = count - levelCount0.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount1 = new LevelCount(1, Math.round(count * 4.0f / 5));
        res.add(levelCount1);
        count = count - levelCount1.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount2 = new LevelCount(2, count);
        res.add(levelCount2);
        return res;
    }

    private List<LevelCount> level1Count(Integer count) {
        List<LevelCount> res = CollectionUtil.newArrayList();
        LevelCount levelCount0 = new LevelCount(0, Math.round(count * 0.4f));
        res.add(levelCount0);
        count = count - levelCount0.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount1 = new LevelCount(1, Math.round(count * 3.0f / 6));
        res.add(levelCount1);
        count = count - levelCount1.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount2 = new LevelCount(2, Math.round(count * 2.0f / 3));
        res.add(levelCount2);
        count = count - levelCount2.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount3 = new LevelCount(3, count);
        res.add(levelCount3);
        return res;
    }

    private List<LevelCount> level2Count(Integer count) {
        List<LevelCount> res = CollectionUtil.newArrayList();
        LevelCount levelCount0 = new LevelCount(0, Math.round(count * 0.1f));
        res.add(levelCount0);
        count = count - levelCount0.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount1 = new LevelCount(1, Math.round(count * 1.0f / 9));
        res.add(levelCount1);
        count = count - levelCount1.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount2 = new LevelCount(2, Math.round(count * 6.0f / 8));
        res.add(levelCount2);
        count = count - levelCount2.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount3 = new LevelCount(3, Math.round(count * 1.0f / 2));
        res.add(levelCount3);
        count = count - levelCount3.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount4 = new LevelCount(4, count);
        res.add(levelCount4);
        return res;

    }

    private List<LevelCount> level3Count(Integer count) {
        List<LevelCount> res = CollectionUtil.newArrayList();
        LevelCount levelCount0 = new LevelCount(0, Math.round(count * 0.1f));
        res.add(levelCount0);
        count = count - levelCount0.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount1 = new LevelCount(1, Math.round(count * 1.0f / 9));
        res.add(levelCount1);
        count = count - levelCount1.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount2 = new LevelCount(2, Math.round(count * 3.0f / 8));
        res.add(levelCount2);
        count = count - levelCount2.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount3 = new LevelCount(3, Math.round(count * 3.0f / 5));
        res.add(levelCount3);
        count = count - levelCount3.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount4 = new LevelCount(4, count);
        res.add(levelCount4);
        return res;

    }

    private List<LevelCount> level4Count(Integer count) {
        List<LevelCount> res = CollectionUtil.newArrayList();
        LevelCount levelCount0 = new LevelCount(0);
        res.add(levelCount0);
        LevelCount levelCount1 = new LevelCount(1, Math.round(count * 1.0f));
        res.add(levelCount1);
        count = count - levelCount1.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount2 = new LevelCount(2, Math.round(count * 2.0f / 9));
        res.add(levelCount2);
        count = count - levelCount2.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount3 = new LevelCount(3, Math.round(count * 4.0f / 7));
        res.add(levelCount3);
        count = count - levelCount3.getCount();
        if (count == 0) {
            return res;
        }
        LevelCount levelCount4 = new LevelCount(4, count);
        res.add(levelCount4);
        return res;

    }

    /**
     * 根据总数量, 分配各个难度题目的数量
     */
    public List<LevelCount> getCount(Integer count) {
        if (count <= 0) {
            return CollectionUtil.newArrayList();
        }
        switch (this.level) {
            case 0:
                return level0Count(count);
            case 1:
                return level1Count(count);
            case 3:
                return level3Count(count);
            case 4:
                return level4Count(count);
            default:
                return level2Count(count);
        }
    }

    public static LevelDesign paperLevel(Integer level) {
        int l = formatLevel(level);
        return new LevelDesign(l);
    }

    private static int formatLevel(Integer level) {
        if (level == null) {
            return 2;
        }
        if (level < 0) {
            return 0;
        }
        if (level > 4) {
            return 4;
        }
        return level;
    }

}
