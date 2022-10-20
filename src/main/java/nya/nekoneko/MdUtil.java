package nya.nekoneko;

import cn.hutool.core.util.NumberUtil;
import org.noear.snack.ONode;
import org.noear.snack.ONodeType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MdUtil {
    private static final LinkedHashMap<String, List<String[]>> map = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {
        //读取JSON
        BufferedReader bufferedReader = new BufferedReader(new FileReader("json.txt"));
        String json = bufferedReader.lines().collect(Collectors.joining());
        bufferedReader.close();
        //解析JSON
        ONode oNode = ONode.loadStr(json);
        String name = "root";
        cl(name, oNode);
        //整理
        System.out.println("=======整理==========");
        for (Map.Entry<String, List<String[]>> item : map.entrySet()) {
            System.out.println("### " + item.getKey().replace("root -> ", ""));
            System.out.println();
            System.out.println("| 字段名     | 类型    | 内容   | 备注                           |");
            System.out.println("|---------|-------|------|------------------------------|");
            for (String[] strings : item.getValue()) {
                System.out.println("| " + strings[0] + "    | " + strings[1] + " |  |      |");
            }
            System.out.println();
        }
    }

    private static void cl(String name, ONode node) {
        List<String[]> list = map.get(name);
        if (null == list) {
            list = new ArrayList<>();
        }
        map.put(name, list);
        List<String[]> finalList = list;
        node.forEach((k, v) -> {
            String[] strings = new String[2];
            strings[0] = k;
            if (v.nodeType() == ONodeType.Array) {
                strings[1] = "array";
                v.forEach(node1 -> cl(name + " -> `" + k + "`数组中的对象", node1));
            } else if (v.nodeType() == ONodeType.Object) {
                strings[1] = "obj";
                cl(name + " -> `" + k + "`对象", v);
            } else if (NumberUtil.isNumber(v.toString())) {
                strings[1] = "num";
            } else if ("true".equals(v.toString()) || "false".equals(v.toString())) {
                strings[1] = "bool";
            } else if (v.toString().indexOf("\"") == 0) {
                strings[1] = "str";
            }
            if (!exist(finalList, k, strings[1])) {
                finalList.add(strings);
            }
        });
    }

    public static boolean exist(List<String[]> list, String name, String value) {
        for (String[] strings : list) {
            if (name.equals(strings[0])) {
                if (strings[1] == null) {
                    strings[1] = value;
                }
                return true;
            }
        }
        return false;
    }
}
