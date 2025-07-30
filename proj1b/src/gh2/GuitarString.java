package gh2;

import deque.ArrayDeque61B;
import deque.Deque61B;

/*
 * NOTE: Implementation of GuitarString is OPTIONAL practice, and will not be tested in the autograder.
 * This class will not compile until the Deque61B implementations are complete.
 */

public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque61B<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // 正确的计算公式应该是采样率 SR 除以频率 frequency
        int capacity = (int) Math.round(SR / frequency);

        // 如果容量小于1，这会导致一个空的 buffer，是不对的。
        // 我们可以强制让它至少为1，尽管对于非常高的频率这在物理上可能不精确，但可以避免程序崩溃。
        if (capacity < 1) {
            capacity = 1;
        }

        buffer = new ArrayDeque61B<Double>();
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // 获取 buffer 的容量
        int capacity = buffer.size();

        // 正确地清空 buffer
        for (int i = 0; i < capacity; i++) {
            buffer.removeFirst();
        }

        // 正确地用随机数填满 buffer
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(Math.random() - 0.5);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // 1. 先取出队首的样本
        double frontSample = buffer.removeFirst();

        // 2. 获取新的队首样本 (原来的第二个)
        double secondSample = buffer.get(0);

        // 3. 计算新的样本并添加到队尾
        double newSample = (frontSample + secondSample) * 0.5 * DECAY;
        buffer.addLast(newSample);
    }


    /* Return the double at the front of the buffer. */


    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}
