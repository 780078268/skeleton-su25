public class TriangleDrawer {
    public static void drawTriangle() {
        int col = 0;
        int row = 0;
        int SIZE = 5;

        while (row < SIZE) {
            col = 0;
            while (col <= row) {
                System.out.print('*');
                col++;
            }
            System.out.println();
            row++;
        }
    }

    public static void main(String[] args) {
        drawTriangle();
    }
}
