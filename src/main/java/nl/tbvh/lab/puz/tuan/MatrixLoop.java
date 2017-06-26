package nl.tbvh.lab.puz.tuan;

public class MatrixLoop {

    static // @formatter:off
    int[][] SAMPLE_MATRIX = new int[][]{
        { 1, 2,3,4, 99},
        { 5,6,7,8, 98},
        { 9,10,11,12,97},
        { 13,14,15,16,96}
    };
    // @formatter:on

    private int[][] matrix;
    private int width;

    private int height;
    private int top;

    private int bottom;

    private int left;

    private int right;

    private int x;

    private int y;

    private int dx;

    private int dy;

    public MatrixLoop(int[][] matrix) {
        this.matrix = matrix;
        height = matrix.length;
        width = matrix[0].length;
    }

    private void print() {
        top = 0;
        bottom = height - 1;
        left = 0;
        right = width - 1;

        x = 0;
        y = 0;
        dx = 1;
        dy = 0;

        while (top != bottom || left != right) {
            System.out.println(matrix[y][x] + " --" + top + " " + bottom + " / " + left + " " + right);
            nextStep();
        }
        System.out.println(matrix[y][x] + " --" + top + " " + bottom + " / " + left + " " + right);
    }

    private void nextStep() {
        if (dx == 1) {
            if (x == right) {
                dx = 0;
                dy = 1;
                top++;
            }
        } else if (dy == 1) {
            if (y == bottom) {
                dy = 0;
                dx = -1;
                right--;
            }
        } else if (dx == -1) {
            if (x == left) {
                dx = 0;
                dy = -1;
                bottom--;
            }
        } else if (dy == -1) {
            if (y == top) {
                dy = 0;
                dx = 1;
                left++;
            }
        }
        x += dx;
        y += dy;
    }

    public static void main(String[] args) {
        new MatrixLoop(SAMPLE_MATRIX).print();
    }
}
