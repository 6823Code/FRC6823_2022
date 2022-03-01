package frc.robot.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.NavXHandler;
// import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class AutoCommandGroup extends SequentialCommandGroup {

    //Declare subsystems and NavX used
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private ShooterSubsystem shooterSubsystem;
    private ConveyorSubsystem conveyorSubsystem;
    // private NavXHandler navXHandler;

    public AutoCommandGroup(RobotContainer robotContainer) {
        //Instantiate subsystems and NavX; set limelight to desired pipeline
        swerveDriveSubsystem = robotContainer.getSwervedriveSubsystem();
        shooterSubsystem = robotContainer.getShooterSubsystem();
        conveyorSubsystem = robotContainer.getConveyorSubsystem();
        
        //Add each command you want the robot to do in order
        if (Preferences.getString("allianceColor", "red").toUpperCase().equals("RED")){
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, 0, 0.6, 4000, 4000));
            addCommands(new Wait(5));
            //addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 1));
            //addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
        }else{
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, 0, 0.6, 4000, 4000));
            addCommands(new Wait(5));
            //addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 1));
            //addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
        }
    }

}
