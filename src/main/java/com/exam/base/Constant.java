package com.exam.base;

import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author sunc
 * @date 2020/3/7 14:03
 */
public class Constant {
    public static final String HEADER_ACCESS_TOKEN = "Access-Token";

    public static final SerializerFeature[] JSON_FEATURE_WITH_NULL = {SerializerFeature.WriteDateUseDateFormat, SerializerFeature.IgnoreNonFieldGetter, SerializerFeature.WriteMapNullValue};

    public static final SerializerFeature[] JSON_FEATURE_WITHOUT_NULL = {SerializerFeature.WriteDateUseDateFormat, SerializerFeature.IgnoreNonFieldGetter};

    public static final Integer PAGE_SIZE = 30;

    public static final Integer PAGE_NUMBER = 1;

    public static final Integer MAX_PAGE_SIZE = 100;

    /**
     * admin 管理员
     **/
    public static final Long MGR_ID = 100001L;
    public static final String MGR_NUMBER = "100001";
    public static final String MGR_NAME = "admin";
    public static final String MGR_PASSWORD = "nimda";

    public enum QuestionLevel {
        // 试题的难度设置: 易,较易,适中,较难,难
        EASY, RELATIVE_EASY, NORMAL, RELATIVE_DIFFICULT, DIFFICULT;
    }

    public enum QuestionType {
        // 试题的类型设置
        SINGLE_CHOICE("单选题"), MULTI_CHOICE("多选题"), TRUE_OR_FALSE("判断题"),
        FILLING("填空题"), SUBJECTIVE("主观题");

        private String name;

        QuestionType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

}
