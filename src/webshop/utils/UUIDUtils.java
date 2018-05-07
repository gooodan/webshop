package webshop.utils;

import java.util.UUID;

/**
 * 生成随机字符串的工具类
 */

public class UUIDUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
