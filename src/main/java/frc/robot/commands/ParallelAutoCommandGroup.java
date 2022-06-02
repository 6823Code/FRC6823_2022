package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class ParallelAutoCommandGroup extends ParallelCommandGroup {

    //Declare subsystems and NavX used
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private Timer globalTimer;

    //private Input[] inputs;

    public ParallelAutoCommandGroup(RobotContainer robotContainer) {
        //Instantiate subsystems and NavX; set limelight to desired pipeline
        swerveDriveSubsystem = robotContainer.getSwervedriveSubsystem();
        intakeSubsystem = robotContainer.getIntakeSubsystem();
        
        //Add each command you want the robot to do in order
        addCommands(new StartTimer(globalTimer));
        addCommands(new TestParallelDrive(swerveDriveSubsystem, 0.5, globalTimer));
        addCommands(new TestParallelIntake(intakeSubsystem, 0.1, globalTimer));
    }

}