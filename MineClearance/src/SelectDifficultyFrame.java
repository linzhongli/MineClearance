import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class SelectDifficultyFrame extends JFrame {

    private int easyRowNum = 10,easyColumnsNum = 10,easyMineNums = 10;
    private int middleRowNum = 20,middleColumnsNum = 20,middleMineNums = 40;
    private int hardRowNum = 30,hardColumnsNum = 30,hardMineNums = 100;

    SelectDifficultyFrame(){
        JButton jButtonEasy,jButtonMiddle,jButtonHard;
        String title = "选择难度";

        this.setSize(300,300);//设置长宽
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);        //是否可由用户更改窗口大小
        this.setTitle(title);       //设置标题
        this.setLayout(new GridLayout()); //设置为边界布局

        JPanel jPanel = new JPanel(new GridLayout(3,1));

        jButtonEasy = new JButton("简单");
        jButtonMiddle = new JButton("中等");
        jButtonHard = new JButton("困难");
        jButtonEasy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new MainFrame(easyRowNum,easyColumnsNum,easyMineNums);
            }
        });
        jButtonMiddle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new MainFrame(middleRowNum,middleColumnsNum,middleMineNums);
            }
        });
        jButtonHard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new MainFrame(hardRowNum,hardColumnsNum,hardMineNums);
            }
        });

        jPanel.add(jButtonEasy);
        jPanel.add(jButtonMiddle);
        jPanel.add(jButtonHard);

        this.add(jPanel);
        this.setVisible(true);      //设置可见
    }

    public static void main(String[] args) {
        new SelectDifficultyFrame();
    }
}
