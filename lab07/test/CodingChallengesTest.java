import org.junit.Test;

import static com.google.common.truth.Truth.assertWithMessage;

public class CodingChallengesTest {

    @Test
    public void testMissingNumber() {
        // 基本情况：中间缺少一个数字
        int[] input1 = {0, 1, 3};
        assertWithMessage("数组 [0, 1, 3] 中缺少 2").that(CodingChallenges.missingNumber(input1)).isEqualTo(2);

        // 缺少第一个数字 (0)
        int[] input2 = {1, 2, 3};
        assertWithMessage("数组 [1, 2, 3] 中缺少 0").that(CodingChallenges.missingNumber(input2)).isEqualTo(0);

        // 缺少最后一个数字 (N)
        int[] input3 = {0, 1, 2};
        assertWithMessage("数组 [0, 1, 2] 中缺少 3").that(CodingChallenges.missingNumber(input3)).isEqualTo(3);

        // 无序数组
        int[] input4 = {3, 0, 1};
        assertWithMessage("无序数组 [3, 0, 1] 中缺少 2").that(CodingChallenges.missingNumber(input4)).isEqualTo(2);

        // 更长的数组
        int[] input5 = {9, 6, 4, 2, 3, 5, 7, 0, 1};
        assertWithMessage("一个较长数组中缺少 8").that(CodingChallenges.missingNumber(input5)).isEqualTo(8);

        // 只有一个元素的数组，缺少 0
        int[] input6 = {1};
        assertWithMessage("数组 [1] 中缺少 0").that(CodingChallenges.missingNumber(input6)).isEqualTo(0);

        // 只有一个元素的数组，缺少 1
        int[] input7 = {0};
        assertWithMessage("数组 [0] 中缺少 1").that(CodingChallenges.missingNumber(input7)).isEqualTo(1);
    }

    @Test
    public void testIsPermutation() {
        // true 的情况
        assertWithMessage("'abc' 和 'cba' 应该是排列关系").that(CodingChallenges.isPermutation("abc", "cba")).isTrue();
        assertWithMessage("带有重复字符的排列关系").that(CodingChallenges.isPermutation("aabbc", "bacab")).isTrue();
        assertWithMessage("空字符串互为排列").that(CodingChallenges.isPermutation("", "")).isTrue();
        assertWithMessage("带有空格的排列关系").that(CodingChallenges.isPermutation("a b c", "c b a")).isTrue();
        assertWithMessage("包含不同大小写的排列关系").that(CodingChallenges.isPermutation("Dog", "goD")).isTrue();


        // false 的情况
        assertWithMessage("不同长度的字符串不可能是排列关系").that(CodingChallenges.isPermutation("abc", "ab")).isFalse();
        assertWithMessage("不同字符的字符串不可能是排列关系").that(CodingChallenges.isPermutation("abc", "abd")).isFalse();
        assertWithMessage("字符计数不同的字符串不可能是排列关系").that(CodingChallenges.isPermutation("aabc", "abbc")).isFalse();
        assertWithMessage("一个为空，一个不为空，不是排列关系").that(CodingChallenges.isPermutation("abc", "")).isFalse();
        assertWithMessage("大小写敏感：'dog' 和 'God' 不应是排列关系").that(CodingChallenges.isPermutation("dog", "God")).isFalse();
    }
}
