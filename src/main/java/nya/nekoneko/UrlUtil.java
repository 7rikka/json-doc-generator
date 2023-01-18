package nya.nekoneko;

import cn.hutool.core.util.NumberUtil;

public class UrlUtil {
    public static void main(String[] args) {
        String url = "https://api.bilibili.com/x/v2/dm/thumbup/add?op=1&dmid=1222629517550865920&oid=949419009&platform=web_player&polaris_appid=100&polaris_platfrom=5&spmid=333.788.0.0&from_spmid=333.1007.tianma.1-1-1.click&csrf=d393c7197657880c41a66ee75f4b0c0d";
        gen(url);
    }

    public static void gen(String url) {
        System.out.println("| 参数名      | 类型  | 必填  | 内容   | 备注                                |\n" +
                "|----------|-----|-----|------|-----------------------------------|");
        String[] s1 = url.split("\\?");
        String[] s2 = s1[1].split("&");
        for (String s : s2) {
            String[] s3 = s.split("=");
            System.out.println("|" + s3[0] + "|" + getType(s3[1]) + "| | | |");
        }
    }

    public static String getType(String s) {
        if (NumberUtil.isNumber(s)) {
            return "num";
        } else if ("true".equals(s) || "false".equals(s)) {
            return "bool";
        }
        return "str";
    }
}
