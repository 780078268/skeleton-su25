import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    /** 测试交替的添加和移除操作。 */
    public void alternatingAddRemoveTest() {
        Deque61B<Double> lld = new LinkedListDeque61B<>();

        lld.addFirst(1.0);
        lld.removeFirst();
        assertWithMessage("添加后立即移除应为空").that(lld.isEmpty()).isTrue();

        lld.addLast(2.0);
        lld.removeLast();
        assertWithMessage("添加后立即移除应为空").that(lld.isEmpty()).isTrue();

        // 交替操作序列
        lld.addFirst(1.0);    // [1.0]
        lld.addLast(2.0);     // [1.0, 2.0]
        double r1 = lld.removeFirst();  // [2.0]
        lld.addFirst(3.0);    // [3.0, 2.0]
        double r2 = lld.removeLast();   // [3.0]

        assertWithMessage("第一次移除的应该是 1.0").that(r1).isEqualTo(1.0);
        assertWithMessage("第二次移除的应该是 2.0").that(r2).isEqualTo(2.0);
        assertWithMessage("最终 size 应该为 1").that(lld.size()).isEqualTo(1);
        assertWithMessage("最终链表应该为 [3.0]").that(lld.toList()).containsExactly(3.0);
    }
    @Test
    /** 测试对单元素列表的移除操作。 */
    public void removeOnSingleElementListTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        lld1.addFirst(5);
        int removed = lld1.removeLast();
        assertWithMessage("对单元素列表 removeLast 应该返回该元素").that(removed).isEqualTo(5);
        assertWithMessage("对单元素列表 removeLast 后应为空").that(lld1.isEmpty()).isTrue();

        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
        lld2.addLast(10);
        removed = lld2.removeFirst();
        assertWithMessage("对单元素列表 removeFirst 应该返回该元素").that(removed).isEqualTo(10);
        assertWithMessage("对单元素列表 removeFirst 后应为空").that(lld2.isEmpty()).isTrue();
    }

    @Test
    /** 测试大规模添加和移除操作，检查性能和正确性。 */
    public void bigDequeTest() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        for (int i = 0; i < 100; i++) {
            lld.addLast(i);
        }

        assertWithMessage("添加 100 个元素后 size 应为 100").that(lld.size()).isEqualTo(100);
        assertWithMessage("get(0) 应该为 0").that(lld.get(0)).isEqualTo(0);
        assertWithMessage("get(50) 应该为 50").that(lld.get(50)).isEqualTo(50);
        assertWithMessage("get(99) 应该为 99").that(lld.get(99)).isEqualTo(99);

        for (int i = 0; i < 100; i++) {
            int removed = lld.removeFirst();
            assertWithMessage("移除的元素应该与添加的顺序一致").that(removed).isEqualTo(i);
        }

        assertWithMessage("移除所有元素后应为空").that(lld.isEmpty()).isTrue();
    }
    @Test
    /** 全面测试 removeFirst 和 removeLast 方法。 */
    public void removeFirstAndLastTest() {
        Deque61B<String> lld = new LinkedListDeque61B<>();

        // 添加初始元素
        lld.addLast("a");
        lld.addLast("b");
        lld.addLast("c");
        lld.addLast("d"); // ["a", "b", "c", "d"]

        // 测试 removeFirst
        String removedFirst = lld.removeFirst();
        assertWithMessage("removeFirst 应该返回被移除的元素 'a'").that(removedFirst).isEqualTo("a");
        assertWithMessage("移除后，链表应该为 ['b', 'c', 'd']").that(lld.toList()).containsExactly("b", "c", "d").inOrder();
        assertWithMessage("移除后 size 应该为 3").that(lld.size()).isEqualTo(3);

        // 测试 removeLast
        String removedLast = lld.removeLast();
        assertWithMessage("removeLast 应该返回被移除的元素 'd'").that(removedLast).isEqualTo("d");
        assertWithMessage("再次移除后，链表应该为 ['b', 'c']").that(lld.toList()).containsExactly("b", "c").inOrder();
        assertWithMessage("再次移除后 size 应该为 2").that(lld.size()).isEqualTo(2);

        // 将链表移除至空
        lld.removeFirst(); // 移除 "b"
        lld.removeLast();  // 移除 "c"
        assertWithMessage("链表移除至空后应该为空").that(lld.isEmpty()).isTrue();
        assertThat(lld.toList()).isEmpty();

        // 测试从空链表中移除
        String removedFromEmptyFirst = lld.removeFirst();
        String removedFromEmptyLast = lld.removeLast();
        assertWithMessage("从空链表中 removeFirst 应该返回 null").that(removedFromEmptyFirst).isNull();
        assertWithMessage("从空链表中 removeLast 应该返回 null").that(removedFromEmptyLast).isNull();
        assertWithMessage("从空链表中移除后 size 仍为 0").that(lld.size()).isEqualTo(0);
    }
    @Test
    /** 测试 getRecursive 方法，逻辑与 getTest 相同。 */
    public void getRecursiveTest() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        // 对空链表调用 getRecursive
        assertWithMessage("对空链表调用 getRecursive 应该返回 null").that(lld.getRecursive(0)).isNull();

        lld.addLast(10); // [10]
        lld.addLast(20); // [10, 20]
        lld.addFirst(5); // [5, 10, 20]

        // 测试有效索引
        assertWithMessage("getRecursive(0) 应该返回第一个元素").that(lld.getRecursive(0)).isEqualTo(5);
        assertWithMessage("getRecursive(1) 应该返回第二个元素").that(lld.getRecursive(1)).isEqualTo(10);
        assertWithMessage("getRecursive(2) 应该返回最后一个元素").that(lld.getRecursive(2)).isEqualTo(20);

        // 测试无效索引
        assertWithMessage("getRecursive(-1) 应该返回 null").that(lld.getRecursive(-1)).isNull();
        assertWithMessage("getRecursive(3) (越界) 应该返回 null").that(lld.getRecursive(3)).isNull();
    }

    @Test
    /** 测试 isEmpty 和 size 方法在各种情况下的行为。 */
    public void isEmptyAndSizeTest() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        // 初始状态
        assertWithMessage("一个新创建的链表应该是空的").that(lld.isEmpty()).isTrue();
        assertWithMessage("一个新创建的链表的 size 应该是 0").that(lld.size()).isEqualTo(0);

        // 添加一个元素后
        lld.addFirst(10);
        assertWithMessage("添加一个元素后链表不应为空").that(lld.isEmpty()).isFalse();
        assertWithMessage("添加一个元素后 size 应该是 1").that(lld.size()).isEqualTo(1);

        // 添加更多元素后
        lld.addLast(20);
        lld.addFirst(5);
        assertWithMessage("添加多个元素后 size 应该是 3").that(lld.size()).isEqualTo(3);

        // 移除所有元素后 (这部分也间接测试了 remove 方法)
        lld.removeFirst();
        lld.removeLast();
        lld.removeFirst();
        assertWithMessage("移除所有元素后链表应该为空").that(lld.isEmpty()).isTrue();
        assertWithMessage("移除所有元素后 size 应该是 0").that(lld.size()).isEqualTo(0);
    }

    @Test
    /** 测试 get 方法，包括有效和无效的索引。 */
    public void getTest() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        // 对空链表调用 get
        assertWithMessage("对空链表调用 get 应该返回 null").that(lld.get(0)).isNull();

        lld.addLast(10); // [10]
        lld.addLast(20); // [10, 20]
        lld.addFirst(5); // [5, 10, 20]

        // 测试有效索引
        assertWithMessage("get(0) 应该返回第一个元素").that(lld.get(0)).isEqualTo(5);
        assertWithMessage("get(1) 应该返回第二个元素").that(lld.get(1)).isEqualTo(10);
        assertWithMessage("get(2) 应该返回最后一个元素").that(lld.get(2)).isEqualTo(20);

        // 测试无效索引
        assertWithMessage("get(-1) 应该返回 null").that(lld.get(-1)).isNull();
        assertWithMessage("get(3) (越界) 应该返回 null").that(lld.get(3)).isNull();
        assertWithMessage("get(100) (远超出界) 应该返回 null").that(lld.get(100)).isNull();
    }
}