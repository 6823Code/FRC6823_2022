package frc.robot.AutoGet;

//For file writing and getting time
import java.util.*;
import java.io.*;

// For getting controller input
import frc.robot.JoystickHandler;




public class AutoTask  extends TimerTask{
    private Date s;
    private long holdTime;
    private JoystickHandler stick;
    private File file;


    /**
     * Constructor for objects of class TimerStuff
     */
    public AutoTask(int num)
    {
        s = new Date();
        holdTime = 0;
        stick = new JoystickHandler(num);
        file = new File("autoCommands.txt");
        try {
            file.createNewFile();
        } catch (Exception e) {
            
        }
    }

    public void run()
    {
        Date e = new Date();
        holdTime = e.getTime() - s.getTime();
        if (holdTime > 29000)
        {
            cancel();
        }
        if (stick.isJoystickInUse())
        {
            file(stick.getAxis0(), stick.getAxis1(), stick.getAxis2(), stick.getAxis3(), stick.getAxis4(), stick.getAxis5(), stick.getAxis6(), getHold());
        }
    }

    public long getHold()
    {
        return holdTime;
    }


    public void file(double a0, double a1, double a2, double a3, double a4, double a5, double a6, long time)
    {
        try {
            FileWriter out = new FileWriter(file);
            out.write(a0 + "," + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + time + ",");
            out.close();
        } catch (Exception e) {
            
        }
        
    }
}