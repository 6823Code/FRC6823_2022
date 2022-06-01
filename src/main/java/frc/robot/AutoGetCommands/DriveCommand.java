package frc.robot.AutoGetCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveSubsystem;

import java.util.*;

public class DriveCommand extends CommandBase {
    
    private ArrayList<DriveInputs> inputs;
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private boolean isFinished;
    private Timer timer;

    public DriveCommand(ArrayList<DriveInputs> a, SwerveDriveSubsystem sds)
    {
        inputs = a;
        isFinished = false;
        swerveDriveSubsystem = sds;
        timer = new Timer();
    }

    public SwerveDriveSubsystem getSwerveDriveSubsystem() {
        return swerveDriveSubsystem;
    }

    public void run()
    {

    }

    public boolean isFinished()
    {
        return isFinished;
    }
    public ArrayList<DriveInputs> getInputs()
    {
        return inputs;
    }

}
