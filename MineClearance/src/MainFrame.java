import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public int rowsNum;          //行个数
    public int columnsNum;       //列个数
    public int mineNums;         //雷的个数
    public int Height = 30;      //单格高度
    public int Weight = 30;      //单格宽度
    public String title = "扫雷";

    public JPanel certerJP;      //中间的部分
    public JButton[][] jButtons;

    MineAlgorithm mineAlgorithm;  //算法

    MainFrame(int r,int c,int mineNum){
        this.rowsNum = r;
        this.columnsNum = c;
        this.mineNums = mineNum;

        this.setSize(Weight*columnsNum,Height*rowsNum);//设置长宽
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);        //是否可由用户更改窗口大小
        this.setTitle(title);       //设置标题
        this.setLayout(new BorderLayout()); //设置为边界布局

        mineAlgorithm = new MineAlgorithm(rowsNum,columnsNum,mineNums);
        initJButton();                     //初始化所有按钮

        certerJP = getCENTER();
        this.add(certerJP,BorderLayout.CENTER);

        this.setVisible(true);      //设置可见
    }

    public void initJButton(){
        jButtons = new JButton[rowsNum][columnsNum];
        for (int i = 0; i < rowsNum; i++) {
            for (int j = 0; j < columnsNum; j++) {

                jButtons[i][j] = new JButton();;
                setButtonImage(jButtons[i][j],0,0);  //设置默认样式

                final int ii = i,jj = j;

                ///添加自己定义的点击事件，并且重写左右点点击方法
                jButtons[i][j].addMouseListener(new MyMouseListener(){
                    @Override
                    public void mouseLeftClicked() {
                        leftClick(ii,jj);
                    }

                    @Override
                    public void mouseRightClicked() {
                        rightClick(ii,jj);
                    }

                    @Override
                    public void mouseDoubleLeftClicked(){
                        doubleLeftClick(ii,jj);
                    }
                });
            }
        }
    }

    public void leftClick(int x,int y){
        mineAlgorithm.leftClick(x,y);
        refreshPage();
    }

    public void rightClick(int x,int y){
        mineAlgorithm.rightClick(x,y);
        refreshPage();
    }

    public void doubleLeftClick(int x,int y){
        mineAlgorithm.doubleLeftClick(x,y);
        refreshPage();
    }

    //刷新页面
    public void refreshPage(){
        for(int i=0;i<mineAlgorithm.linkedList.size();i++){
            Point p = mineAlgorithm.linkedList.get(i);
            setButtonImage(jButtons[p.x][p.y],mineAlgorithm.isOpen[p.x][p.y],mineAlgorithm.checkerboard[p.x][p.y]);
        }
        certerJP.revalidate();     //重绘
        mineAlgorithm.linkedList.clear();

        if(mineAlgorithm.GameOver){
            JOptionPane.showMessageDialog(this, "BOOM!", "you lose",JOptionPane.ERROR_MESSAGE);
            return;
        }

        mineAlgorithm.OpenNum=0;
        for(int i=0;i< mineAlgorithm.rowsNum;i++){
            for(int j=0;j<mineAlgorithm.columnsNum;j++){
                if(mineAlgorithm.isOpen[i][j]==1)
                    mineAlgorithm.OpenNum++;
            }
        }
        if(mineAlgorithm.OpenNum == mineAlgorithm.rowsNum*mineAlgorithm.columnsNum - mineNums)
            JOptionPane.showMessageDialog(this, "Win!", "you win",JOptionPane.ERROR_MESSAGE);
    }

    //设置icon的样式
    public void setButtonImage(JButton jButton,int isOpen,int checkerboard){

        String path=null;  //图片路径
        if(isOpen==0){  //未打开
            path = String.format("D:/MineClearance/Image/11.png");
        }else if(isOpen==1){ //打开过
            switch (checkerboard){
                case 0:path = String.format("D:/MineClearance/Image/0.png");break;
                case 1:path = String.format("D:/MineClearance/Image/1.png");break;
                case 2:path = String.format("D:/MineClearance/Image/2.png");break;
                case 3:path = String.format("D:/MineClearance/Image/3.png");break;
                case 4:path = String.format("D:/MineClearance/Image/4.png");break;
                case 5:path = String.format("D:/MineClearance/Image/5.png");break;
                case 6:path = String.format("D:/MineClearance/Image/6.png");break;
                case 7:path = String.format("D:/MineClearance/Image/7.png");break;
                case 8:path = String.format("D:/MineClearance/Image/8.png");break;
                case 9:path = String.format("D:/MineClearance/Image/9.png");break;
            }
        }else {         //红旗标记
            path = String.format("D:/MineClearance/Image/10.png");
        }
        ImageIcon imageIcon = new ImageIcon(String.format(path));
        jButton.setIcon(imageIcon);
    }

    //获得中央Jpanel
    public JPanel getCENTER(){
        JPanel panel = new JPanel();
        GridLayout gridLayout = new GridLayout(rowsNum,columnsNum);  //设置为网格布局
        panel.setLayout(gridLayout);

        for (int i = 0; i < rowsNum; i++) {
            for (int j = 0; j < columnsNum; j++) {
                panel.add(jButtons[i][j]);
            }
        }
        return panel;
    }
}
