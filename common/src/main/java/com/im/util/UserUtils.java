package com.im.util;

import com.im.entity.User;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author shiyakun
 */
public class UserUtils {

    public static List<User> getUserList() {
        try {
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("user.json");
            InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
            StringBuffer sb = new StringBuffer();
            int ch = 0;
            while ((ch = inputStreamReader.read()) != -1) {
                sb.append((char) ch);
            }
            String jsonStr = sb.toString();
            return JacksonUtils.json2list(jsonStr, User.class);
        } catch (Exception e) {

        }
        return null;
    }
}
