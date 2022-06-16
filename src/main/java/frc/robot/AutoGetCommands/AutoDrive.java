//Solution only works for instantaneous, interruptible, and continuous functions

package frc.robot.AutoGetCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.wpilibj.Timer;


import java.util.*;

public class AutoDrive extends CommandBase {
    
    private ArrayList<DriveInputs> inputs;
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private Timer timer;
    private double x, y, spin;
    private int inputNum;

    public AutoDrive (ArrayList<DriveInputs> a, SwerveDriveSubsystem sds)
    {
        inputs = a;
        swerveDriveSubsystem = sds;
        timer = new Timer();
    }

    public void run()
    {
        while(!timer.hasElapsed(30.0))
        {
            swerveDriveSubsystem.drive(x, y, spin);
            if (timer.hasElapsed(inputs.get(inputNum).time))
            {
                updateDrive(inputNum);
                inputNum++;
            }
        }
    }
    public void updateDrive(int num)
    {
        //do some stuff to update x,y,spin to the values in inputs.get(num)
    }
}


