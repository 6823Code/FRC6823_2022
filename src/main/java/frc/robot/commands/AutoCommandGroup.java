package frc.robot.commands;

//Imports for commands and subsystems used
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.NavXHandler;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class AutoCommandGroup extends SequentialCommandGroup {

    //Declare subsystems and NavX used
    private LimeLightSubsystem limeLightSubsystem;
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private ShooterSubsystem shooterSubsystem;
    private ConveyorSubsystem conveyorSubsystem;
    private NavXHandler navXHandler;

    public AutoCommandGroup(RobotContainer robotContainer) {
        //Instantiate subsystems and NavX; set limelight to desired pipeline
        limeLightSubsystem = robotContainer.getLimeLightSubsystem();
        swerveDriveSubsystem = robotContainer.getSwervedriveSubsystem();
        shooterSubsystem = robotContainer.getShooterSubsystem();
        limeLightSubsystem.setPipeline(1);
        navXHandler = robotContainer.getNavXHandler();

        RotateToZero.setInitialAngle(navXHandler.getAngleRad());
        RotateToAngle.setInitialAngle(navXHandler.getAngleRad());

        //Add each command you want the robot to do in order
        addCommands(new AutoShoot(shooterSubsystem));
        addCommands(new Wait(1));
        addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
    }

}
