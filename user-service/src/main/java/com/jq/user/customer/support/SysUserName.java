package com.jq.user.customer.support;

import java.util.Random;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/5/9
 */
public class SysUserName {

    public static String generatorName(String startsWith){

        return startsWith+System.currentTimeMillis()+generateNumber();
    }

    /**
     * @return 随机8为不重复数组
     */
    private static String generateNumber() {
        StringBuilder no = new StringBuilder();
        int[] defaultNums = new int[10];
        for (int i = 0; i < defaultNums.length; i++) {
            defaultNums[i] = i;
        }
        Random random = new Random();
        int[] nums = new int[8];
        int canBeUsed = 10;
        for (int i = 0; i < nums.length; i++) {
            int index = random.nextInt(canBeUsed);
            nums[i] = defaultNums[index];
            swap(index, canBeUsed - 1, defaultNums);
            canBeUsed--;
        }
        if (nums.length > 0) {
            for (int num : nums) {
                no.append(num);
            }
        }
        return no.toString();
    }

    private static void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
