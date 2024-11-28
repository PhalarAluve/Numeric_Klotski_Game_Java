
public class Move {
    private int moveBlock;
    private String direction;
    private int indexOfBoard;
    private int[][] zeros;
    private int[][] searched;

    public Move(int movedBlock, String direction, int indexOfBoard, int[][] zeros, int[][] searched ){
        this.moveBlock = movedBlock;
        this.direction = direction;
        this.indexOfBoard = indexOfBoard;
        this.zeros = zeros.clone();
        this.searched = searched.clone();
    }

    public int getMoveBlock() {
        return moveBlock;
    }

    public void setMoveBlock(int moveBlock) {
        this.moveBlock = moveBlock;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getIndexOfBoard() {
        return indexOfBoard;
    }

    public void setIndexOfBoard(int indexOfBoard) {
        this.indexOfBoard = indexOfBoard;
    }

    public int[][] getSearched() {
        int[][] copy = new int[searched.length][searched[0].length];
        for (int i = 0; i < searched.length; i++) {
            copy[i] = searched[i].clone();
        }
        return copy;
    }

    public void setSearched(int[][] searched) {
        this.searched = searched;
    }

    public int[][] getZeros() {
        int[][] copy = new int[zeros.length][zeros[0].length];
        for (int i = 0; i < zeros.length; i++) {
            copy[i] = zeros[i].clone();
        }
        return copy;
    }

    public int[][] copyArray2D(int[][] in){
        int[][] out = new int[in.length][in[0].length];
        for (int i = 0; i < in.length; i++) {
            out[i] = in[i].clone();
        }
        return out;
    }

    public void setZeros(int[][] zeros) {
        this.zeros = zeros;
    }
}
