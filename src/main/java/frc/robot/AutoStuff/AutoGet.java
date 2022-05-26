package frc.robot.AutoStuff;

import java.util.Timer;

public class AutoGet {

    private Timer timer;
    private AutoTask task;

    // Initializes Timer and AutoTask with joystick number (usually 3)
    public AutoGet(int joyNum) throws Exception
    {
        timer = new Timer(true);
        task = new AutoTask(joyNum);
    }

    // Runs the run() function from AutoTask at regular intervals to get inputs
    // Delay is the delay after teleop has begun to start taking inputs
    // Period is the time between getting inputs (measured in miliseconds)
    // (16 ms ~ 60 inputs per second)
    public boolean start(long delay)
    {
        timer.scheduleAtFixedRate(task, delay, 16);
        return true;
    }
}
