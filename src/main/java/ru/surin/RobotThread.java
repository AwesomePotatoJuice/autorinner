package ru.surin;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RobotThread extends Thread {

    private final Robot robot;
    private boolean shiftRun = false;
    private boolean stopRunning = false;

    public RobotThread(String name) {
        super(name);

        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        while (true) {

            if(stopRunning){
                stopRunning = false;

                robot.keyRelease(KeyEvent.VK_W);

                if(shiftRun){
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                }

                synchronized (this){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }

            robot.keyPress(KeyEvent.VK_W);

            if(shiftRun){
                robot.keyPress(KeyEvent.VK_SHIFT);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void setStopRunning(boolean stopRunning){
        this.stopRunning = stopRunning;
    }

    public void setShiftRun(boolean shiftRun) {
        this.shiftRun = shiftRun;
        if(!shiftRun){
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }
    }
}
