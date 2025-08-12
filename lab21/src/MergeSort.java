public class MergeSort {


    /**
     * @param arr
     *
     * Sort the array arr using merge sort.
     * The merge sort algorithm is as follows:
     * 1. Split the collection to be sorted in half.
     * 2. Recursively call merge sort on each half.
     * 3. Merge the sorted half-lists.
     *
     */
    public static int[] sort(int[] arr) {
        if (arr.length == 1) {
            return arr;
        }else{
        int n = arr.length/2;
        int m = arr.length - n;
        int[] left = new int[n];
        int[] right = new int[m];
        for(int i=0; i<n; i++) {
            left[i] = arr[i];
        }
        for(int i=0; i<m; i++) {
            right[i] = arr[n+i];
        }
         return merge(sort(left),sort(right));
        }
    }

    /**
     * @param a
     * @param b
     *
     * Merge the sorted half-lists.
     *
     * Suggested helper method that will make it easier for you to implement merge sort.
     */
    private static int[] merge(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                c[k] = a[i];
                i++;
                k++;
            } else {
                c[k] = b[j];
                j++;
                k++;
            }
        }
        while (i < a.length) {
                c[k] = a[i];
                k++;
                i++;
        }
        while (j < b.length) {
                c[k] = b[j];
                k++;
                j++;
        }
        return c;
    }
}

