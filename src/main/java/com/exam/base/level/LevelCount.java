package com.exam.base.level;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sunc
 * @date 2020/3/14 19:16
 */
@Getter
@Setter
public class LevelCount {
    private Integer level;
    private Integer count;

    public LevelCount(Integer level) {
        this(level, 0);
    }

    public LevelCount(Integer level, Integer count) {
        this.level = level;
        this.count = count;
    }

}
