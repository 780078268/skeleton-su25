import java.util.ArrayList;
import java.util.List;

public class ArrayExercises {

    /**
     * Returns an array [1, 2, 3, 4, 5, 6]
     */
    public static int[] makeDice() {
        // TODO: Fill in this function.
        int[] arr = new int[6];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        return arr;
    }

    /**
     * Returns the positive difference between the maximum element and minimum element of the given array.
     * Assumes array is nonempty.
     */
    public static int findMinMax(int[] array) {
        // TODO: Fill in this function.
        int min = array[0];
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return (max - min);
    }

}
