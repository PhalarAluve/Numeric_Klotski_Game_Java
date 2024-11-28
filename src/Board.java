import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.SeparateChainingHashST;



public class Board {
    private int[][] klotski;
    private List<Block> blockList;
    private int[][] zeros;
//     啥都有的 List
    private ArrayList< Move > moveList = new ArrayList<>();
//    已经搜索过的 状态
    private SeparateChainingHashST<Integer, Integer> searchedI = new SeparateChainingHashST<>();
//    目标状态
    private SeparateChainingHashST<Integer, Integer> target = new SeparateChainingHashST<>();

//    zeros[][]
//    zeros[] 有几个 0
//    zeros[][]  0为 x ， 1 为 y

    public Board (int[][] klotski, List<Block> blockList, int[][] zeros){
        this.klotski = klotski;
        this.blockList = blockList;
        this.zeros = zeros;
    }

    public int shift(){
//        棋盘上所有的可走棋局
//        只移动一次
//        那么就要选 0 进行移动
//        按顺序来
        int count = 0; int counter = 0;
        searchedI.put(hashcode(klotski), count);
        moveList.add(new Move(0," ", -1, zeros, klotski ));
        target.put( hashcode(findTarget(klotski,zeros) ), 0) ;
        counter++;
        boolean mup = false;
        boolean mdown= false;
        boolean mleft = false;
        boolean mright = false;

        while( true ) {
            int[][] klo = moveList.get(count).getSearched();
            int[][] zero = moveList.get(count).getZeros();
            for (int i = 0; i < zeros.length; i++) {
                int x = zero[i][0];
                int y = zero[i][1];
//      UP
                if (x != 0 && klo[x-1][y] != 0) {
                    int indexOfblock = isBlock(x-1, y, klo);
                    int up = moveUp(x, y, klo, indexOfblock);
                    if (!searchedI.contains(up)) {
                        Move move = moveUp(x, y, i, klo, zero, count, indexOfblock);
                        if (target.contains(up)) {
                            moveList.add(move);
                            return moveList.size();
                        }
                        searchedI.put(up, counter);
                        moveList.add(move);
                        counter++;
                        mup = true;
                    }
                }
//      DOWN

                if (x != klotski.length - 1 && klo[x + 1][y] != 0) {
                    int indexOfblock = isBlock(x+1, y, klo);
                    int down = moveDown(x, y, klo, indexOfblock);
                    if (!searchedI.contains(down)) {
                        Move move = moveDown(x, y, i, klo, zero, count, indexOfblock);
                        if (target.contains(down)) {
                            moveList.add(move);
                            return moveList.size();
                        }
                        searchedI.put(down, counter);
                        moveList.add(move);
                        counter++;
                        mdown = true;
                    }
                }
//      LEFT

                if (y != 0 && klo[x][y - 1] != 0 ) {
                    int indexOfblock = isBlock(x, y-1, klo);
                    int left = moveLeft(x, y, klo, indexOfblock);
                    if (!searchedI.contains(left)) {
                    Move move = moveLeft(x, y, i, klo, zero, count,indexOfblock);
                    if (target.contains(left)) {
                        moveList.add(move);
                        return moveList.size();
                    }
                    searchedI.put(left, counter);
                    moveList.add(move);
                    counter++;
                    mleft = true;
                }
            }
//      Right
                if (y != klotski[0].length - 1 && klo[x][y+1] != 0) {
                    int indexOfblock = isBlock(x, y+1, klo);
                    int right = moveRight(x, y, klo,indexOfblock);
                    if (!searchedI.contains(right)) {
                        Move move = moveRight(x, y, i, klo, zero, count, indexOfblock);
                        if (target.contains(right)) {
                            moveList.add(move);
                            return moveList.size();
                        }
                        searchedI.put(right, counter);
                        moveList.add(move);
                        counter++;
                        mright = true;
                    }
                }
            }
            count++;
        }
    }

    public Move moveUp(int x, int y, int i, int[][] klo1, int[][] zeros1, int count, int indexOfblock){
        int[][] klo = copyArray2D(klo1);
        int[][] zeros = copyArray2D(zeros1);
        int movedBlock = 0;
        if( indexOfblock >= 0){
            int initial = blockList.get(indexOfblock).getBlock()[0];
            switch (blockList.get(indexOfblock).size()){
                case 2:
                    if( blockList.get(indexOfblock).getBlockRow() == 2){
//                 2*1
//                 a    a     0
//                 b    0     a
//                *0    b    *b
                        int tem = klo[x-1][y];
                        klo[x-1][y] = 0;
                        klo[x][y] = tem;
                        x--;
                        tem = klo[x-1][y];
                        klo[x-1][y] = 0;
                        klo[x][y] = tem;
                        movedBlock = initial;
                        zeros[i][0]--;
                        zeros[i][0]--;
                    }
                    else{
//                 1*2 上面是 block 的首
//                 a b      0 b    0 0
//                *0 0     *a 0   *a b
                        if(klo[x-1][y] == initial){
                            if(klo[x][y+1] == 0){
                                int j =findIndexofZero(x,y+1,zeros);
                                zeros[i][0]--;
                                zeros[j][0]--;
                                int tem = klo[x-1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;
                                y++;
                                tem = klo[x-1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;

                                movedBlock = initial;
                            }
                        }else{
//                 1*2  上面不是 block 的首
//                 a  b    a  0    0  0
//                 0 *0    0 *b    a  b
                            if(klo[x][y-1] == 0){
                                int j =findIndexofZero(x,y-1,zeros);
                                zeros[j][0]--;
                                zeros[i][0]--;
                                int tem = klo[x-1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;
                                y--;
                                tem = klo[x-1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;

                                movedBlock = initial;
                            }
                        }
                    }
                    break;
                case 4:
//                  1 1    1 1   1 1   0 1     0 0
//                  1 1    0 1   0 0   1 0     1 1
//                 *0 0   *1 0   1 1   1 1     1 1
                    if( klo[x-1][y] == blockList.get(indexOfblock).getBlock()[1] ){
                        if(klo[x][y+1] == 0) {
                            int j =findIndexofZero(x,y+1,zeros);
                            zeros[j][0]--;
                            zeros[j][0]--;
                            zeros[i][0]--;
                            zeros[i][0]--;
                            int tem = klo[x - 1][y];
                            klo[x-1][y] = 0;
                            klo[x][y] = tem;
                            y++;
                            tem = klo[x - 1][y];
                            klo[x-1][y] = 0;
                            klo[x][y] = tem;
                            y--;x--;
                            tem = klo[x - 1][y];
                            klo[x-1][y] = 0;
                            klo[x][y] = tem;
                            y++;
                            tem = klo[x - 1][y];
                            klo[x-1][y] = 0;
                            klo[x][y] = tem;

                            movedBlock = initial;
                        }
                    }
                    else{
//                  1  2    1  2   1 1   0 1     0 0
//                  3  4    3  0   0 0   1 0     1 1
//                  0 *0    0  4   1 1   1 1     1 1
                        if( klo[x-1][y] == blockList.get(indexOfblock).getBlock()[3] ){
                            if(klo[x][y-1] == 0){
                                int j =findIndexofZero(x,y-1,zeros);
                                zeros[j][0]--;
                                zeros[j][0]--;
                                zeros[i][0]--;
                                zeros[i][0]--;
                                int tem = klo[x-1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;
                                y--;
                                tem = klo[x - 1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;
                                y++;x--;
                                tem = klo[x-1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;
                                y--;
                                tem = klo[x - 1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;

                                movedBlock = initial;
                            }
                        }
                    }
                    break;
            }
        }
        else{
            zeros[i][0]--;
            int tem = klo[x - 1][y];
            klo[x - 1][y] = 0;
            klo[x][y] = tem;
            movedBlock = tem;
        }
        return new Move(movedBlock, " D", count, zeros, klo);
    }

    public Move moveDown(int x, int y, int i, int[][] klo1, int[][] zeros1, int count, int indexOfblock){
        int[][] klo = copyArray2D(klo1);
        int[][] zeros = copyArray2D(zeros1);
        int movedBlock = 0;

        if( indexOfblock >= 0){
            int initial = blockList.get(indexOfblock).getBlock()[0];
            switch (blockList.get(indexOfblock).size()){
                case 2:
                    if( blockList.get(indexOfblock).getBlockRow() == 2){

//                 2*1
//                 *0    a    a
//                  a    0    b
//                  b    b    0
                        int tem = klo[x+1][y];
                        klo[x+1][y] = 0;
                        klo[x][y] = tem;
                        x++;
                        tem = klo[x+1][y];
                        klo[x+1][y] = 0;
                        klo[x][y] = tem;

                        movedBlock = initial;
                        zeros[i][0]++;
                        zeros[i][0]++;
                    }
                    else{
//                 1*2  下面是 block 的首
//                *0 0     *a 0    a b
//                 a b      0 b    0 0
                        if(klo[x+1][y] == initial){
                            if(klo[x][y+1] == 0){
                                int j =findIndexofZero(x,y+1,zeros);
                                zeros[j][0]++;
                                zeros[i][0]++;
                                int tem = klo[x+1][y];
                                klo[x+1][y] = 0;
                                klo[x][y] = tem;
                                y++;
                                tem = klo[x+1][y];
                                klo[x+1][y] = 0;
                                klo[x][y] = tem;
                                movedBlock = initial;
                            }
                        }else{
//                 1*2  下面不是 block 的首
//                 0 *0    0 *b    a  b
//                 a  b    a  0    0  0
                            if(klo[x][y-1] == 0){
                                int j =findIndexofZero(x,y-1,zeros);
                                zeros[j][0]++;
                                zeros[i][0]++;
                                int tem = klo[x+1][y];
                                klo[x+1][y] = 0;
                                klo[x][y] = tem;
                                y--;
                                tem = klo[x+1][y];
                                klo[x+1][y] = 0;
                                klo[x][y] = tem;
                                movedBlock = initial;
                            }
                        }
                    }
                    break;
                case 4:
//                 *0 0   *1 0   1 1   1 1     1 1
//                  1 1    0 1   0 0   1 0     1 1
//                  1 1    1 1   1 1   0 1     0 0
                    if( klo[x+1][y] == initial ){
                        if(klo[x][y+1] == 0) {
                            int j =findIndexofZero(x,y+1,zeros);
                            zeros[j][0]++;
                            zeros[j][0]++;
                            zeros[i][0]++;
                            zeros[i][0]++;
                            int tem = klo[x + 1][y];
                            klo[x+1][y] = 0;
                            klo[x][y] = tem;
                            y++;
                            tem = klo[x + 1][y];
                            klo[x+1][y] = 0;
                            klo[x][y] = tem;
                            x++;y--;
                            tem = klo[x + 1][y];
                            klo[x+1][y] = 0;
                            klo[x][y] = tem;
                            y++;
                            tem = klo[x + 1][y];
                            klo[x+1][y] = 0;
                            klo[x][y] = tem;

                            movedBlock = initial;
                        }
                    }
                    else {
//                  0 *0    0  4   1 1   1 1     1 1
//                  3  4    3  0   0 0   1 0     1 1
//                  1  2    1  2   1 1   0 1     0 0
                        if (klo[x + 1][y] == blockList.get(indexOfblock).getBlock()[2]) {
                            if (klo[x][y - 1] == 0) {
                                int j =findIndexofZero(x,y-1,zeros);
                                zeros[j][0]++;
                                zeros[j][0]++;
                                zeros[i][0]++;
                                zeros[i][0]++;
                                int tem = klo[x + 1][y];
                                klo[x + 1][y] = 0;
                                klo[x][y] = tem;
                                y--;
                                tem = klo[x + 1][y];
                                klo[x + 1][y] = 0;
                                klo[x][y] = tem;
                                y++;
                                x++;
                                tem = klo[x + 1][y];
                                klo[x + 1][y] = 0;
                                klo[x][y] = tem;
                                y--;
                                tem = klo[x + 1][y];
                                klo[x + 1][y] = 0;
                                klo[x][y] = tem;

                                movedBlock = initial;
                            }
                        }
                    }
                    break;
            }
        }
        else{
            zeros[i][0]++;
            int tem = klo[x + 1][y];
            klo[x + 1][y] = 0;
            klo[x][y] = tem;
            movedBlock = tem;
        }
        return new Move(movedBlock, " U", count, zeros, klo);
    }

    public Move moveLeft(int x, int y, int i, int[][] klo1, int[][] zeros1, int count, int indexOfblock){
        int[][] klo = copyArray2D(klo1);
        int[][] zeros = copyArray2D(zeros1);
        int movedBlock = 0;

        if( indexOfblock >= 0){
            int initial = blockList.get(indexOfblock).getBlock()[0];
            switch (blockList.get(indexOfblock).size()){
                case 2:
                    if( blockList.get(indexOfblock).getBlockRow() == 1){

//                 1*2
//                      a b*0    a 0 b    0 a b
                        int tem = klo[x][y-1];
                        klo[x][y-1] = 0;
                        klo[x][y] = tem;
                        y--;
                        tem = klo[x][y-1];
                        klo[x][y-1] = 0;
                        klo[x][y] = tem;

                        movedBlock = initial;
                        zeros[i][1]--;
                        zeros[i][1]--;
                    }
                    else{
//                 2*1  左面是 block 的首
//                   a*0      0 a       0 a
//                   b 0      b 0       0 b
                        if(klo[x][y-1] == initial){
                            if(klo[x+1][y] == 0){
                                int j =findIndexofZero(x+1,y,zeros);
                                zeros[j][1]--;
                                zeros[i][1]--;
                                int tem = klo[x][y-1];
                                klo[x][y-1] = 0;
                                klo[x][y] = tem;
                                x++;
                                tem = klo[x][y-1];
                                klo[x][y-1] = 0;
                                klo[x][y] = tem;
                                movedBlock = initial;
                            }
                        }else{
//                 2*1  左面是不 block 的首
//                   a  0      a 0       0 a
//                   b *0      0 b       0 b
                            if(klo[x-1][y] == 0){
                                int j =findIndexofZero(x-1,y,zeros);
                                zeros[j][1]--;
                                zeros[i][1]--;
                                int tem = klo[x][y-1];
                                klo[x][y-1] = 0;
                                klo[x][y] = tem;
                                x--;
                                tem = klo[x][y-1];
                                klo[x][y-1] = 0;
                                klo[x][y] = tem;
                                movedBlock = initial;
                            }
                        }
                    }
                    break;
                case 4:
//                 2*2  左面是 block 的 [2] 位
//                  c a *0      a 0      c 0 a
//                  d b  0      0 b      d 0 b
                    if( klo[x][y-1] == blockList.get(indexOfblock).getBlock()[2] ){
                        if(klo[x+1][y] == 0) {
                            int j =findIndexofZero(x+1,y,zeros);
                            zeros[j][1]--;
                            zeros[j][1]--;
                            zeros[i][1]--;
                            zeros[i][1]--;
                            int tem = klo[x][y-1];
                            klo[x][y-1] = 0;
                            klo[x][y] = tem;
                            x++;
                            tem = klo[x][y-1];
                            klo[x][y-1] = 0;
                            klo[x][y] = tem;
                            x--;y--;

                            tem = klo[x][y-1];
                            klo[x][y-1] = 0;
                            klo[x][y] = tem;
                            x++;
                            tem = klo[x][y-1];
                            klo[x][y-1] = 0;
                            klo[x][y] = tem;

                            movedBlock = initial;
                        }
                    }
                    else{
//                 2*2  左面是 block 的 [3] 位
//                  c a  0       c 0 a
//                  d b *0       d 0 b
                        if(klo[x][y-1] == blockList.get(indexOfblock).getBlock()[3]){
                            if(klo[x-1][y] == 0) {
                                int j =findIndexofZero(x-1,y,zeros);
                                zeros[j][1]--;
                                zeros[j][1]--;
                                zeros[i][1]--;
                                zeros[i][1]--;
                                int tem = klo[x][y - 1];
                                klo[x][y - 1] = 0;
                                klo[x][y] = tem;
                                x--;
                                tem = klo[x][y - 1];
                                klo[x][y - 1] = 0;
                                klo[x][y] = tem;
                                y--;
                                x++;
                                tem = klo[x][y - 1];
                                klo[x][y - 1] = 0;
                                klo[x][y] = tem;
                                x--;
                                tem = klo[x][y - 1];
                                klo[x][y - 1] = 0;
                                klo[x][y] = tem;

                                movedBlock = initial;
                            }
                        }
                    }
                    break;
            }
        }
        else{
//            a 0
            zeros[i][1]--;
            int tem = klo[x][y-1];
            klo[x][y-1] = 0;
            klo[x][y] = tem;
            movedBlock = tem;
        }
        return new Move(movedBlock, " R", count, zeros, klo);
    }

    public Move moveRight(int x, int y, int i, int[][] klo1, int[][] zeros1, int count, int indexOfblock){
        int[][] klo = copyArray2D(klo1);
        int[][] zeros = copyArray2D(zeros1);
        int movedBlock = 0;

        if( indexOfblock >= 0){
            int initial = blockList.get(indexOfblock).getBlock()[0];
            switch (blockList.get(indexOfblock).size()){
                case 2:
                    if( blockList.get(indexOfblock).getBlockRow() == 1){
//                 1*2
//                    *0 a b    a 0 b    a b 0
                        int tem = klo[x][y+1];
                        klo[x][y+1] = 0;
                        klo[x][y] = tem;
                        y++;
                        tem = klo[x][y+1];
                        klo[x][y+1] = 0;
                        klo[x][y] = tem;

                        movedBlock = initial;
                        zeros[i][1]++;
                        zeros[i][1]++;
                    }
                    else{
//                 2*1  右面是 block 的首
//                   *0  a     a 0       a 0
//                    0  b     0 b       b 0
                        if(klo[x][y+1] == initial){
                            if(klo[x+1][y] == 0){
                                int j =findIndexofZero(x+1,y,zeros);
                                zeros[j][1]++;
                                zeros[i][1]++;
                                int tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                                x++;
                                tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                                movedBlock = initial;
                            }
                        }else{
//                 2*1 右面不 block 的首
//                    0  a     a 0       a 0
//                   *0  b     0 b       b 0
                            if(klo[x-1][y] == 0){
                                int j =findIndexofZero(x-1,y,zeros);
                                zeros[j][1]++;
                                zeros[i][1]++;
                                int tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                                x--;
                                tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                                movedBlock = initial;
                            }
                        }
                    }
                    break;
                case 4:
//                 2*2  右面是 block 的 首 位
//                   *0 c a           c 0 a
//                    0 d b           d 0 b
                    if( klo[x][y+1] == initial ){
                        if(klo[x+1][y] == 0) {
                            int j =findIndexofZero(x+1,y,zeros);
                            zeros[j][1]++;
                            zeros[j][1]++;

                            zeros[i][1]++;
                            zeros[i][1]++;
                            int tem = klo[x][y+1];
                            klo[x][y+1] = 0;
                            klo[x][y] = tem;
                            x++;
                            tem = klo[x][y+1];
                            klo[x][y+1] = 0;
                            klo[x][y] = tem;
                            x--;y++;
                            tem = klo[x][y+1];
                            klo[x][y+1] = 0;
                            klo[x][y] = tem;
                            x++;
                            tem = klo[x][y+1];
                            klo[x][y+1] = 0;
                            klo[x][y] = tem;

                            movedBlock = initial;
                        }
                    }
                    else{
//                 2*2  右面是 block 的 [1] 位
//                     0 c a           c 0 a
//                    *0 d b           d 0 b
                        if(klo[x][y+1] == blockList.get(indexOfblock).getBlock()[1]){
                            if(klo[x-1][y] == 0){
                                int j =findIndexofZero(x-1,y,zeros);
                                zeros[j][1]++;
                                zeros[j][1]++;

                                zeros[i][1]++;
                                zeros[i][1]++;
                                int tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                                x--;
                                tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                                y++;x++;
                                tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                                x--;
                                tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;

                                movedBlock = initial;
                            }
                        }
                    }
                    break;
            }
        }
        else{
//             0 a
            zeros[i][1]++;
            int tem = klo[x][y+1];
            klo[x][y+1] = 0;
            klo[x][y] = tem;
            movedBlock = tem;
        }
        return new Move(movedBlock, " L", count, zeros, klo);
    }

    public int moveUp(int x, int y, int[][] klo1, int indexOfblock){
        int[][] klo = copyArray2D(klo1);
        if( indexOfblock >= 0){
            int initial = blockList.get(indexOfblock).getBlock()[0];
            switch (blockList.get(indexOfblock).size()){
                case 2:
                    if( blockList.get(indexOfblock).getBlockRow() == 2){
//                 2*1
//                 a    a     0
//                 b    0     a
//                *0    b    *b
                        int tem = klo[x-1][y];
                        klo[x-1][y] = 0;
                        klo[x][y] = tem;
                        x--;
                        tem = klo[x-1][y];
                        klo[x-1][y] = 0;
                        klo[x][y] = tem;
                    }
                    else{
//                 1*2 上面是 block 的首
//                 a b      0 b    0 0
//                *0 0     *a 0   *a b
                        if(klo[x-1][y] == initial){
                            if(klo[x][y+1] == 0){
                                int tem = klo[x-1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;
                                y++;
                                tem = klo[x-1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;
                            }
                        }else{
//                 1*2  上面不是 block 的首
//                 a  b    a  0    0  0
//                 0 *0    0 *b    a  b
                            if(klo[x][y-1] == 0){
                                int tem = klo[x-1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;
                                y--;
                                tem = klo[x-1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;
                            }
                        }
                    }
                    break;
                case 4:
//                  1 1    1 1   1 1   0 1     0 0
//                  1 1    0 1   0 0   1 0     1 1
//                 *0 0   *1 0   1 1   1 1     1 1
                    if( klo[x-1][y] == blockList.get(indexOfblock).getBlock()[1] ){
                        if(klo[x][y+1] == 0) {
                            int tem = klo[x - 1][y];
                            klo[x-1][y] = 0;
                            klo[x][y] = tem;
                            y++;
                            tem = klo[x - 1][y];
                            klo[x-1][y] = 0;
                            klo[x][y] = tem;
                            y--;x--;
                            tem = klo[x - 1][y];
                            klo[x-1][y] = 0;
                            klo[x][y] = tem;
                            y++;
                            tem = klo[x - 1][y];
                            klo[x-1][y] = 0;
                            klo[x][y] = tem;
                        }
                    }
                    else{
//                  1  2    1  2   1 1   0 1     0 0
//                  3  4    3  0   0 0   1 0     1 1
//                  0 *0    0  4   1 1   1 1     1 1
                        if( klo[x-1][y] == blockList.get(indexOfblock).getBlock()[3] ){
                            if(klo[x][y-1] == 0){
                                int tem = klo[x-1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;
                                y--;
                                tem = klo[x - 1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;
                                y++;x--;
                                tem = klo[x-1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;
                                y--;
                                tem = klo[x - 1][y];
                                klo[x-1][y] = 0;
                                klo[x][y] = tem;
                            }
                        }
                    }
                    break;
            }
        }
        else{
            int tem = klo[x - 1][y];
            klo[x - 1][y] = 0;
            klo[x][y] = tem;
        }
        return hashcode(klo);
    }

    public int moveDown(int x, int y, int[][] klo1, int indexOfblock){
        int[][] klo = copyArray2D(klo1);
        if( indexOfblock >= 0){
            int initial = blockList.get(indexOfblock).getBlock()[0];
            switch (blockList.get(indexOfblock).size()){
                case 2:
                    if( blockList.get(indexOfblock).getBlockRow() == 2){

//                 2*1
//                 *0    a    a
//                  a    0    b
//                  b    b    0
                        int tem = klo[x+1][y];
                        klo[x+1][y] = 0;
                        klo[x][y] = tem;
                        x++;
                        tem = klo[x+1][y];
                        klo[x+1][y] = 0;
                        klo[x][y] = tem;
                    }
                    else{
//                 1*2  下面是 block 的首
//                *0 0     *a 0    a b
//                 a b      0 b    0 0
                        if(klo[x+1][y] == initial){
                            if(klo[x][y+1] == 0){
                                int tem = klo[x+1][y];
                                klo[x+1][y] = 0;
                                klo[x][y] = tem;
                                y++;
                                tem = klo[x+1][y];
                                klo[x+1][y] = 0;
                                klo[x][y] = tem;
                            }
                        }else{
//                 1*2  下面不是 block 的首
//                 0 *0    0 *b    a  b
//                 a  b    a  0    0  0
                            if(klo[x][y-1] == 0){
                                int tem = klo[x+1][y];
                                klo[x+1][y] = 0;
                                klo[x][y] = tem;
                                y--;
                                tem = klo[x+1][y];
                                klo[x+1][y] = 0;
                                klo[x][y] = tem;
                            }
                        }
                    }
                    break;
                case 4:
//                 *0 0   *1 0   1 1   1 1     1 1
//                  1 1    0 1   0 0   1 0     1 1
//                  1 1    1 1   1 1   0 1     0 0
                    if( klo[x+1][y] == initial ){
                        if(klo[x][y+1] == 0) {
                            int tem = klo[x + 1][y];
                            klo[x+1][y] = 0;
                            klo[x][y] = tem;
                            y++;
                            tem = klo[x + 1][y];
                            klo[x+1][y] = 0;
                            klo[x][y] = tem;
                            x++;y--;
                            tem = klo[x + 1][y];
                            klo[x+1][y] = 0;
                            klo[x][y] = tem;
                            y++;
                            tem = klo[x + 1][y];
                            klo[x+1][y] = 0;
                            klo[x][y] = tem;
                        }
                    }
                    else {
//                  0 *0    0  4   1 1   1 1     1 1
//                  3  4    3  0   0 0   1 0     1 1
//                  1  2    1  2   1 1   0 1     0 0
                        if (klo[x + 1][y] == blockList.get(indexOfblock).getBlock()[2]) {
                            if (klo[x][y - 1] == 0) {
                                int tem = klo[x + 1][y];
                                klo[x + 1][y] = 0;
                                klo[x][y] = tem;
                                y--;
                                tem = klo[x + 1][y];
                                klo[x + 1][y] = 0;
                                klo[x][y] = tem;
                                y++;
                                x++;
                                tem = klo[x + 1][y];
                                klo[x + 1][y] = 0;
                                klo[x][y] = tem;
                                y--;
                                tem = klo[x + 1][y];
                                klo[x + 1][y] = 0;
                                klo[x][y] = tem;
                            }
                        }
                    }
                    break;
            }
        }
        else{
            int tem = klo[x + 1][y];
            klo[x + 1][y] = 0;
            klo[x][y] = tem;
        }
        return hashcode(klo);
    }

    public int moveLeft(int x, int y, int[][] klo1, int indexOfblock){
        int[][] klo = copyArray2D(klo1);
        if( indexOfblock >= 0){
            int initial = blockList.get(indexOfblock).getBlock()[0];
            switch (blockList.get(indexOfblock).size()){
                case 2:
                    if( blockList.get(indexOfblock).getBlockRow() == 1){
//                 1*2
//                      a b*0    a 0 b    0 a b
                        int tem = klo[x][y-1];
                        klo[x][y-1] = 0;
                        klo[x][y] = tem;
                        y--;
                        tem = klo[x][y-1];
                        klo[x][y-1] = 0;
                        klo[x][y] = tem;
                    }
                    else{
//                 2*1  左面是 block 的首
//                   a*0      0 a       0 a
//                   b 0      b 0       0 b
                        if(klo[x][y-1] == initial){
                            if(klo[x+1][y] == 0){
                                int tem = klo[x][y-1];
                                klo[x][y-1] = 0;
                                klo[x][y] = tem;
                                x++;
                                tem = klo[x][y-1];
                                klo[x][y-1] = 0;
                                klo[x][y] = tem;
                            }
                        }else{
//                 2*1  左面是不 block 的首
//                   a  0      a 0       0 a
//                   b *0      0 b       0 b
                            if(klo[x-1][y] == 0){
                                int tem = klo[x][y-1];
                                klo[x][y-1] = 0;
                                klo[x][y] = tem;
                                x--;
                                tem = klo[x][y-1];
                                klo[x][y-1] = 0;
                                klo[x][y] = tem;
                            }
                        }
                    }
                    break;
                case 4:
//                 2*2  左面是 block 的 [2] 位
//                  c a *0      a 0      c 0 a
//                  d b  0      0 b      d 0 b
                    if( klo[x][y-1] == blockList.get(indexOfblock).getBlock()[2] ){
                        if(klo[x+1][y] == 0) {
                            int j =findIndexofZero(x+1,y,zeros);
                            int tem = klo[x][y-1];
                            klo[x][y-1] = 0;
                            klo[x][y] = tem;
                            x++;
                            tem = klo[x][y-1];
                            klo[x][y-1] = 0;
                            klo[x][y] = tem;
                            x--;y--;

                            tem = klo[x][y-1];
                            klo[x][y-1] = 0;
                            klo[x][y] = tem;
                            x++;
                            tem = klo[x][y-1];
                            klo[x][y-1] = 0;
                            klo[x][y] = tem;
                        }
                    }
                    else{
//                 2*2  左面是 block 的 [3] 位
//                  c a  0       c 0 a
//                  d b *0       d 0 b
                        if(klo[x][y-1] == blockList.get(indexOfblock).getBlock()[3]){
                            if(klo[x-1][y] == 0) {
                                int tem = klo[x][y - 1];
                                klo[x][y - 1] = 0;
                                klo[x][y] = tem;
                                x--;
                                tem = klo[x][y - 1];
                                klo[x][y - 1] = 0;
                                klo[x][y] = tem;
                                y--;
                                x++;
                                tem = klo[x][y - 1];
                                klo[x][y - 1] = 0;
                                klo[x][y] = tem;
                                x--;
                                tem = klo[x][y - 1];
                                klo[x][y - 1] = 0;
                                klo[x][y] = tem;
                            }
                        }
                    }
                    break;
            }
        }
        else{
//            a 0
            int tem = klo[x][y-1];
            klo[x][y-1] = 0;
            klo[x][y] = tem;
        }
        return hashcode(klo);
    }

    public int moveRight(int x, int y, int[][] klo1, int indexOfblock){
        int[][] klo = copyArray2D(klo1);
        if( indexOfblock >= 0){
            int initial = blockList.get(indexOfblock).getBlock()[0];
            switch (blockList.get(indexOfblock).size()){
                case 2:
                    if( blockList.get(indexOfblock).getBlockRow() == 1){
//                 1*2
//                    *0 a b    a 0 b    a b 0
                        int tem = klo[x][y+1];
                        klo[x][y+1] = 0;
                        klo[x][y] = tem;
                        y++;
                        tem = klo[x][y+1];
                        klo[x][y+1] = 0;
                        klo[x][y] = tem;
                    }
                    else{
//                 2*1  右面是 block 的首
//                   *0  a     a 0       a 0
//                    0  b     0 b       b 0
                        if(klo[x][y+1] == initial){
                            if(klo[x+1][y] == 0){
                                int tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                                x++;
                                tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                            }
                        }else{
//                 2*1 右面不 block 的首
//                    0  a     a 0       a 0
//                   *0  b     0 b       b 0
                            if(klo[x-1][y] == 0){
                                int tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                                x--;
                                tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                            }
                        }
                    }
                    break;
                case 4:
//                 2*2  右面是 block 的 首 位
//                   *0 c a           c 0 a
//                    0 d b           d 0 b
                    if( klo[x][y+1] == initial ){
                        if(klo[x+1][y] == 0) {
                            int tem = klo[x][y+1];
                            klo[x][y+1] = 0;
                            klo[x][y] = tem;
                            x++;
                            tem = klo[x][y+1];
                            klo[x][y+1] = 0;
                            klo[x][y] = tem;
                            x--;y++;
                            tem = klo[x][y+1];
                            klo[x][y+1] = 0;
                            klo[x][y] = tem;
                            x++;
                            tem = klo[x][y+1];
                            klo[x][y+1] = 0;
                            klo[x][y] = tem;
                        }
                    }
                    else{
//                 2*2  右面是 block 的 [1] 位
//                     0 c a           c 0 a
//                    *0 d b           d 0 b
                        if(klo[x][y+1] == blockList.get(indexOfblock).getBlock()[1]){
                            if(klo[x-1][y] == 0){
                                int tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                                x--;
                                tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                                y++;x++;
                                tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                                x--;
                                tem = klo[x][y+1];
                                klo[x][y+1] = 0;
                                klo[x][y] = tem;
                            }
                        }
                    }
                    break;
            }
        }
        else{
//             0 a
            int tem = klo[x][y+1];
            klo[x][y+1] = 0;
            klo[x][y] = tem;
        }
        return hashcode(klo);
    }

//  这是 blockList 的 索引 、 -1 则为不是 block

    public int isBlock(int x, int y, int[][] klo){
        for (int i = 0; i < blockList.size(); i++) {
            for (int j = 0; j < blockList.get(i).size(); j++) {
                if(klo[x][y] == blockList.get(i).getBlock()[j]){
                    return i;
                }
            }
        }
        return -100;
    }

    public int[][] findTarget(int[][] klo, int[][] zeros){
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

    public int[][] copyArray2D(int[][] in){
        int[][] out = new int[in.length][in[0].length];
        for (int i = 0; i < in.length; i++) {
            out[i] = in[i].clone();
        }
        return out;
    }

    public int hashcode(int[][] in){
        int h=0;
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                h ^= in[i][j] ^ (h << 8) + 2*h;
            }
        }
        return h;
    }

    public int findIndexofZero(int x, int y, int[][] zeros){
        for (int i = 0; i < zeros.length; i++) {
            if(zeros[i][0] == x && zeros[i][1] == y)return i;
        }
        return -1;
    }

    public ArrayList<Move> getMoveList() {
        return moveList;
    }
}
