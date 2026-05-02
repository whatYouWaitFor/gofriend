package com.greg.gofriend.utils;

import java.util.List;
import java.util.Objects;

public class AlgorithmuUtils {

    /**
     * 计算两个标签列表的Levenshtein距离（用于计算最相似的两个标签列表）
     * @param tagList1
     * @param tagList2
     * @return
     */
    public static int computeLevenshteinDistance(List<String> tagList1, List<String> tagList2) {
        int m = tagList1.size();
        int n = tagList2.size();

        // 创建一个二维数组来存储中间结果
        int[][] dp = new int[m + 1][n + 1];

        // 初始化边界条件
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // 填充dp表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = Objects.equals(tagList1.get(i - 1), tagList2.get(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(dp[i - 1][j] + 1,       // 删除操作
                        Math.min(dp[i][j - 1] + 1, // 插入操作
                                dp[i - 1][j - 1] + cost)); // 替换操作
            }
        }
        return dp[m][n];
    }

    /**
     * 计算两个字符串的Levenshtein距离（用于计算最相似的两个字符串）
     *
     * @param s 字符串1
     * @param t 字符串2
     * @return Levenshtein距离
     */
    public static int computeLevenshteinDistance(String s, String t) {
        int m = s.length();
        int n = t.length();

        // 创建一个二维数组来存储中间结果
        int[][] dp = new int[m + 1][n + 1];

        // 初始化边界条件
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // 填充dp表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = (s.charAt(i - 1) == t.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(dp[i - 1][j] + 1,       // 删除操作
                        Math.min(dp[i][j - 1] + 1, // 插入操作
                                dp[i - 1][j - 1] + cost)); // 替换操作
            }
        }

        return dp[m][n];
    }
}
