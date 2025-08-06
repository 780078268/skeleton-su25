import org.junit.Test;
import static org.junit.Assert.*;

public class MinHeapTest {

    @Test
    public void testInitialization() {
        MinHeap<Integer> heap = new MinHeap<>();
        assertEquals(0, heap.size());
        assertNull(heap.findMin());
    }

    @Test
    public void testInsertSingleElement() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(10);
        assertEquals(1, heap.size());
        assertEquals(Integer.valueOf(10), heap.findMin());
    }

    @Test
    public void testInsertMultipleAndFindMin() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(5);
        assertEquals(Integer.valueOf(5), heap.findMin());
        heap.insert(10);
        assertEquals(Integer.valueOf(5), heap.findMin());
        heap.insert(3);
        assertEquals(Integer.valueOf(3), heap.findMin());
        heap.insert(12);
        assertEquals(Integer.valueOf(3), heap.findMin());
        heap.insert(1);
        assertEquals(Integer.valueOf(1), heap.findMin());

        assertEquals(5, heap.size());
    }

    @Test
    public void testInsertDescendingOrderBubbleUp() {
        // 这个测试专门用来验证 bubbleUp 是否正常工作
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(10);
        heap.insert(9);
        heap.insert(8);
        heap.insert(7);
        assertEquals(4, heap.size());
        assertEquals(Integer.valueOf(7), heap.findMin());
    }

    @Test
    public void testRemoveMinOnEmptyHeap() {
        MinHeap<String> heap = new MinHeap<>();
        assertNull(heap.removeMin());
    }

    @Test
    public void testRemoveMinTheMostImportantTest() {
        // 这个测试是检验整个堆是否正确的关键
        // 它同时测试了 insert, bubbleUp, removeMin, 和 bubbleDown
        MinHeap<Integer> heap = new MinHeap<>();
        // 乱序插入
        heap.insert(5);
        heap.insert(2);
        heap.insert(8);
        heap.insert(1);
        heap.insert(6);
        heap.insert(10);
        heap.insert(4);

        assertEquals(7, heap.size());

        // 按顺序移除最小值
        assertEquals(Integer.valueOf(1), heap.removeMin());
        assertEquals(6, heap.size());
        assertEquals(Integer.valueOf(2), heap.findMin()); // 检查新的最小值

        assertEquals(Integer.valueOf(2), heap.removeMin());
        assertEquals(5, heap.size());

        assertEquals(Integer.valueOf(4), heap.removeMin());
        assertEquals(4, heap.size());

        assertEquals(Integer.valueOf(5), heap.removeMin());
        assertEquals(3, heap.size());

        assertEquals(Integer.valueOf(6), heap.removeMin());
        assertEquals(2, heap.size());
        assertEquals(Integer.valueOf(8), heap.findMin()); // 检查新的最小值

        assertEquals(Integer.valueOf(8), heap.removeMin());
        assertEquals(1, heap.size());

        assertEquals(Integer.valueOf(10), heap.removeMin());
        assertEquals(0, heap.size());

        // 堆现在应该是空的
        assertNull(heap.findMin());
        assertNull(heap.removeMin());
    }

    @Test
    public void testInsertDuplicateThrowsException() {
        MinHeap<String> heap = new MinHeap<>();
        heap.insert("apple");

        // 使用 JUnit 的 assertThrows 来验证是否抛出了正确的异常
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            heap.insert("apple");
        });

        // 可选：检查异常消息
        // String expectedMessage = "Element already exists in the heap.";
        // String actualMessage = exception.getMessage();
        // assertTrue(actualMessage.contains(expectedMessage));

        // 确保插入失败后，堆的大小没有改变
        assertEquals(1, heap.size());
    }

    @Test
    public void testStringHeap() {
        MinHeap<String> heap = new MinHeap<>();
        heap.insert("zebra");
        heap.insert("apple");
        heap.insert("cat");
        heap.insert("banana");

        assertEquals("apple", heap.removeMin());
        assertEquals("banana", heap.removeMin());
        assertEquals("cat", heap.removeMin());
        assertEquals("zebra", heap.removeMin());
        assertEquals(0, heap.size());
    }

    @Test
    public void testContains() {
        MinHeap<Integer> heap = new MinHeap<>();
        heap.insert(10);
        heap.insert(5);
        heap.insert(15);

        assertTrue(heap.contains(10));
        assertTrue(heap.contains(5));
        assertTrue(heap.contains(15));
        assertFalse(heap.contains(99));
    }

}