package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);
    private static final String[] SIZE_NAMES = {"B", "KB", "MB", "GB"};

    /**
     * 文件大小转换为B/KB/MB/...
     * @param size
     * @return
     */
    public static String parseSize(Long size) {
        int n = 0;
        while(size >= 1024){
            size=size/1024;
            n++;
        }
        return size+SIZE_NAMES[n];
    }

    /**
     * 日期解析
     * @param lastModified
     * @return
     */
    public static String parseDate(Long lastModified) {
        return DATE_FORMAT.format(new Date(lastModified));
    }
}
