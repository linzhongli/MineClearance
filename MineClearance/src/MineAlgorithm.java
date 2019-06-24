

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class MineAlgorithm {

    LinkedList<Point>linkedList;

    public boolean GameOver;
    public boolean isWin;
    public int rowsNum;           //行数
    public int columnsNum;        //列数
    public int[][] isOpen;        //是否开启
    /*  isOpen[][]
     *   0未开启，1开启，2标记旗子
     */
    public int[][] checkerboard;  //逻辑棋盘
    public int mineNum;           //雷的数量
    public int OpenNum;           //开启的格子数量

    //dfs
    int[] dirX= {0,0,1,1,1,-1,-1,-1};
    int[] dirY= {1,-1,1,-1,0,1,-1,0};

    /*
     *   checkerboard[][]
     *   0-8 周围的雷数
     *   9  雷
     *   10 旗子
     */

    MineAlgorithm (int r,int c,int mineNum){
        linkedList = new LinkedList<>();
        rowsNum = r;
        columnsNum = c;
        this.mineNum = mineNum;
        isOpen = new int[r][c];
        GameOver = false;
        isWin = true;
        OpenNum = 0;
    }

    //随机创建地图
    public void createCheckerboard(int click_x,int click_y)//点击的位置
    {
        checkerboard = new int[rowsNum][columnsNum];
        //随机地图
        Random ra =new Random();
        int cnt = 0;
        while(cnt<mineNum){
            int x = ra.nextInt(rowsNum);
            int y = ra.nextInt(columnsNum);
            if(checkerboard[x][y]==9)continue;
            //避免雷出现在点击第一次的附近八格内
            if(x==click_x+1&&y==click_y)continue;
            if(x==click_x-1&&y==click_y)continue;
            if(x==click_x&&y==click_y)continue;
            if(x==click_x+1&&y==click_y-1)continue;
            if(x==click_x-1&&y==click_y-1)continue;
            if(x==click_x&&y==click_y-1)continue;
            if(x==click_x+1&&y==click_y+1)continue;
            if(x==click_x-1&&y==click_y+1)continue;
            if(x==click_x&&y==click_y+1)continue;

            checkerboard[x][y]=9;
            cnt++;
        }

        //初始化地图的
        for(int i=0;i<rowsNum;i++){
            for(int j=0;j<columnsNum;j++){
                if(checkerboard[i][j]==9)continue;
                for(int k=0;k<8;k++){
                    if(i+dirX[k]<0||j+dirY[k]<0||i+dirX[k]>=rowsNum||j+dirY[k]>=columnsNum)continue;
                    if(checkerboard[i+dirX[k]][j+dirY[k]]==9)
                        checkerboard[i][j]++;
                }
            }
        }
    }

    //自动扩展地图
    public void dfs(int x,int y){
        if(x<0||y<0||x>=rowsNum||y>=columnsNum)return ;
        if(isOpen[x][y]!=1){
            isOpen[x][y] = 1;
            linkedList.add(new Point(x,y));
        }else return;
        if(checkerboard[x][y]==0)
        {
            for(int i=0;i<8;i++){
                dfs(x+dirX[i],y+dirY[i]);
            }
        }
    }

    //单击左键
    public void leftClick(int x,int y){
        if(checkerboard==null){
            createCheckerboard(x,y);
        }
        if(isOpen[x][y]!=0)return ;
        switch (checkerboard[x][y]){
            case 0:dfs(x,y);break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8: isOpen[x][y]=1;linkedList.add(new Point(x,y)); break;
            case 9: isOpen[x][y]=1;linkedList.add(new Point(x,y)); GameOver = true;break;
        }
    }

    //单击右键
    public void rightClick(int x,int y){
        if(isOpen[x][y]==0){
            isOpen[x][y]=2;
            linkedList.add(new Point(x,y));
        }else if(isOpen[x][y]==2){
            isOpen[x][y]=0;
            linkedList.add(new Point(x,y));
        }
    }

    //双击左键
    public void doubleLeftClick(int x,int y){
        if(isOpen[x][y]==0||checkerboard[x][y]==0||checkerboard[x][y]==9)return ;
        int nearbyFlag = 0;  //计算附近的旗子
        for(int i=0;i<8;i++){
            int xx = x+dirX[i],yy = y+dirY[i];
            if(xx<0||yy<0||xx>=rowsNum||yy>=columnsNum)continue;

            if(isOpen[xx][yy]==2){
                nearbyFlag++;
            }
        }

        //附近旗子数等于附近雷数
        if(nearbyFlag==checkerboard[x][y]){
            for(int i=0;i<8;i++){
                int xx = x+dirX[i],yy = y+dirY[i];
                if(xx<0||yy<0||xx>=rowsNum||yy>=columnsNum)continue;

                if(isOpen[xx][yy]==0){
                    leftClick(xx,yy);
                }
            }
        }
    }
}
