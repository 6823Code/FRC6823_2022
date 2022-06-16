package frc.robot.AutoStuff;

//For file writing and getting time
import java.util.*;
import java.io.*;


// For getting controller input
import frc.robot.JoystickHandler;
import frc.robot.util.Constants;

//Neo: Follow the White Rabbit...


public class AutoTask  extends TimerTask{
    private Date s;
    private long holdTime;
    private JoystickHandler stick;
    private JoystickHandler stick2;
    private File file;


    /**
     * Constructor for objects of class TimerStuff
     */
    public AutoTask(int num, int num2) 
    {
        // for URI take file path for AutoCommand.txt
        s = new Date();
        holdTime = 0;
        stick = new JoystickHandler(num);
        stick2 = new JoystickHandler(num2);
        System.out.println("Before File");
        file = new File(Constants.fileName);
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
        if (stick.isJoystickInUse() || stick2.isJoystickInUse())
        {
            //System.out.println("Method Called");
            //System.out.println(holdTime);
            file(stick.getAxis0(), stick.getAxis1(), stick.getAxis2(), stick.getAxis3(), stick.getAxis4(), stick.getAxis5(), stick.getAxis6(), stick2.getAxis0(), stick2.getAxis1(), stick2.getAxis2(), stick2.getAxis3(), stick2.getAxis4(), stick2.getAxis5(), stick2.getAxis6(), getHold());
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
    public void file(double a0, double a1, double a2, double a3, double a4, double a5, double a6, double b0, double b1, double b2, double b3, double b4, double b5, double b6, long time)
    {
        try {
            //System.out.println("In try statement");
            FileWriter out = new FileWriter(file);
            out.append(a0 + "," + a1 + "," + a2 + "," + a3 + "," + a4 + "," + a5 + "," + a6 + "," + b0 + "," + b1 + "," + b2 + "," + b3 + "," + b4 + "," + b5 + "," + b6 + "," + time + "\n");
            out.close();
        } catch (Exception e) {
            
        }
        
    }
}