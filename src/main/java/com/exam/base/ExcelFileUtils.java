package com.exam.base;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel 处理类
 *
 * @author sunc
 * @date 2020/3/7 14:15
 */

public class ExcelFileUtils {
    private static final Logger logger = Logger.getLogger(ExcelFileUtils.class);

    /**
     * 读取 excel 文件内容
     *
     * @param file excel 文件路径
     * @return 文件内容
     */
    public static List<List<String>> readExcel(String file) {
        return readExcel(ExcelUtil.getReader(file));
    }

    public static List<List<String>> readExcel(File file) {
        return readExcel(ExcelUtil.getReader(file));
    }

    private static List<List<String>> readExcel(ExcelReader reader) {
        List<List<Object>> excel = reader.read();
        reader.close();

        List<List<String>> contents = new ArrayList<>();
        excel.forEach(line -> {
            List<String> content = new ArrayList<>();
            line.forEach(l -> {
                if (l != null) {
                    content.add(String.valueOf(l));
                }
            });
            contents.add(content);
        });

        return contents;
    }

    /**
     * 创建 excel 文件内容
     *
     * @param file excel 文件路径
     */
    public static void writeExcel(String file, List<List<Object>> contents) {
        ExcelWriter writer = ExcelUtil.getWriter(file);
        writer.write(contents);
        writer.close();
    }

}
