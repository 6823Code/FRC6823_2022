package frc.robot.commands;

//Imports for commands and subsystems used
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.NavXHandler;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class AutoCommandGroup extends SequentialCommandGroup {

    //Declare subsystems and NavX used
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private ShooterSubsystem shooterSubsystem;
    private ConveyorSubsystem conveyorSubsystem;
    private NavXHandler navXHandler;

    public AutoCommandGroup(RobotContainer robotContainer) {
        //Instantiate subsystems and NavX; set limelight to desired pipeline
        swerveDriveSubsystem = robotContainer.getSwervedriveSubsystem();
        shooterSubsystem = robotContainer.getShooterSubsystem();
        
        //Add each command you want the robot to do in order
        if (Robot.PREFS.getString("Alliance Color", "Red").toUpperCase().equals("RED")){
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 1));
            addCommands(new AutoShoot(shooterSubsystem));
            addCommands(new Wait(1));
            addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
            addCommands(new RotateToAngle(swerveDriveSubsystem, navXHandler, Math.PI/2));
        }else{
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 1));
            addCommands(new AutoShoot(shooterSubsystem));
            addCommands(new Wait(1));
            addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
            addCommands(new RotateToAngle(swerveDriveSubsystem, navXHandler, 3 * Math.PI/2));
        }
    }

}
