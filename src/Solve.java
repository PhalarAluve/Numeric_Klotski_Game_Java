import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import edu.princeton.cs.algs4.Stopwatch;


public class Solve {

    public static void main(String[] args) {
        HuaRongDaoGame huaRongDaoGame = new HuaRongDaoGame();
        GameJFrame gameJFrame = new GameJFrame();
        Scanner input = new Scanner(System.in);
        int kloRow = input.nextInt();
        int kloCol = input.nextInt();
        gameJFrame.setKloRow(kloRow);
        gameJFrame.setKloCol(kloCol);
        int[][] klo = new int[kloRow][kloCol];
        List<Block> blockList = new ArrayList<>();

        int[][] zeros1 = new int[kloRow*kloCol][4];
        int count0 = 0;
        for (int i = 0; i < kloRow; i++) {
            for (int j = 0; j < kloCol; j++) {
                int in = input.nextInt();
                if(in == 0){
                    zeros1[count0][0] = i;
                    zeros1[count0][1] = j;
                    count0++;
                }
                klo[i][j] = in;
            }
        }
        int[][] zeros = new int[count0][2];
        for (int i = 0; i < count0; i++) {
            zeros[i][0] = zeros1[i][0];
            zeros[i][1] = zeros1[i][1];
        }

        if (input.hasNext()) {
            int numOfblock = input.nextInt();
            gameJFrame.setNumOfblock(numOfblock);
            int[][] bang = new int[numOfblock][3];
            for (int i = 0; i < numOfblock; i++) {
                int initialNumber = input.nextInt();
                bang[i][0] = initialNumber;
                String inn = input.next();
//         行数
                int blockRow = Integer.parseInt(String.valueOf(inn.charAt(0)));
                bang[i][1] = blockRow;
//         列数
                int blockCol = Integer.parseInt(String.valueOf(inn.charAt(2)));
                bang[i][2] = blockCol;
                int[] blocks = findBlocks(klo, initialNumber, blockRow, blockCol);

                Block block = new Block(blocks, initialNumber, blockRow, blockCol);

                blockList.add(block);
            }
            gameJFrame.setBang(bang);
            huaRongDaoGame.setDate(klo);
            huaRongDaoGame.setN(kloRow);
            huaRongDaoGame.setM(kloCol);
            huaRongDaoGame.setA(numOfblock);
            huaRongDaoGame.setKun(bang);
            gameJFrame.HuaRongDao(huaRongDaoGame.getDate(),huaRongDaoGame.getN(),
                    huaRongDaoGame.getM(),huaRongDaoGame.getA(), huaRongDaoGame.getKun());
        }
        else {
            gameJFrame.setNumOfblock(0);
            gameJFrame.setBang(null);
            huaRongDaoGame.setDate(klo);
            huaRongDaoGame.setN(kloRow);
            huaRongDaoGame.setM(kloCol);
            huaRongDaoGame.setA(0);
            huaRongDaoGame.setKun(null);
            gameJFrame.HuaRongDao(huaRongDaoGame.getDate(),huaRongDaoGame.getN(),
                    huaRongDaoGame.getM(),huaRongDaoGame.getA(), huaRongDaoGame.getKun());
        }
        input.close();



        if(hasSolution(klo,zeros, blockList,kloRow,kloCol)) {
            Board board = new Board(klo, blockList, zeros);

            int index = board.shift() - 1;
            System.out.println("Yes");
            ArrayList<String> steps = new ArrayList<>();
            ArrayList<int[][]> kloSteps = new ArrayList<>();


            while (index != 0) {
                steps.add(board.getMoveList().get(index).getMoveBlock() + board.getMoveList().get(index).getDirection());
                kloSteps.add( board.getMoveList().get(index).getSearched());
                index = board.getMoveList().get(index).getIndexOfBoard();
            }
            gameJFrame.setKloSteps(kloSteps);
            gameJFrame.setTime(kloSteps.size() - 1);

            System.out.println(steps.size());
            for (int i = steps.size() - 1; i >= 0; i--) {
                System.out.println(steps.get(i));
            }

        }
        else{
            System.out.println("No");
            gameJFrame.NoWin();
        }

    }

    public static int[] findBlocks(int[][] klo, int initialNumber, int blockRow, int blockCol){
        int[] blocks = new int[blockRow * blockCol];
        for (int i = 0; i < klo.length; i++) {
            for (int j = 0; j < klo[0].length; j++) {
                if( klo[i][j] == initialNumber){
                    blocks[0] = klo[i][j];
                    if(blockRow == 2 && blockCol == 2){
                        blocks[1] = klo[i+1][j];
                        blocks[2] = klo[i][j+1];
                        blocks[3] = klo[i+1][j+1];
                    }else if(blockRow == 2){
                        blocks[1] = klo[i+1][j];
                    } else{
                        blocks[1] = klo[i][j+1];
                    }
                }
            }
        }
        return blocks;
    }
    
    public static boolean hasSolution (int[][] klo, int[][] zeros, List<Block> blockList, int kloRow, int kloCol){

        if(zeros.length == kloRow*kloCol) return false;

        if(zeros.length < blockList.size())return false;

        int[][] target = findTarget(klo, zeros);
        int cSame = 0;
        tar:
        for (int i = 0; i < kloRow; i++) {
            for (int j = 0; j < kloCol; j++) {
                if(klo[i][j] != target[i][j]) break tar;
                else cSame++;
            }
        }

        if(cSame == kloCol*kloRow) {
            System.out.println("Yes");
            System.out.println("0");
            System.exit(0);
        }

        for (Block block : blockList) {
            int b1 = block.getBlock()[0];
            if (block.getBlockRow() == 2 && block.getBlockCol() == 2) {
                if (block.getBlock()[2] != b1 + 1 || block.getBlock()[1] != b1 + kloCol || block.getBlock()[3] != b1 + 1 + kloCol)
                    return false;
            } else if (block.getBlockRow() == 2) {
                if (block.getBlock()[1] != b1 + kloCol)
                    return false;
            } else {
                if (block.getBlock()[1] != b1 + 1)
                    return false;
            }

        }

        int[] ints = new int[klo.length * klo[0].length - zeros.length];
        int k = 0;
        int count = 0;
        for (int[] value : klo) {
            for (int j = 0; j < klo[0].length; j++) {
                if (value[j] != 0) {
                    ints[k] = value[j];
                    k++;
                }
            }
        }

        for (int i = 0; i < ints.length; i++) {
            int num = ints[i];
            for (int j = i+1; j < ints.length; j++) {
                if(num > ints[j])count++;
            }
        }

        return count % 2 == 0;
    }

    public static int[][] findTarget(int[][] klo, int[][] zeros){
        int count = 1;
        int[][] targetKLO = new int[klo.length][klo[0].length];
        for (int i = 0; i < klo.length; i++) {
            for (int j = 0; j < klo[0].length; j++) {
                if( klo.length * klo[0].length - count + 1!= zeros.length ){
                    targetKLO[i][j] = count;
                    count++;
                }
                else{
                    targetKLO[i][j] = 0;
                }
            }
        }
        return targetKLO;
    }




}
