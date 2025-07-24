import org.junit.Test;

import static com.google.common.truth.Truth.*;

public class SLListTest {

    @Test
    public void testSLListAdd() {
        SLList test1 = SLList.of(1, 3, 5); /* test1: {1, 3, 5} */
        SLList test2 = new SLList(); /* test2: {} */

        test1.add(1, 2); /* test1: {1, 2, 3, 5}*/
        test1.add(3, 4); /* test1: {1, 2, 3, 4, 5}*/
        assertWithMessage("test1 does not have a size of 5").that(test1.size()).isEqualTo(5);
        assertWithMessage("test1 does not have 3 at index 2 or 4 at index 3").that(test1.equals(SLList.of(1, 2, 3, 4, 5))).isTrue();

        test2.add(1, 1); /* test2: {1} */
        assertWithMessage("test2 does not contain 1").that(test2.equals(SLList.of(1))).isTrue();
        assertWithMessage("test2 does not have a size of 1").that(test2.size()).isEqualTo(1);

        test2.add(10, 10); /* test2: {1, 10} */
        assertWithMessage("test2 is incorrect after adding at an out-of-bounds index").that(test2.equals(SLList.of(1, 10))).isTrue();
        test1.add(0, 0); /* test1: {0, 1, 2, 3, 4, 5}*/
        assertWithMessage("test1 is incorrect after addition at the front").that(test1.equals(SLList.of(0, 1, 2, 3, 4, 5))).isTrue();
    }

    @Test
    public void testSLListReverse() {
// --- 测试1: 反转一个常规链表 ---
        SLList test1 = SLList.of(1, 3, 5); /* test1: {1, 3, 5} */
        test1.reverse(); /* test1 应该变为: {5, 3, 1} */
        assertWithMessage("test1 反转后内容不正确").that(test1.equals(SLList.of(5, 3, 1))).isTrue();
        assertWithMessage("test1 反转后 size 不应改变").that(test1.size()).isEqualTo(3);


// --- 测试2: 反转一个空链表 ---
        SLList test2 = new SLList(); /* test2: {} */
        test2.reverse(); /* test2 应该仍然为: {} */
        assertWithMessage("空链表反转后应仍为空").that(test2.equals(new SLList())).isTrue();
        assertWithMessage("空链表反转后 size 应仍为 0").that(test2.size()).isEqualTo(0);


// --- 测试3: 反转只有一个元素的链表 ---
        SLList test3 = SLList.of(42); /* test3: {42} */
        test3.reverse(); /* test3 应该仍然为: {42} */
        assertWithMessage("单元素链表反转后应不变").that(test3.equals(SLList.of(42))).isTrue();


// --- 测试4: 连续反转两次，应回到原始状态 ---
        SLList test4 = SLList.of(10, 20, 30, 40); /* test4: {10, 20, 30, 40} */
        test4.reverse(); /* test4 变为: {40, 30, 20, 10} */
        test4.reverse(); /* test4 应该变回: {10, 20, 30, 40} */
        assertWithMessage("链表反转两次后应回到初始状态").that(test4.equals(SLList.of(10, 20, 30, 40))).isTrue();
        assertWithMessage("链表反转两次后 size 不应改变").that(test4.size()).isEqualTo(4);
    }
}
