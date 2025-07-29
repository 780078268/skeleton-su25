import deque.ArrayDeque61B;
import deque.Deque61B;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    /** 测试最基本的功能：add, size, 和 isEmpty。 */
    public void addIsEmptySizeTest() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        assertThat(ad.isEmpty()).isTrue();
        ad.addFirst("front");
        assertThat(ad.size()).isEqualTo(1);
        ad.addLast("back");
        assertThat(ad.size()).isEqualTo(2);
    }

    @Test
    /** 测试 toList 方法在无环绕情况下的表现。 */
    public void toListTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(10);
        ad.addLast(20);
        ad.addFirst(5);
        List<Integer> expected = List.of(5, 10, 20);
        assertThat(ad.toList()).isEqualTo(expected);
    }

    @Test
    /** 测试 removeFirst 和 removeLast 的基本功能。 */
    public void removeTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(10);
        ad.addLast(20);
        ad.addFirst(5); // Deque: [5, 10, 20]

        assertThat(ad.removeFirst()).isEqualTo(5);
        assertThat(ad.toList()).containsExactly(10, 20).inOrder();
        assertThat(ad.removeLast()).isEqualTo(20);
        assertThat(ad.toList()).containsExactly(10).inOrder();
    }

    @Test
    /** 测试 get 方法。 */
    public void getTest() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("a");
        ad.addLast("b");
        ad.addFirst("c"); // [c, a, b]
        assertThat(ad.get(1)).isEqualTo("a");
        assertThat(ad.get(5)).isNull(); // 越界测试
    }

    // ---------- 以下是新增的缩放测试 ----------

    @Test
    /** 测试当元素填满初始数组时，扩容 (Upsizing) 是否能正确工作。 */
    public void addPastInitialCapacityShouldResizeUp() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        // 初始容量为 8，我们添加 10 个元素来触发扩容
        for (int i = 0; i < 10; i++) {
            ad.addLast(i);
        }

        assertWithMessage("添加 10 个元素后，size 应该为 10").that(ad.size()).isEqualTo(10);
        assertWithMessage("get(9) 应该返回最后一个元素").that(ad.get(9)).isEqualTo(9);

        // 验证 toList 是否在扩容后依然正确
        List<Integer> expectedList = IntStream.range(0, 10).boxed().toList();
        assertWithMessage("扩容后 toList() 的结果不正确").that(ad.toList()).isEqualTo(expectedList);
    }

    @Test
    /** 测试在环形状态下触发扩容。 */
    public void addWithWrapAroundShouldResizeUp() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        // 1. 填满数组，并让指针环绕
        for (int i = 0; i < 8; i++) {
            ad.addFirst(i);
        }
        // Deque: [7, 6, 5, 4, 3, 2, 1, 0]
        // 此时数组已满，nextFirst 和 nextLast 相邻

        // 2. 再次添加，触发扩容
        ad.addLast(8);

        assertWithMessage("环形扩容后，size 应该为 9").that(ad.size()).isEqualTo(9);
        assertWithMessage("环形扩容后，get(0) 应该返回 7").that(ad.get(0)).isEqualTo(7);
        assertWithMessage("环形扩容后，get(8) 应该返回 8").that(ad.get(8)).isEqualTo(8);

        // 3. 再次添加
        ad.addFirst(9);
        assertWithMessage("再次添加后，size 应该为 10").that(ad.size()).isEqualTo(10);
        assertWithMessage("再次添加后，get(0) 应该返回 9").that(ad.get(0)).isEqualTo(9);
        assertWithMessage("再次添加后，get(1) 应该返回 7").that(ad.get(1)).isEqualTo(7);
    }

    @Test
    /** 测试当元素大量移除后，缩容 (Downsizing) 是否能正确工作。 */
    public void removePastThresholdShouldResizeDown() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        // 1. 添加 20 个元素，至少触发一次扩容
        for (int i = 0; i < 20; i++) {
            ad.addLast(i);
        }
        assertWithMessage("添加 20 个元素后，size 应该为 20").that(ad.size()).isEqualTo(20);

        // 2. 移除元素，直到低于缩容阈值
        // 假设使用率为 25%，当 size 变为 4 时，数组长度应从 16+ 缩容
        for (int i = 0; i < 16; i++) {
            ad.removeLast();
        }
        assertWithMessage("移除 16 个元素后，size 应该为 4").that(ad.size()).isEqualTo(4);

        // 3. 检查缩容后的状态
        List<Integer> expected = List.of(0, 1, 2, 3);
        assertWithMessage("缩容后 toList() 的结果不正确").that(ad.toList()).isEqualTo(expected);
        assertWithMessage("缩容后 get(3) 应该返回 3").that(ad.get(3)).isEqualTo(3);
    }

    @Test
    /** 测试在环形状态下触发缩容。 */
    public void removeWithWrapAroundShouldResizeDown() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        // 1. 添加 20 个元素
        for (int i = 0; i < 20; i++) {
            ad.addLast(i);
        }

        // 2. 从两端移除，制造环形状态
        for (int i = 0; i < 8; i++) {
            ad.removeFirst();
        }
        for (int i = 0; i < 8; i++) {
            ad.removeLast();
        }
        // 此时 size = 4, 元素为 [8, 9, 10, 11]
        // 这应该已经触发了缩容
        assertWithMessage("环形移除后，size 应该为 4").that(ad.size()).isEqualTo(4);
        List<Integer> expected = List.of(8, 9, 10, 11);
        assertWithMessage("环形缩容后 toList() 的结果不正确").that(ad.toList()).isEqualTo(expected);

        // 4. 继续操作，确保状态正确
        assertThat(ad.removeFirst()).isEqualTo(8);
        assertThat(ad.removeLast()).isEqualTo(11);
        assertThat(ad.size()).isEqualTo(2);
    }
}
