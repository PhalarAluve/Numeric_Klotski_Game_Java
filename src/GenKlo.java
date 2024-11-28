import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Random;

import edu.princeton.cs.algs4.StdRandom;

public class GenKlo {
    public static void main( String[] args ) {
        for( int i = 1; i <= 20; ++ i ) {

            Random rand = new Random();
            int row = rand.nextInt(2) + 2;
            int col = rand.nextInt(2) + 2;
            int zeronum = rand.nextInt(2) + 1;
            int[][] arr = new int[row][col];
            int count=0;
            int count0 = 0;

            for (int j = 0; j <row ; j++) {
                for (int k = 0; k < col; k++) {
                    if(count < row*col - zeronum){
                        count++;
                        arr[j][k] = count;
                    }else{
                        arr[j][k] = 0;
                        count0++;
                    }
                }
            }


            int x1 = rand.nextInt(row) ;
            int y1 = rand.nextInt(col) ;
            int x2 = rand.nextInt(row) ;
            int y2 = rand.nextInt(col) ;

            for (int j = 0; j < row; j++) {
                int tem = arr[x1][y1];
                arr[x1][y1] = arr[x2][y2];
                arr[x2][y2] = tem;
            }
            StdRandom.shuffle(arr[x1]);
            StdRandom.shuffle(arr);



            while ( !hasSolution(arr,count0) ){
                x1 = rand.nextInt(row);
                y1 = rand.nextInt(col);
                x2 = rand.nextInt(row);
                y2 = rand.nextInt(col);

                int tem = arr[x1][y1];
                arr[x1][y1] = arr[x2][y2];
                arr[x2][y2] = tem;
                StdRandom.shuffle(arr[x1]);
                StdRandom.shuffle(arr);
            }


            new File("src/data/").mkdirs();

            try ( PrintWriter fout = new PrintWriter("src/data/"+i+"data.txt") ) {
                fout.println(row + " " +  col);
                for (int j = 0; j < row; j++) {
                    for (int k = 0; k < col; k++) {
                        fout.print(arr[j][k] + " ");
                    }
                    fout.println();
                }
                fout.println(0);
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
    public static boolean hasSolution (int[][] klo, int count0){

        int[] ints = new int[klo.length * klo[0].length - count0];
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






}