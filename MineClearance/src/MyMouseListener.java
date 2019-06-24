
import sun.rmi.runtime.Log;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class MyMouseListener implements MouseListener {

    long threadId = -1;    //记录进程号
    long delay = 150;      //延迟

    MyMouseListener(){}

    //处理左点击事件
    public void mouseLeftClicked() { }

    //处理右点击事件
    public void mouseRightClicked() { }

    public void mouseDoubleLeftClicked(){ }

    //判断线程是否在运行
    public Boolean checkThreadISRun(){
        try{
            ThreadMXBean tmx = ManagementFactory.getThreadMXBean();
            ThreadInfo info = tmx.getThreadInfo(threadId);
            System.out.println(info.getThreadState());

            if (info.getThreadState().toString().equals("TIMED_WAITING"))//线程正在运行
                return true;
            else
                return false;
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            return false;
        }
    }

    //终止线程
    public void stopThread(){
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        for (int i = 0; i < noThreads; i++) {
            if (lstThreads[i].getId() == threadId) {
                lstThreads[i].interrupt();
                return;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton()!=1&&e.getButton()!=3)return;

        /*
         *判断是否有线程在执行，如果有则视为双击终止线程
         * 否则开启线程延迟执行
         */

        if(checkThreadISRun()){
            stopThread();
            mouseDoubleLeftClicked();
            return;
        }
        new Thread(){
            public void run() {
                threadId = getId();
                try {
                    sleep(delay);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                if(e.getButton() == MouseEvent.BUTTON1)
                    mouseLeftClicked();
                else
                    mouseRightClicked();
            }
        }.start();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
