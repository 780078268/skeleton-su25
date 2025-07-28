import org.junit.Test;

import static com.google.common.truth.Truth.assertWithMessage;

public class BooleanSetTest {

    @Test
    public void testBasics() {
        BooleanSet aSet = new BooleanSet(100);
        assertWithMessage("Size is not zero upon instantiation").that(aSet.size()).isEqualTo(0);
        for (int i = 0; i < 100; i += 2) {
            aSet.add(i);
            assertWithMessage("aSet should contain " + i).that(aSet.contains(i));
        }

        assertWithMessage("Size is not 50 after 50 calls to add").that(aSet.size()).isEqualTo(50);
        for (int i = 0; i < 100; i += 2) {
            aSet.remove(i);
            assertWithMessage("aSet should not contain " + i).that(!aSet.contains(i));;
        }

        assertWithMessage("aSet is not empty after removing all elements").that(aSet.isEmpty());
        assertWithMessage("Size is not zero after removing all elements").that(aSet.size()).isEqualTo(0);
    }
    @Test
    /** 这个测试专门用来捕捉你遇到的那个错误：重复添加同一个元素。 */
    public void testAddDuplicates() {
        BooleanSet aSet = new BooleanSet(100);
        aSet.add(10);
        assertWithMessage("Size should be 1 after adding one element").that(aSet.size()).isEqualTo(1);

        // 重复添加同一个元素
        aSet.add(10);
        assertWithMessage("Size should still be 1 after adding a duplicate element").that(aSet.size()).isEqualTo(1);

        aSet.add(20);
        assertWithMessage("Size should be 2 after adding a new, different element").that(aSet.size()).isEqualTo(2);

        // 再次重复添加已存在的元素
        aSet.add(20);
        assertWithMessage("Size should still be 2 after adding another duplicate").that(aSet.size()).isEqualTo(2);
    }
    @Test
    /** 测试 contains 和 remove 方法处理不存在的元素时的行为。 */
    public void testContainsAndRemoveNonExistent() {
        BooleanSet aSet = new BooleanSet(100);
        aSet.add(10);
        aSet.add(20);
        aSet.add(30);

        // 检查一个从未添加过的元素
        assertWithMessage("Set should not contain an element that was never added").that(aSet.contains(50)).isFalse();

        // 移除一个不存在的元素
        aSet.remove(50);
        assertWithMessage("Removing a non-existent element should not change the size").that(aSet.size()).isEqualTo(3);
    }

    @Test
    /** 测试边界条件，比如添加 0 和最大容量减 1 的元素。 */
    public void testBoundaryConditions() {
        BooleanSet aSet = new BooleanSet(100);
        // 添加第一个元素 (索引 0)
        aSet.add(0);
        assertWithMessage("Set should contain 0").that(aSet.contains(0)).isTrue();
        assertWithMessage("Size should be 1 after adding 0").that(aSet.size()).isEqualTo(1);

        // 添加最后一个可能的元素 (索引 99)
        aSet.add(99);
        assertWithMessage("Set should contain 99").that(aSet.contains(99)).isTrue();
        assertWithMessage("Size should be 2 after adding 99").that(aSet.size()).isEqualTo(2);

        // 移除这两个元素
        aSet.remove(0);
        assertWithMessage("Set should not contain 0 after removal").that(aSet.contains(0)).isFalse();
        aSet.remove(99);
        assertWithMessage("Set should not contain 99 after removal").that(aSet.contains(99)).isFalse();
        assertWithMessage("Set should be empty after removing boundary elements").that(aSet.isEmpty()).isTrue();
    }

    @Test
    /** 测试 toIntArray 方法是否能正确返回包含所有元素的数组。 */
    public void testToIntArray() {
        BooleanSet aSet = new BooleanSet(100);
        aSet.add(10);
        aSet.add(5);
        aSet.add(20);

        int[] expected = {5, 10, 20}; // toIntArray 应该返回一个有序数组
        int[] actual = aSet.toIntArray();

        assertWithMessage("toIntArray() did not return the correct elements or order").that(actual).isEqualTo(expected);

        // 测试空集合的情况
        BooleanSet emptySet = new BooleanSet(10);
        int[] emptyArray = emptySet.toIntArray();
        assertWithMessage("toIntArray() on an empty set should return an empty array").that(emptyArray).isEqualTo(new int[0]);
    }
}
