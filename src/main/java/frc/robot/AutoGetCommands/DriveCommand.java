package frc.robot.AutoGetCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.*;

public class DriveCommand extends CommandBase {
    
    private ArrayList<DriveInputs> inputs;

    public DriveCommand(ArrayList<DriveInputs> a)
    {
        inputs = a;
    }
    public ArrayList<DriveInputs> getInputs()
    {
        return inputs;
    }

}
