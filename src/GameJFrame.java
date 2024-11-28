import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameJFrame extends JFrame {
    public ArrayList<int[][]> kloSteps = new ArrayList<>();
    public int kloRow;
    public int kloCol;
    public int numOfblock;
    public int[][] bang;
    private int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getKloRow() {
        return kloRow;
    }

    public void setKloRow(int kloRow) {
        this.kloRow = kloRow;
    }

    public int getKloCol() {
        return kloCol;
    }

    public void setKloCol(int kloCol) {
        this.kloCol = kloCol;
    }

    public int getNumOfblock() {
        return numOfblock;
    }

    public void setNumOfblock(int numOfblock) {
        this.numOfblock = numOfblock;
    }

    public ArrayList<int[][]> getKloSteps() {
        return kloSteps;
    }

    public int[][] getBang() {
        return bang;
    }

    public void setBang(int[][] bang) {
        this.bang = bang;
    }

    public void setKloSteps(ArrayList<int[][]> kloSteps) {
        this.kloSteps = kloSteps;
    }

    public void HuaRongDao(int[][]datas, int N, int M, int A, int[][]kun) {
        initFrame();//窗体初始化设置
        paintView(datas,N,M,A,kun);
        this.setVisible(true);
    }

    public void NoWin(){
        JOptionPane.showMessageDialog(this, "无解！！！");
    }

    public void initFrame() {
        this.setTitle("华容道");
        this.setSize(800, 800);
        this.setBackground(new Color(189, 212, 200));
        this.setDefaultCloseOperation(3);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        addNextButton();
    }
    public void addNextButton() {
        JButton button = new JButton("Next");
        button.addActionListener((e) -> {
            if (time >= 0) {
                int[][] tem = kloSteps.get(time);
                HuaRongDao(tem, getKloRow(), getKloCol(), getNumOfblock(), getBang());
                time--;
            }
            else {
                JOptionPane.showMessageDialog(this, "成功！！！");
            }
        });
        button.setBounds(360,30,80,40);
        button.setBackground(new Color(110,164,189));
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        this.add(button);
    }

    public void paintView(int[][]datas,int N,int M,int A,int[][]kun) {
        JLayeredPane jLayeredPane = new JLayeredPane();
        jLayeredPane.setBounds(100, 100, 600, 600);
        jLayeredPane.setLayout(null);

        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[i].length; j++) {
                JPanel piece = new JPanel();
                piece.setBounds(600 / M * j, 600 / N * i, 600 / M, 600 / N);
                piece.setOpaque(true);
                if (datas[i][j] == 0) {
                    piece.setBackground(Color.WHITE);
                    JLabel num = new JLabel("");
                    num.setOpaque(true);
                    num.setBackground(Color.WHITE);
                    num.setBounds(0, 0, 600 / M, 600 / N);
                    piece.add(num);
                } else {
                    if (A == 0) {
                        piece.setBackground(new Color(209, 153, 160));
                        piece.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                        JLabel num = new JLabel(String.valueOf(datas[i][j]));
                        num.setOpaque(true);
                        num.setBackground(new Color(209, 153, 160));
                        num.setBounds(0, 0, 600 / M, 600 / N);
                        piece.add(num);
                    }
                    if (A==1){
                        if (datas[i][j] == kun[0][0]||datas[i][j] == kun[0][0]+(kun[0][1]-1)*M||datas[i][j] == kun[0][0]+(kun[0][2]-1)||
                                datas[i][j] == kun[0][0]+(kun[0][1]-1)*M+kun[0][2]-1) {
                            piece.setBackground(new Color(110, 164, 189));
                            piece.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                            JLabel num = new JLabel(String.valueOf(datas[i][j]));
                            num.setOpaque(true);
                            num.setBackground(new Color(110, 164, 189));
                            num.setBounds(0, 0, 600 / M, 600 / N);
                            piece.add(num);
                        } else {
                            piece.setBackground(new Color(209, 153, 160));
                            piece.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                            JLabel num = new JLabel(String.valueOf(datas[i][j]));
                            num.setOpaque(true);
                            num.setBackground(new Color(209, 153, 160));
                            num.setBounds(0, 0, 600 / M, 600 / N);
                            piece.add(num);
                        }
                    }
                    if (A==2){
                        if (datas[i][j] == kun[0][0]||datas[i][j] == kun[0][0]+(kun[0][1]-1)*M||datas[i][j] == kun[0][0]+(kun[0][2]-1)||
                                datas[i][j] == kun[0][0]+(kun[0][1]-1)*M+kun[0][2]-1) {
                            piece.setBackground(new Color(110, 164, 189));
                            piece.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                            JLabel num = new JLabel(String.valueOf(datas[i][j]));
                            num.setOpaque(true);
                            num.setBackground(new Color(110, 164, 189));
                            num.setBounds(0, 0, 600 / M, 600 / N);
                            piece.add(num);
                        }
                        else if (datas[i][j] == kun[1][0]||datas[i][j] == kun[1][0]+(kun[1][1]-1)*M||datas[i][j] == kun[1][0]+(kun[1][2]-1)||
                                datas[i][j] == kun[1][0]+(kun[1][1]-1)*M+kun[1][2]-1) {
                            piece.setBackground(new Color(110, 164, 189));
                            piece.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                            JLabel num = new JLabel(String.valueOf(datas[i][j]));
                            num.setOpaque(true);
                            num.setBackground(new Color(110, 164, 189));
                            num.setBounds(0, 0, 600 / M, 600 / N);
                            piece.add(num);}
                        else {
                            piece.setBackground(new Color(209, 153, 160));
                            piece.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                            JLabel num = new JLabel(String.valueOf(datas[i][j]));
                            num.setOpaque(true);
                            num.setBackground(new Color(209, 153, 160));
                            num.setBounds(0, 0, 600 / M, 600 / N);
                            piece.add(num);
                        }
                    }
                    if (A==3){
                        if (datas[i][j] == kun[0][0]||datas[i][j] == kun[0][0]+(kun[0][1]-1)*M||datas[i][j] == kun[0][0]+(kun[0][2]-1)||
                                datas[i][j] == kun[0][0]+(kun[0][1]-1)*M+kun[0][2]-1) {
                            piece.setBackground(new Color(110, 164, 189));
                            piece.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                            JLabel num = new JLabel(String.valueOf(datas[i][j]));
                            num.setOpaque(true);
                            num.setBackground(new Color(110, 164, 189));
                            num.setBounds(0, 0, 600 / M, 600 / N);
                            piece.add(num);
                        }
                        else if (datas[i][j] == kun[1][0]||datas[i][j] == kun[1][0]+(kun[1][1]-1)*M||datas[i][j] == kun[1][0]+(kun[1][2]-1)||
                                datas[i][j] == kun[1][0]+(kun[1][1]-1)*M+kun[1][2]-1) {
                            piece.setBackground(new Color(110, 164, 189));
                            piece.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                            JLabel num = new JLabel(String.valueOf(datas[i][j]));
                            num.setOpaque(true);
                            num.setBackground(new Color(110, 164, 189));
                            num.setBounds(0, 0, 600 / M, 600 / N);
                            piece.add(num);}
                        else if (datas[i][j] == kun[2][0]||datas[i][j] == kun[2][0]+(kun[2][1]-1)*M||datas[i][j] == kun[2][0]+(kun[2][2]-1)||
                                datas[i][j] == kun[2][0]+(kun[2][1]-1)*M+kun[2][2]-1) {
                            piece.setBackground(new Color(110, 164, 189));
                            piece.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                            JLabel num = new JLabel(String.valueOf(datas[i][j]));
                            num.setOpaque(true);
                            num.setBackground(new Color(110, 164, 189));
                            num.setBounds(0, 0, 600 / M, 600 / N);
                            piece.add(num);}
                        else {
                            piece.setBackground(new Color(209, 153, 160));
                            piece.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                            JLabel num = new JLabel(String.valueOf(datas[i][j]));
                            num.setOpaque(true);
                            num.setBackground(new Color(209, 153, 160));
                            num.setBounds(0, 0, 600 / M, 600 / N);
                            piece.add(num);
                        }
                    }
                }
            jLayeredPane.add(piece, new Integer(100));
        }
    }
    this.add(jLayeredPane);
}

    public void upset(int[][]arr) {
        Random rd = new Random();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                int x = rd.nextInt(arr.length);
                int y = rd.nextInt(arr[x].length);
                int temp = arr[i][j];
                arr[i][j] = arr[x][y];
                arr[x][y] = temp;
            }
        }
    }//产生随机棋盘，但还需要判断是否能成功才能完成bones！
}
