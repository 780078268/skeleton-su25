import org.junit.Test;
import java.util.NoSuchElementException;
import static com.google.common.truth.Truth.*;

public class MinHeapPQTest {

    /**
     * Tests that a new MinHeapPQ is empty.
     */
    @Test
    public void testEmptyPQ() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        assertThat(pq.size()).isEqualTo(0);
        assertThat(pq.poll()).isNull();
    }

    /**
     * Tests that a MinHeapPQ can add and remove a single element.
     */
    @Test
    public void testAddAndPollOne() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("A", 1.0);
        assertThat(pq.size()).isEqualTo(1);
        String item = pq.poll();
        assertThat(item).isEqualTo("A");
        assertThat(pq.size()).isEqualTo(0);
    }

    /**
     * A comprehensive test that adds multiple items with jumbled priorities
     * and ensures they are polled in the correct order.
     */
    @Test
    public void testAddAndPollMultiple() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("C", 3.0);
        pq.insert("A", 1.0);
        pq.insert("E", 5.0);
        pq.insert("B", 2.0);
        pq.insert("D", 4.0);

        assertThat(pq.size()).isEqualTo(5);

        // Items should be polled in order of their priority (1.0, 2.0, 3.0, etc.)
        assertThat(pq.poll()).isEqualTo("A");
        assertThat(pq.poll()).isEqualTo("B");
        assertThat(pq.poll()).isEqualTo("C");
        assertThat(pq.poll()).isEqualTo("D");
        assertThat(pq.poll()).isEqualTo("E");

        assertThat(pq.size()).isEqualTo(0);
    }

    /**
     * Tests the contains method.
     */
    @Test
    public void testContains() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("apple", 10.0);
        pq.insert("banana", 5.0);

        assertThat(pq.contains("apple")).isTrue();
        assertThat(pq.contains("banana")).isTrue();
        assertThat(pq.contains("cherry")).isFalse();
    }

    /**
     * Tests changePriority by making an item's priority higher (value lower),
     * causing it to bubble up and become the new minimum.
     */
    @Test
    public void testChangePriorityToBecomeMin() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("A", 10.0);
        pq.insert("B", 20.0);
        pq.insert("C", 30.0);

        // Change "C"'s priority to be the smallest
        pq.changePriority("C", 5.0);

        // Now "C" should be the first item polled.
        assertThat(pq.poll()).isEqualTo("C");
        assertThat(pq.poll()).isEqualTo("A");
    }

    /**
     * Tests changePriority by making the minimum item's priority lower (value higher),
     * causing it to bubble down.
     */
    @Test
    public void testChangePriorityToNoLongerBeMin() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("A", 10.0);
        pq.insert("B", 20.0);
        pq.insert("C", 30.0);

        // Change "A"'s priority to be the largest
        pq.changePriority("A", 40.0);

        // Now "B" should be the first item polled.
        assertThat(pq.poll()).isEqualTo("B");
        assertThat(pq.poll()).isEqualTo("C");
        assertThat(pq.poll()).isEqualTo("A");
    }

    /**
     * Tests that changing the priority of a non-existent element throws an exception.
     * This uses the standard JUnit 4 way to test for expected exceptions.
     */
    @Test(expected = NoSuchElementException.class)
    public void testChangePriorityOnNonExistentElement() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("A", 1.0);
        pq.changePriority("Z", 2.0); // This line should throw the exception.
    }

    /**
     * Tests that adding a duplicate item throws an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddDuplicateThrowsException() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert("item", 1.0);
        pq.insert("item", 2.0); // This line should throw the exception.
    }

    /**
     * Tests that adding a null item throws an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNullItemThrowsException() {
        MinHeapPQ<String> pq = new MinHeapPQ<>();
        pq.insert(null, 1.0); // This line should throw the exception.
    }

    /**
     * Tests a mix of operations to ensure stability.
     */
    @Test
    public void testMixedOperations() {
        MinHeapPQ<Integer> pq = new MinHeapPQ<>();
        pq.insert(1, 1.0);
        pq.insert(2, 2.0);
        pq.insert(3, 3.0);

        // Poll the min
        assertThat(pq.poll()).isEqualTo(1);

        // Add a new min
        pq.insert(0, 0.0);
        assertThat(pq.poll()).isEqualTo(0);

        // Change priority
        pq.changePriority(3, 1.5); // 3's new priority is 1.5
        assertThat(pq.poll()).isEqualTo(3);
        assertThat(pq.poll()).isEqualTo(2);
        assertThat(pq.size()).isEqualTo(0);
    }
}
