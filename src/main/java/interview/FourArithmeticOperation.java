package interview;



import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author yechao111987@126.com
 * @date 2019/1/6 19:09
 */
public class FourArithmeticOperation {


    //题目： 一个int型的数字，范围在-2147483648～2147483647之间（32位），假设给你远远超过范围的两个数字，以String类型给出
    //（例如：a=“31934987234987191374981394713487”和b=“983741987391798347198374918”）请代码实现如下功能：
    //1.a+b;
    //2.a-b;
    //3.a*b;
    //4.a/b;
    //notice:This interview question is from Alibaba

    private static boolean isNumeric(String str) {
        if (str.isEmpty()) {
            System.out.println("异常：\"" + str + "\"不是数字/整数...");
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            int ch = str.charAt(i);
            //45为"-"
            if ((ch < 48 || ch > 57) && ch != 45) {
                System.out.println("异常：\"" + str + "\"不是数字/整数...");
                return false;
            }
        }
        return true;
    }

    private static Boolean compare(String a, String b) {
        if (!isNumeric(a) || !isNumeric(b)) {
            return null;
        }
        if (a.startsWith("-") && b.startsWith("-")) {
            return !compareBasic(a.substring(1, a.length()), b.substring(1, b.length()));
        }
        if (a.startsWith("-")) {
            return false;
        }
        if (b.startsWith("-")) {
            return true;
        }
        return compareBasic(a, b);
    }

    private static boolean compareBasic(String a, String b) {
        boolean flag = true;
        if (a.length() < b.length()) {
            flag = false;
        }
        if (a.length() == b.length()) {
            for (int i = 0; i < a.length(); i++) {
                //小于，循环结束
                if (Integer.valueOf(a.substring(i, 1 + i)) < Integer.valueOf(b.substring(i, 1 + i))) {
                    flag = false;
                    break;
                }
                //大于 循环结束
                if (Integer.valueOf(a.substring(i, 1 + i)) > Integer.valueOf(b.substring(i, 1 + i))) {
                    flag = true;
                    break;
                }
                //等于 继续比较
            }
        }
        return flag;
    }

    /**
     * 加法：a+b
     *
     * @param a
     * @param b
     * @return
     */
    public String add(String a, String b) {
        if (!isNumeric(a) || !isNumeric(b)) {
            return null;
        }
        if (a.startsWith("-") && b.startsWith("-")) {
            return "-" + addBasic(a.substring(1, a.length()), b.substring(1, b.length()));
        }
        if (a.startsWith("-")) {
            return subtractBasic(b, a.substring(1, a.length() - 1));
        }
        if (b.startsWith("-")) {
            return subtractBasic(a, b.substring(1, b.length() - 1));
        }
        return addBasic(a, b);
    }

    public String addBasic(String a, String b) {
        StringBuffer buffer = new StringBuffer();
        int length;
        String subPart;
        System.out.println(a + "+" + b + "=");
        //1.判断a.b长度，切割到头部数据subPart
        if (a.length() > b.length()) {
            length = b.length();
            subPart = a.substring(0, a.length() - length);
        } else {
            length = a.length();
            if (a.length() == b.length()) {
                subPart = "";
            } else {
                subPart = b.substring(0, b.length() - length);
            }
        }
        //2.将a,b相同位数的数字相加，注意：考虑进位进位
        Boolean flag = false;
        for (int i = 0; i < length; i++) {
            int aValue = Integer.valueOf(a.substring(a.length() - 1 - i, a.length() - i));
            int bValue = Integer.valueOf(b.substring(b.length() - 1 - i, b.length() - i));
            int temp = aValue + bValue;
            if (flag) {
                temp = temp + 1;
            }
            if (temp > 9) {
                //2.1 进位：放入余数
                buffer.insert(0, temp % 10);
                flag = true;
            } else {
                //2.2 不进位
                flag = false;
                buffer.insert(0, temp);
            }
        }
        //3.如果最后一次计算发生进位，则subPart必须进位，考虑到subPart可能继续进位
        if (flag) {
            if (subPart.length() == 0) {
                //3.1 头部长度为0且发生进位，则新进1位
                buffer.insert(0, 1);
            } else {
                //3.2 头部长度不为0，且发生进位
                for (int i = 0; i < subPart.length(); i++) {
                    int iValue = Integer.valueOf(subPart.substring(subPart.length() - 1 - i, subPart.length() - i));
                    iValue++;
                    //3.2.1 若大于9，则进位，继续循环
                    if (iValue > 9) {
//                        buffer.insert(0, iValue % 10);  bug:不能直接插入余数，考虑9999的进位情况
                        // 3.2.1.2 非最后一位时，直接取余数
                        if (i + 1 < subPart.length()) {
                            buffer.insert(0, iValue % 10);
                        }
                        //3.2.1.2 当最后一位依然发生进位时
                        if (i + 1 == subPart.length()) {
                            buffer.insert(0, iValue);
                        }
                    } else {
                        //3.2.2 若小于等于9，不进位，结束循环
                        buffer.insert(0, iValue);
                        //插入尚未进位的头部数据
                        buffer.insert(0, subPart.substring(0, subPart.length() - i - 1));
                        break;
                    }
                }
            }
        } else {
            //4 若不发生进位，则直接插入头部
            buffer.insert(0, subPart);
        }
        System.out.println("result is " + buffer.toString());
        return buffer.toString();
    }

    /**
     * 减法：a-b
     *
     * @param a
     * @param b
     * @return
     */
    public String subtract(String a, String b) {
        if (!isNumeric(a) || !isNumeric(b)) {
            return null;
        }
        if (a.startsWith("-") && b.startsWith("-")) {
            return subtractBasic(b.substring(1, b.length() - 1), a.substring(1, a.length() - 1));
        }
        if (a.startsWith("-")) {
            return "-" + addBasic(a.substring(1, a.length()), b.substring(1, b.length()));
        }
        if (b.startsWith("-")) {
            return addBasic(a, b.substring(1, b.length()));
        }
        return subtractBasic(a, b);

    }

    public String subtractBasic(String a, String b) {
        StringBuffer buffer = new StringBuffer();
        System.out.println(a + "-" + b + "=");
        //用于判断最后结果的+/-
        Boolean resultFlag = false;
        //1.首先判断a,b大小
        if (!compareBasic(a, b)) {
            String temp = a;
            a = b;
            b = temp;
            resultFlag = true;
        }
        int aLength = a.length();
        int bLength = b.length();
        //退位标志
        Boolean flag = Boolean.FALSE;
        //2 计算相同位数的减法
        for (int i = 0; i < bLength; i++) {
            int aValue;
            //2.1 若发生退位，则前面一位数据-1
            if (flag) {
                aValue = Integer.valueOf(a.substring(a.length() - 1 - i, a.length() - i)) - 1;
            } else {
                aValue = Integer.valueOf(a.substring(a.length() - 1 - i, a.length() - i));
            }
            int bValue = Integer.valueOf(b.substring(b.length() - 1 - i, b.length() - i));
            if (aValue >= bValue) {
                //2.2 若aValue>bValue,直接保存结果,且设置下一位计算不退位
                buffer.insert(0, aValue - bValue);
                //设置退位标志不退位
                flag = false;
            } else {
                //2.3 若aValue<bValue,发生退位，设置下位计算退位
                buffer.insert(0, aValue - bValue + 10);
                flag = true;
            }
        }
        String subPart = a.substring(0, aLength - bLength);
        //3.如果需要退位(aLength比大于bLength)，
        if (flag) {
            for (int i = 0; i < subPart.length(); i++) {
                Integer temp = Integer.valueOf(subPart.substring(subPart.length() - 1 - i, subPart.length() - i));
                //3.1 如果当前位大于0，则直接退位,加上未计算的位数，结束循环
                if (temp > 0) {
                    temp = temp - 1;
                    buffer.insert(0, temp);
                    buffer.insert(0, subPart.substring(0, subPart.length() - i - 1));
                    break;
                } else {
                    //3.2 若当前位为0，则继续循环，考虑到必然有首位数字大于等于1，所以循环必定会结束
                    buffer.insert(0, 9);
                }
            }
        } else {
            //4.若不发生退位，则直接加上头部部分
            buffer.insert(0, subPart);
        }
        //5.去掉多余的0 注意排除全为0的情况
        String tempStr = buffer.toString();
        while (tempStr.startsWith("0") & tempStr.length() > 1) {
            tempStr = tempStr.substring(1);
        }
        if (resultFlag && !tempStr.equals("0")) {
            tempStr = "-" + tempStr;
        }
        System.out.println("result is " + tempStr);
        return tempStr;
    }

    /**
     * 乘法：a*b
     *
     * @param a
     * @param b
     * @return
     */
    public String multiple(String a, String b) {
        if (!isNumeric(a) && !isNumeric(b)) {
            System.out.println("参数必须为数字类型");
            return "";
        }
        if (a.startsWith("-") && b.startsWith("-")) {
            return multipleBasic(b.substring(1, b.length() - 1), a.substring(1, a.length() - 1));
        }
        if (a.startsWith("-")) {
            return "-" + multipleBasic(a.substring(1, a.length()), b);
        }
        if (b.startsWith("-")) {
            return "-" + multipleBasic(a, b.substring(1, b.length()));
        }
        return multipleBasic(a, b);

    }

    public String multipleBasic(String a, String b) {
        StringBuffer buffer = new StringBuffer();
        List<Integer> aValues = new ArrayList<>();
        List<Integer> bValues = new ArrayList<>();
        List<Map<String, Integer>> cValues = new ArrayList<>();
        for (int i = 0; i < a.length(); i++) {
            aValues.add(i, Integer.valueOf(a.substring(a.length() - 1 - i, a.length() - i)));
        }
        for (int i = 0; i < b.length(); i++) {
            bValues.add(i, Integer.valueOf(b.substring(b.length() - 1 - i, b.length() - i)));
        }
        for (int i = 0; i < aValues.size(); i++) {
            int aTemp = aValues.get(i);
            for (int j = 0; j < bValues.size(); j++) {
                int bTemp = bValues.get(j);
                int cTemp = aTemp * bTemp;
                if (String.valueOf(cTemp).length() == 1) {
                    Map<String, Integer> map = new HashMap<>();
                    map.put(String.valueOf(i + j), cTemp);
//                    map.put(String.valueOf(i + j + 1), 0);
//                    cValues.add(i + j, cTemp);
                    cValues.add(map);
                } else {
                    Map<String, Integer> map1 = new HashMap<>();
                    Map<String, Integer> map2 = new HashMap<>();
                    map1.put(String.valueOf(i + j), cTemp % 10);
                    map2.put(String.valueOf(i + j + 1), cTemp / 10);
                    cValues.add(map1);
                    cValues.add(map2);
                }
            }
        }
        System.out.println(cValues.size());
        String flagValue = "0";
        boolean flag = false;
        for (int i = 0; i < a.length() + b.length(); i++) {
            String tempResult = "0";
            for (Map<String, Integer> map : cValues) {
                if (map.containsKey(String.valueOf(i))) {
                    Integer temp = map.get(String.valueOf(i));
                    tempResult = add(tempResult, String.valueOf(temp));
                }
            }
            if (flag) {
                tempResult = add(tempResult, flagValue);
            }
            if (tempResult.length() > 1) {
                buffer.insert(0, tempResult.substring(tempResult.length() - 1, tempResult.length()));
                flagValue = tempResult.substring(0, tempResult.length() - 1);
                flag = true;
            } else {
                buffer.insert(0, tempResult);
                flag = false;
                flagValue = "0";
            }
        }
        String tempResult = buffer.toString();
        while (tempResult.startsWith("0") && tempResult.length() > 1) {
            tempResult = tempResult.substring(1);
        }
        System.out.println("result:" + tempResult);
        return tempResult;
    }

    /**
     * 除法：a/b
     *
     * @param a
     * @param b
     * @return 商和余数
     */
    public String division(String a, String b) {
        if (!isNumeric(a) && !isNumeric(b)) {
            System.out.println("参数必须为数字类型");
            return "";
        }
        if (a.startsWith("-") && b.startsWith("-")) {
            return divisionBasic(a.substring(1, a.length() - 1), b.substring(1, b.length() - 1), "");
        }
        if (a.startsWith("-")) {
            return divisionBasic(a.substring(1, a.length()), b, "-");
        }
        if (b.startsWith("-")) {
            return divisionBasic(a, b.substring(1, b.length()), "-");
        }
        return divisionBasic(a, b, "");


    }

    public String divisionBasic(String a, String b, String resultFlag) {
        //判断a,b大小
        Boolean flag = compare(a, b);
        if (!flag) {
            System.out.println("商为0，余数为:" + a);
            return "商为0，余数为:" + a;
        } else {
            String i = "0";
            while (compare(a, b)) {
                a = subtract(a, b);
                i = addBasic(i, "1");
            }
            System.out.println("商为:" + resultFlag + i + " 余数为:" + a);
            return "商为:" + resultFlag + i + " 余数为:" + a;
        }
    }

    @Test
    public void testAdd() {
        String a = "0";
        String b = "199";
        System.out.println(add(a, b));
    }

    @Test
    public void testSubtract() {
        String a = "1999897";
        String b = "129";
        System.out.println(subtract(a, b));

    }

    @Test
    public void testMultiple() {
        String a = "200000000000026";
        String b = "129";
        System.out.println(multiple(a, b));
    }

    @Test
    public void testDivision() {
        String a = "200000000000000000000000026";
        String b = "129";
        System.out.println(division(a, b));
    }

    @Test
    public void testCompare() {
        boolean b = compare("-25", "10");
        System.out.println(b);
    }

    @Test
    public void testNumeric() {
        isNumeric("0");
        isNumeric("");
        isNumeric("1212");
        isNumeric("name");
        isNumeric("-5");
    }

}
