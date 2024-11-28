public class Block {
    private int[] block;
    private int initialNumber;
    private int blockRow;
    private int blockCol;


    public Block (int[] block, int initialNumber, int blockRow, int blockCol){
        this.block = block.clone();
        this.initialNumber = initialNumber;
        this.blockRow = blockRow;
        this.blockCol = blockCol;
    }

    public int size(){
        return block.length;
    }

    public int[] getBlock() {
        return block.clone();
    }

    public void setBlock(int[] block) {
        this.block = block;
    }

    public int getInitialNumber() {
        return initialNumber;
    }

    public void setInitialNumber(int initialNumber) {
        this.initialNumber = initialNumber;
    }

    public int getBlockRow() {
        return blockRow;
    }

    public void setBlockRow(int blockRow) {
        this.blockRow = blockRow;
    }

    public int getBlockCol() {
        return blockCol;
    }

    public void setBlockCol(int blockCol) {
        this.blockCol = blockCol;
    }
}
