package frc.robot.AutoGet;

import java.util.Timer;

public class AutoGet {

    private Timer timer;
    private AutoTask task;

    public AutoGet(int joyNum)
    {
        timer = new Timer(true);
        task = new AutoTask(joyNum);
    }

    public boolean start(long delay)
    {
        System.out.println("Started!");
        timer.scheduleAtFixedRate(task, delay, 16);
        return true;
    }
}
