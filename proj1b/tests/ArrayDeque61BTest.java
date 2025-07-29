import deque.ArrayDeque61B;
import deque.Deque61B;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    /* 测试最基本的功能：add, size, 和 isEmpty。 */
    public void addIsEmptySizeTest() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        assertThat(ad.isEmpty()).isTrue();
        ad.addFirst("front");
        assertThat(ad.size()).isEqualTo(1);
        assertThat(ad.isEmpty()).isFalse();
        ad.addLast("back");
        assertThat(ad.size()).isEqualTo(2);
    }

    @Test
    /* 测试 toList 方法在无环绕情况下的表现。 */
    public void toListTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(10);
        ad.addLast(20);
        ad.addFirst(5);
        List<Integer> expected = List.of(5, 10, 20);
        assertThat(ad.toList()).isEqualTo(expected);
    }

    @Test
    /* 测试 removeFirst 和 removeLast 的基本功能。 */
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
    /* 测试 get 方法。 */
    public void getTest() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("a");
        ad.addLast("b");
        ad.addFirst("c"); // [c, a, b]
        assertThat(ad.get(0)).isEqualTo("c");
        assertThat(ad.get(1)).isEqualTo("a");
        assertThat(ad.get(2)).isEqualTo("b");
        assertThat(ad.get(5)).isNull(); // 越界测试
        assertThat(ad.get(-1)).isNull(); // 越界测试
    }

    // ---------- 以下是你已有的缩放测试 (保持不变) ----------

    @Test
    /* 测试当元素填满初始数组时，扩容 (Upsizing) 是否能正确工作。 */
    public void addPastInitialCapacityShouldResizeUp() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        for (int i = 0; i < 10; i++) {
            ad.addLast(i);
        }
        assertWithMessage("添加 10 个元素后，size 应该为 10").that(ad.size()).isEqualTo(10);
        assertWithMessage("get(9) 应该返回最后一个元素").that(ad.get(9)).isEqualTo(9);
        List<Integer> expectedList = IntStream.range(0, 10).boxed().toList();
        assertWithMessage("扩容后 toList() 的结果不正确").that(ad.toList()).isEqualTo(expectedList);
    }

    @Test
    /* 测试在环形状态下触发扩容。 */
    public void addWithWrapAroundShouldResizeUp() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        for (int i = 0; i < 8; i++) {
            ad.addFirst(i);
        }
        ad.addLast(8);
        assertWithMessage("环形扩容后，size 应该为 9").that(ad.size()).isEqualTo(9);
        assertWithMessage("环形扩容后，get(0) 应该返回 7").that(ad.get(0)).isEqualTo(7);
        assertWithMessage("环形扩容后，get(8) 应该返回 8").that(ad.get(8)).isEqualTo(8);
        ad.addFirst(9);
        assertWithMessage("再次添加后，size 应该为 10").that(ad.size()).isEqualTo(10);
        assertWithMessage("再次添加后，get(0) 应该返回 9").that(ad.get(0)).isEqualTo(9);
        assertWithMessage("再次添加后，get(1) 应该返回 7").that(ad.get(1)).isEqualTo(7);
    }

    @Test
    /* 测试当元素大量移除后，缩容 (Downsizing) 是否能正确工作。 */
    public void removePastThresholdShouldResizeDown() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        for (int i = 0; i < 20; i++) {
            ad.addLast(i);
        }
        for (int i = 0; i < 16; i++) {
            ad.removeLast();
        }
        assertWithMessage("移除 16 个元素后，size 应该为 4").that(ad.size()).isEqualTo(4);
        List<Integer> expected = List.of(0, 1, 2, 3);
        assertWithMessage("缩容后 toList() 的结果不正确").that(ad.toList()).isEqualTo(expected);
        assertWithMessage("缩容后 get(3) 应该返回 3").that(ad.get(3)).isEqualTo(3);
    }


    // ---------- 以下是新增的、用于覆盖缺失场景的测试 ----------

    @Test
    public void emptyDequeOperationsTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        assertWithMessage("新建的队列 toList() 应该返回空列表").that(ad.toList()).isEmpty();
        assertWithMessage("在空队列上 get(0) 应该返回 null").that(ad.get(0)).isNull();
        assertWithMessage("新建的队列 isEmpty() 应该返回 true").that(ad.isEmpty()).isTrue();
    }

    @Test
    /* [新增] 测试移除最后一个元素后的状态。 */
    public void removeLastElementMakesDequeEmptyTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(5);
        assertThat(ad.removeFirst()).isEqualTo(5);
        assertWithMessage("移除最后一个元素后，isEmpty() 应该返回 true").that(ad.isEmpty()).isTrue();
        assertWithMessage("移除最后一个元素后，size() 应该为 0").that(ad.size()).isEqualTo(0);
        assertWithMessage("移除最后一个元素后，toList() 应该返回空列表").that(ad.toList()).isEmpty();
    }

    @Test
    /* [新增] 专门测试“在移除后添加”的场景。 */
    public void addAfterRemoveTest() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("a");
        ad.addLast("b");
        ad.removeFirst(); // Deque: ["b"]
        ad.addFirst("c"); // Deque: ["c", "b"]
        assertThat(ad.toList()).containsExactly("c", "b").inOrder();
        assertThat(ad.size()).isEqualTo(2);

        ad.removeLast(); // Deque: ["c"]
        ad.addLast("d"); // Deque: ["c", "d"]
        assertThat(ad.toList()).containsExactly("c", "d").inOrder();
        assertThat(ad.size()).isEqualTo(2);
    }

    @Test
    /* [新增] 更复杂的“在移除后添加”场景，混合使用 addFirst 和 addLast。 */
    public void addAfterManyRemovalsTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        // 1. 加满，使其环绕
        for (int i = 0; i < 8; i++) {
            ad.addFirst(i);
        } // Deque: [7, 6, 5, 4, 3, 2, 1, 0]

        // 2. 移除大部分
        for (int i = 0; i < 7; i++) {
            ad.removeLast();
        } // Deque: [7]
        assertWithMessage("移除7个元素后，size 应该为 1").that(ad.size()).isEqualTo(1);

        // 3. 再次添加，测试指针是否正确
        ad.addLast(8); // Deque: [7, 8]
        ad.addFirst(6); // Deque: [6, 7, 8]
        List<Integer> expected = List.of(6, 7, 8);
        assertThat(ad.toList()).isEqualTo(expected);
    }

    @Test
    /* [新增] 综合测试，混合多种操作并持续检查 size 和 isEmpty。 */
    public void comprehensiveMixedOperationsTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        assertThat(ad.isEmpty()).isTrue();

        ad.addFirst(10); // [10]
        assertThat(ad.size()).isEqualTo(1);
        assertThat(ad.isEmpty()).isFalse();

        ad.addFirst(5); // [5, 10]
        assertThat(ad.size()).isEqualTo(2);

        ad.addLast(20); // [5, 10, 20]
        assertThat(ad.size()).isEqualTo(3);

        assertThat(ad.removeLast()).isEqualTo(20); // [5, 10]
        assertThat(ad.size()).isEqualTo(2);

        assertThat(ad.removeFirst()).isEqualTo(5); // [10]
        assertThat(ad.size()).isEqualTo(1);

        assertThat(ad.removeLast()).isEqualTo(10); // []
        assertThat(ad.size()).isEqualTo(0);
        assertThat(ad.isEmpty()).isTrue();
    }
    @Test
    /*
      [终极测试] 专门测试将队列移除至空后，再调用 addLast 的情况。
      这对应 "add_last_after_remove_to_empty" flag。
     */
    public void addLastAfterRemovingToEmptyTest() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        // 1. 添加一些元素
        ad.addFirst(10);
        ad.addFirst(20);
        ad.addFirst(30);

        // 2. 将它们全部移除
        ad.removeLast();
        ad.removeLast();
        ad.removeLast();

        // 3. 确认队列现在是空的
        assertWithMessage("移除所有元素后，队列应该为空").that(ad.isEmpty()).isTrue();

        // 4. 在空队列上调用 addLast
        ad.addLast(50);

        // 5. 检查状态是否正确
        assertWithMessage("将空队列 addLast 后，size 应该为 1").that(ad.size()).isEqualTo(1);
        assertWithMessage("将空队列 addLast 后，队列不应为空").that(ad.isEmpty()).isFalse();
        assertWithMessage("将空队列 addLast 后，get(0) 应该返回新元素").that(ad.get(0)).isEqualTo(50);
        assertThat(ad.toList()).containsExactly(50);
    }

    @Test
    void testIntegrated1() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(0);
        ad.addFirst(1);
        assertThat(ad.removeLast()).isEqualTo(0);
        assertThat(ad.get(0)).isEqualTo(1);
        ad.addLast(4);
        ad.addFirst(5);
        assertThat(ad.removeLast()).isEqualTo(4);
        assertThat(ad.get(0)).isEqualTo(5);
        ad.addFirst(8);
        assertThat(ad.removeLast()).isEqualTo(1);
        assertThat(ad.removeLast()).isEqualTo(5);
        ad.addFirst(11);
        ad.addLast(12);
        ad.addLast(13);
        assertThat(ad.removeFirst()).isEqualTo(11);
        ad.addFirst(15);
        ad.addFirst(16);
        ad.addFirst(17);
        assertThat(ad.removeFirst()).isEqualTo(17);
        ad.addLast(19);
        assertThat(ad.removeLast()).isEqualTo(19);
        assertThat(ad.removeFirst()).isEqualTo(16);
        assertThat(ad.removeLast()).isEqualTo(13);
    }
    @Test
    void testIntegrated2() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(11);
        ad.removeLast();
        ad.addFirst(12);
        assertThat(ad.get(0)).isEqualTo(12);
    }
}
