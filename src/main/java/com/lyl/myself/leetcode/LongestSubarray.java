package com.lyl.myself.leetcode;

import java.util.TreeMap;

/**
 * description LongestSubarray
 *
 * @author liuyanling
 * @date 2021-02-21 10:45
 */
public class LongestSubarray {

    public int longestSubarray(int[] nums, int limit) {
        //首先,找最大值,最小值,java用TreeMap,这个我以后一定用的上
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        //结果先记录为0;
        int ret = 0;

        //用滑动窗口的方式,固定右边,然后先从最左边找起来,若最大和最小符合条件,则他们的长度就可以保留看看
        //若不符合条件,则缩小范围看看,最左边往右走一格;
        int right = 0;
        int left = 0;
        int n = nums.length;
        //算法好像不喜欢用for循环,一直都是用while
        while (right < n) {
            //先把右边的值,放入map中,值的话默认是1,后面就累加
            map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);

            //若此时map中的最大值和最小值,不符合limit要求,则需要左边往右走走
            //算法也不用if,用while,这样,就能一直走
            while (map.lastKey() - map.firstKey() > limit) {
                map.put(nums[left], map.get(nums[left]) - 1);
                if (map.get(nums[left]) == 0) {
                    map.remove(nums[left]);
                }
                left++;
            }

            ret = Math.max(ret, right - left + 1);
            right++;

        }
        return ret;
    }

    public static void main(String[] args) {
        LongestSubarray instance = new LongestSubarray();

        int[] nums = new int[]{3, 2, 5, 2, 7, 9, 10};
        int limit = 4;
        System.out.println(instance.longestSubarray(nums, limit));

    }
}
