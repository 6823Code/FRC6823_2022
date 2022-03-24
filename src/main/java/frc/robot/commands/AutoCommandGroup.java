package frc.robot.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.NavXHandler;
// import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
// import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class AutoCommandGroup extends SequentialCommandGroup {

    //Declare subsystems and NavX used
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private ShooterSubsystem shooterSubsystem;
    private ConveyorSubsystem conveyorSubsystem;
    private IntakeSubsystem intakeSubsystem;
    // private LimeLightSubsystem limeLightSubsystem;
    // private NavXHandler navXHandler;

    public AutoCommandGroup(RobotContainer robotContainer) {
        //Instantiate subsystems and NavX; set limelight to desired pipeline
        swerveDriveSubsystem = robotContainer.getSwervedriveSubsystem();
        shooterSubsystem = robotContainer.getShooterSubsystem();
        conveyorSubsystem = robotContainer.getConveyorSubsystem();
        intakeSubsystem = robotContainer.getIntakeSubsystem();
        // limeLightSubsystem = robotContainer.getLimeLightSubsystem();
        
        //Add each command you want the robot to do in order
        if (Preferences.getString("allianceColor", "red").toUpperCase().equals("RED")){
            addCommands(new HammerDrop(intakeSubsystem, 0.1));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.1, 0.5));
            // addCommands(new AutoAim2d(swerveDriveSubsystem, limeLightSubsystem, 0));
            // addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, 0, 0.6, shooterSubsystem.getShooterRPMLeft()*20, shooterSubsystem.getShooterRPMRight()*20));
            // addCommands(new Wait(3));
            // addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem))
            // addCommands(new AutoSearchRight(swerveDriveSubsystem, limeLightSubsystem, 1));
            // addCommands(new PickUpBall(swerveDriveSubsystem, intakeSubsystem, limeLightSubsystem, 1));
            // addCommands(new AutoSearchLeft(swerveDriveSubsystem, limeLightSubsystem, 0));
            // addCommands(new AutoAim2d(swerveDriveSubsystem, limeLightSubsystem, 0));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, 0, 0.6, 1500, 1500));
            addCommands(new Wait(3));
            addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }else if (Preferences.getString("allianceColor", "blue").toUpperCase().equals("BLUE")){
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.1, 0.5));
            // addCommands(new AutoAim2d(swerveDriveSubsystem, limeLightSubsystem, 0));
            // addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, 0, 0.6, shooterSubsystem.getShooterRPMLeft()*20, shooterSubsystem.getShooterRPMRight()*20));
            // addCommands(new Wait(3));
            // addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem))
            // addCommands(new AutoSearchRight(swerveDriveSubsystem, limeLightSubsystem, 2));
            // addCommands(new PickUpBall(swerveDriveSubsystem, intakeSubsystem, limeLightSubsystem, 2));
            // addCommands(new AutoSearchLeft(swerveDriveSubsystem, limeLightSubsystem, 0));
            // addCommands(new AutoAim2d(swerveDriveSubsystem, limeLightSubsystem, 0));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, 0, 0.6, 4140*20, 4140*20));
            addCommands(new Wait(3));
            addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }
    }

}
