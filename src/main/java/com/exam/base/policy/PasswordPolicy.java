package com.exam.base.policy;

import cn.hutool.core.lang.Console;
import com.exam.exception.ExceptionCode;
import com.exam.exception.LocalException;

import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * 密码策略, 校验密码
 *
 * @author sunc
 * @date 2020/3/7 15:59
 */

public class PasswordPolicy {

    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "1234567890";
    private static final String SYMBOLS = "`~!@#$%^&*()_+-={}|[]\\:\";'<>?,./";
    private static final int MIN = 6;
    private static final int MAX = 18;
    private static final int LOG_ROUNDS = 11;

    private static final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    public static void validate(String password) throws LocalException {
        int len = password.length();
        if (len < MIN) {
            throw new LocalException(ExceptionCode.PASSWORD_TOO_SHORT_ERROR, password);
        }

        if (len > MAX) {
            throw new LocalException(ExceptionCode.PASSWORD_TOO_LONG_ERROR, password);
        }

        int letterCount = 0;
        int numberCount = 0;
        int symbolCount = 0;
        for (int i = 0; i < len; i++) {
            String ch = password.charAt(i) + "";
            if (LETTERS.contains(ch.toLowerCase())) {
                letterCount++;
            } else if (NUMBERS.contains(ch)) {
                numberCount++;
            } else if (SYMBOLS.contains(ch)) {
                symbolCount++;
            } else {
                throw new LocalException(ExceptionCode.PASSWORD_NOT_SAFE_ERROR, password);
            }
        }
        if (letterCount * numberCount == 0 && symbolCount == 0) {
            throw new LocalException(ExceptionCode.PASSWORD_NOT_SAFE_ERROR, password);
        }
    }

    /**
     * 密码加密, 返回加密后的字符串
     *
     * @param password 密码
     */
    public static String encrypt(String password) {
        String salt = BCrypt.genSalt(LOG_ROUNDS, new SecureRandom());
        return BCrypt.hashPassword(password, salt);
    }

    /**
     * 密码校验
     *
     * @param password        输入密码
     * @param encodedPassword 加密后的字符串
     */
    public static boolean matches(String password, String encodedPassword) {
        return !(encodedPassword == null || encodedPassword.length() == 0) && BCRYPT_PATTERN.matcher(encodedPassword).matches() && BCrypt.checkPassword(password, encodedPassword);
    }

    /**
     * main 测试密码加密和校验
     */
    public static void main(String[] args) {
        String pwd = "focus";
        String encodedPwd = encrypt(pwd);
        Console.log(encodedPwd);
        Console.log(matches(pwd, encodedPwd));
        encodedPwd = encrypt(pwd);
        Console.log(encodedPwd);
        Console.log(matches(pwd, encodedPwd));
    }

}
