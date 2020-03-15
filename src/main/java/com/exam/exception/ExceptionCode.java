package com.exam.exception;

/**
 * 各种错误码定义
 *
 * @author sunc
 * @date 2020/3/7 16:25
 */

public final class ExceptionCode {

    /**
     * 参数基础校验类错误码 10001~10100 如姓名不能为空等等
     */
    public static final int NOT_DEFINE_ERROR = LocalException.DEFAULT_ERROR + 1;
    public static final int OBJECT_NULL_ERROR = LocalException.DEFAULT_ERROR + 2;

    /**
     * 试卷中有重复序号的试题
     */
    public static final int SAME_INDEX_ERROR = LocalException.DEFAULT_ERROR + 9;

    /**
     * 业务校验类错误码 10101~10300 如用户不存在, 无权限等等
     */
    public static final int DEPENDENCY_ERROR = LocalException.DEFAULT_ERROR + 150;
    public static final int USERNAME_EXIST_ERROR = LocalException.DEFAULT_ERROR + 161;
    public static final int USERNAME_NOT_EXIST_ERROR = LocalException.DEFAULT_ERROR + 162;
    public static final int USERNAME_NULL_ERROR = LocalException.DEFAULT_ERROR + 163;
    public static final int PASSWORD_NULL_ERROR = LocalException.DEFAULT_ERROR + 166;
    public static final int PASSWORD_TOO_SHORT_ERROR = LocalException.DEFAULT_ERROR + 167;
    public static final int PASSWORD_TOO_LONG_ERROR = LocalException.DEFAULT_ERROR + 168;
    public static final int PASSWORD_NOT_SAFE_ERROR = LocalException.DEFAULT_ERROR + 169;
    public static final int PASSWORD_ERROR = LocalException.DEFAULT_ERROR + 171;
    public static final int NOT_LOGIN_ERROR = LocalException.DEFAULT_ERROR + 174;
    public static final int LOGIN_ERROR = LocalException.DEFAULT_ERROR + 175;

    public static final int PAPER_NOT_PUBLISH_ERROR = LocalException.DEFAULT_ERROR + 176;

    public static final int FILE_CONTENT_ERROR = LocalException.DEFAULT_ERROR + 210;
    public static final int FILE_UPLOAD_ERROR = LocalException.DEFAULT_ERROR + 211;
    public static final int FILE_SIZE_ERROR = LocalException.DEFAULT_ERROR + 212;

    public static final int NO_PERMISSION_ERROR = LocalException.DEFAULT_ERROR + 299;

    /**
     * 非业务类错误码 10401~10500 如数据库操作失败, 请求接收数据异常等等
     */
    public static final int NOT_FOUND = LocalException.DEFAULT_ERROR + 401;
    public static final int SERVER_ERROR = LocalException.DEFAULT_ERROR + 402;
    public static final int BAD_GATEWAY = LocalException.DEFAULT_ERROR + 403;
    public static final int PARAM_ERROR = LocalException.DEFAULT_ERROR + 404;
    public static final int METHOD_ERROR = LocalException.DEFAULT_ERROR + 405;
    public static final int INVALID_REQUEST_ERROR = LocalException.DEFAULT_ERROR + 406;

    public static final int SQL_ERROR = LocalException.DEFAULT_ERROR + 420;

}
