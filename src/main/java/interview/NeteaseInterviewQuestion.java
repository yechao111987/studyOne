package interview;

import org.junit.jupiter.api.Test;

public class NeteaseInterviewQuestion {

    //题目：给出100元钱，鸡蛋每只1/3，公鸡1元/只，母鸡3元/只，请计算出所有的可能性。

    /**
     * 3x+y+1/3*z=100
     * x+y+z=100
     *
     * @param args
     */
    public static void main(String[] args) {
        for (int x = 1; x <= 33; x++) {
            for (int y = 1; y <= 97; y++) {
                for (int z = 1; z < 99; z++) {
                    if (x + y + z == 100 && 3 * 3 * x + 3 * y + z == 300) {
                        System.out.println("x=" + x + "," + ",y=" + y + ",z=" + z);
                    }
                }
            }
        }
    }

    //错误写法一
    @Test
    public void test1() {
        for (int x = 1; x <= 33; x++) {
            for (int y = 1; y <= 97; y++) {
                for (int z = 1; z < 99; z++) {
                    if (z % 3 == 0) {
                        if (x + y + z == 100 && 3 * x + y + 1 / 3 * z == 100) {
                            //错误原因：1/3是整除  应该修改为z/3
                            System.out.println("x=" + x + "," + ",y=" + y + ",z=" + z);
                        }
                    }
                }
            }
        }

    }

    //错误写法二
    @Test
    public void test2() {
        for (int x = 1; x <= 33; x++) {
            for (int y = 1; y <= 97; y++) {
                for (int z = 1; z < 99; z++) {
                    if (x + y + z == 100 && 3 * x + y + z / 3 == 100) {
                        //错误原因：z/3是整除，没有判断z是否是3的倍数
                        System.out.println("x=" + x + "," + ",y=" + y + ",z=" + z);
                    }
                }
            }
        }
    }

    //错误写法三：考虑到非IDE环境，需要注意&&，==等符号

}
