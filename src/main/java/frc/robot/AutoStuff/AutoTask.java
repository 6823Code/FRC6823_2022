package frc.robot.AutoStuff;

//For file writing and getting time
import java.util.*;
import java.io.*;


// For getting controller input
import frc.robot.JoystickHandler;

//Neo: Follow the White Rabbit...


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
        // for URI take file path for AutoCommand.txt
        s = new Date();
        holdTime = 0;
        stick = new JoystickHandler(num);
        System.out.println("Before File");
        file = new File("/home/lvuser/Inputs.txt");
        // file.mkdirs();
        try {
            file.createNewFile();
        } catch (Exception e) {
            System.out.println("Not able to make file");
        }
        
    }

    // Gets inputs from axes and runs File, 
    // also checks to see if program has been running for too long
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
            //System.out.println("Method Called");
            //System.out.println(holdTime);
            file(stick.getAxis0(), stick.getAxis1(), stick.getAxis2(), stick.getAxis3(), stick.getAxis4(), stick.getAxis5(), stick.getAxis6(), getHold());
            Scanner myScan;
            try {
                System.out.println("try to print");
                myScan = new Scanner(file);
                System.out.println("file found");
                System.out.println(myScan.nextLine());
                myScan.close();
            } catch (FileNotFoundException e1) {
                System.out.println("file not found?");
            }
        }
    }

    // In case holdtime is needed from another class
    public long getHold()
    {
        return holdTime;
    }

    // Files away inputs for later use
    public void file(double a0, double a1, double a2, double a3, double a4, double a5, double a6, long time)
    {
        try {
            //System.out.println("In try statement");
            FileWriter out = new FileWriter(file);
            out.append(a0 + "," + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + time + "\n");
            out.close();
        } catch (Exception e) {
            
        }
        
    }
}