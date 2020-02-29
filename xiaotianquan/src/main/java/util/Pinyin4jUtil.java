

package util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.HashSet;
import java.util.Set;

public class Pinyin4jUtil {

    /**
     * 中文字符格式
     */
    private static final String CHINESE_PATTERN = "[\\u4E00-\\u9FA5]";

    private static final HanyuPinyinOutputFormat FORMAT
            = new HanyuPinyinOutputFormat();

    static {
        // 拼音小写
        FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 不带音调
        FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // 带v字符
        FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * 判断字符串中是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean containsChinese(String str) {
        return str.matches(".*" + CHINESE_PATTERN + ".*");
    }

    /**
     * 简单实现：
     * 1.取每个汉字拼音的第一个，然后拼接在一起返回
     * 2.去每个汉字拼音的第一个字符串的首字母，拼接返回
     *
     * @param hanyu
     * @return
     */
    public static String[] get(String hanyu) {
        String[] array = new String[2];
        // 全拼
        StringBuilder pinyin = new StringBuilder();
        // 拼音首字母
        StringBuilder pinyin_first = new StringBuilder();
        for (int i = 0; i < hanyu.length(); i++) {
            try {
                String[] pinyins = PinyinHelper
                        .toHanyuPinyinStringArray(hanyu.charAt(i), FORMAT);
                // 中文字符返回的字符串数组，可能为null或长度为0
                // 返回原始的字符
                if (pinyins == null || pinyins.length == 0) {
                    pinyin.append(hanyu.charAt(i));
                    pinyin_first.append(hanyu.charAt(i));
                } else {// 可以转换为拼音，只取第一个字符串作为拼音
                    pinyin.append(pinyins[0]);
                    pinyin_first.append(pinyins[0].charAt(0));
                }
            } catch (Exception e) {// 出现异常，返回原始的字符
                pinyin.append(hanyu.charAt(i));
                pinyin_first.append(hanyu.charAt(i));
            }
        }
        array[0] = pinyin.toString();
        array[1] = pinyin_first.toString();
        return array;
    }

    /**
     * 返回字符串所有的拼音
     *
     * @param hanyu     字符串
     * @param fullSpell 是否为全拼
     * @return
     */
    public static String[][] get(String hanyu, boolean fullSpell) {
        String[][] array = new String[hanyu.length()][];
        for (int i = 0; i < hanyu.length(); i++) {
            try {
                String[] pinyins = PinyinHelper
                        .toHanyuPinyinStringArray(hanyu.charAt(i), FORMAT);
                if (pinyins == null || pinyins.length == 0) {// a->["a"]
                    array[i] = new String[]{String.valueOf(hanyu.charAt(i))};
                } else {// 和->he he huo he hu 去重
                    array[i] = unique(pinyins, fullSpell);
                }
            } catch (Exception e) {// 出现异常，返回原始的字符
                array[i] = new String[]{String.valueOf(hanyu.charAt(i))};
            }
        }
        return array;
    }
    // 长（chang、zhang）和（he、huo、hu、hai）乐（le、yue）
    // 排列组合：两两组合：第一个和第二个组合为一个字符串数组，
    // 依次往后组合

    /**
     * 拼音字符串数组去重操作
     *
     * @param pinyins
     * @param fullSpell true为全拼，false取首字母
     * @return
     */
    private static String[] unique(String[] pinyins, boolean fullSpell) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < pinyins.length; i++) {
            if (fullSpell) {
                set.add(pinyins[i]);
            } else {
                set.add(String.valueOf(pinyins[i].charAt(0)));
            }
        }
        return set.toArray(new String[set.size()]);
    }
}