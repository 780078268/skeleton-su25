
    package deque;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

    /**
     * 这是针对 LinkedListDeque61B 的测试文件。
     */
    public class LinkedListDeque61BTest {

        //######################################################################
        //###                      iterator() Tests                          ###
        //######################################################################

        @Test
        public void iteratorTest() {
            // 创建一个 LinkedListDeque61B 实例
            Deque61B<String> lld = new LinkedListDeque61B<>();
            lld.addLast("front");
            lld.addLast("middle");
            lld.addLast("back");

            // 使用 for-each 循环遍历，这会隐式调用 iterator()
            List<String> iteratedItems = new ArrayList<>();
            for (String s : lld) {
                iteratedItems.add(s);
            }

            // 验证迭代的元素是否按顺序正确
            assertThat(iteratedItems).containsExactly("front", "middle", "back").inOrder();
        }

        @Test
        public void iteratorEmptyTest() {
            // 测试空队列的迭代器
            Deque61B<String> lld = new LinkedListDeque61B<>();
            int count = 0;
            for (String s : lld) {
                count++;
            }
            // 确认没有元素被迭代
            assertThat(count).isEqualTo(0);
        }


        //######################################################################
        //###                       equals() Tests                           ###
        //######################################################################

        @Test
        public void equalsBasicTest() {
            // 测试两个内容相同的 LinkedListDeque61B
            Deque61B<String> lld1 = new LinkedListDeque61B<>();
            lld1.addLast("a");
            lld1.addLast("b");
            lld1.addLast("c");

            Deque61B<String> lld2 = new LinkedListDeque61B<>();
            lld2.addLast("a");
            lld2.addLast("b");
            lld2.addLast("c");

            // 验证它们相等
            assertThat(lld1).isEqualTo(lld2);
        }

        @Test
        public void equalsDifferentImplementationsTest() {
            // 测试 LinkedListDeque61B 是否能与内容相同的 ArrayDeque61B 相等
            Deque61B<Character> lld = new LinkedListDeque61B<>();
            lld.addLast('x');
            lld.addLast('y');

            Deque61B<Character> ad = new ArrayDeque61B<>();
            ad.addLast('x');
            ad.addLast('y');

            // 如果内容相同，即使实现不同，也应该相等
            assertThat(lld).isEqualTo(ad);
            assertThat(ad).isEqualTo(lld); // 反向测试也应该成立
        }

        @Test
        public void equalsNegativeTest() {
            Deque61B<String> lld1 = new LinkedListDeque61B<>();
            lld1.addLast("a");
            lld1.addLast("b");

            Deque61B<String> lld2 = new LinkedListDeque61B<>();
            lld2.addLast("a");
            lld2.addLast("c"); // 内容不同

            Deque61B<String> lld3 = new LinkedListDeque61B<>();
            lld3.addLast("a"); // 长度不同

            // 测试内容不同的情况
            assertThat(lld1).isNotEqualTo(lld2);
            // 测试长度不同的情况
            assertThat(lld1).isNotEqualTo(lld3);
            // 测试与 null 比较
            assertThat(lld1).isNotEqualTo(null);
            // 测试与不同类型的对象比较
            assertThat(lld1).isNotEqualTo("a string object");
        }

        @Test
        public void equalsSelfTest() {
            Deque61B<Integer> lld = new LinkedListDeque61B<>();
            lld.addLast(5);
            // 一个对象应该等于它自己
            assertThat(lld).isEqualTo(lld);
        }

        //######################################################################
        //###                       toString() Tests                         ###
        //######################################################################

        @Test
        public void toStringTest() {
            Deque61B<String> lld = new LinkedListDeque61B<>();
            lld.addLast("front");
            lld.addLast("middle");
            lld.addLast("back");

            String expected = "[front, middle, back]";
            assertThat(lld.toString()).isEqualTo(expected);
        }

        @Test
        public void toStringEmptyTest() {
            Deque61B<Double> lld = new LinkedListDeque61B<>();
            assertThat(lld.toString()).isEqualTo("[]");
        }
    }
