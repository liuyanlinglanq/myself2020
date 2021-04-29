package com.lyl.myself.leetcode;

import com.lyl.myself.mytest.JacksonUtils;

/**
 * description LongestOne
 *
 * @author liuyanling
 * @date 2021-02-19 14:42
 */
public class LongestOne {

    public static void main(String[] args) {
        LongestOne instance = new LongestOne();
        int[] A = new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0};
        int K = 2;
        int result = instance.longestOnes(A, K);
        System.out.println(result);
    }

    public int longestOnes(int[] A, int K) {
        //数组长度
        int n = A.length;
        //前缀和 数组,空出P[0]
        int[] p = new int[n + 1];
        //初始化p的值,前缀和,p[0]=0,p[1]=0+若1号位是0,则p[1]是1,否则还是0,用于统计0的个数 p[1]表示的A[0]有几个0;
        // p[2]表示A[0-1]有几个0,
        for (int i = 1; i <= n; ++i) {
            p[i] = p[i - 1] + (1 - A[i - 1]);
        }
        System.out.println("p值初始化:" + JacksonUtils.toJson(p));

        //找到最大的r-l,采用2分查找P数组,
        int ans = 0;
        // 从头到尾遍历p数组,0是没什么意义的,从1开始;
        // p[r+1],就是从第一个数开始,p[0]有几个0;
        // 没有0,则找-K个值;由于没有0,所以,找不到更多的
        // 若有2个0,则正好,右边的都算上,看看有多少;
        // 若有右边有3个0,则多了一个,需要去掉1个,找到右边有1个0的,这样就符合了;
        for (int r = 0; r < n; ++r) {
            int l = binarySearch(p, p[r + 1] - K);
//            System.out.println("ans此时的结果是:" + ans + " r是: " + r + " r-l+ 1的结果是:" + (r - l + 1));
            ans = Math.max(ans, r - l + 1);

        }
        return ans;

    }

    public int binarySearch(int[] p, int target) {
        //下标,从0开始到length-1结束
        int low = 0;
        int upper = p.length - 1;

        //若 没有遍历完,则继续
        while (low < upper) {
            int mid = (upper - low) / 2 + low;
            // 目标在右边
            if (p[mid] < target) {
                low = mid + 1;
            } else {
                //等于也取左边
                upper = mid;
            }
        }
        System.out.println("查找target:" + target + ",最左边的结果是:" + low);
        //返回最左边的值
        return low;
    }
}
