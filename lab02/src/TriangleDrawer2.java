public class TriangleDrawer2 {
    public static void drawTriangle(){
        int col = 0;
        int row = 0;
        int SIZE = 5;
        for(int i = 1; i <= SIZE; i++){
            for(int j = 0; j <=col; j++){
                System.out.print("*");
            }
            System.out.println();
            col++;
        }
    }
    public static void main(String[] args) {
        drawTriangle();
    }
}
