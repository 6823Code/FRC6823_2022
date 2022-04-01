package frc.robot.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.NavXHandler;
// import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class AutoCommandGroup extends SequentialCommandGroup {

    //Declare subsystems and NavX used
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private ShooterSubsystem shooterSubsystem;
    private ConveyorSubsystem conveyorSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private LimeLightSubsystem limeLightSubsystem;
    // private NavXHandler navXHandler;
    private SendableChooser<String> autoSelect;

    public AutoCommandGroup(RobotContainer robotContainer) {
        //Instantiate subsystems and NavX; set limelight to desired pipeline
        swerveDriveSubsystem = robotContainer.getSwervedriveSubsystem();
        shooterSubsystem = robotContainer.getShooterSubsystem();
        conveyorSubsystem = robotContainer.getConveyorSubsystem();
        intakeSubsystem = robotContainer.getIntakeSubsystem();
        limeLightSubsystem = robotContainer.getLimeLightSubsystem();

        autoSelect = new SendableChooser<String>();
        autoSelect.setDefaultOption("Red", "Red"); //change this
        autoSelect.addOption("Blue", "Blue");
        autoSelect.addOption("1Ball", "1Ball");
        autoSelect.addOption("Taxi", "Taxi");
        autoSelect.addOption("None", "None");
        
        //Add each command you want the robot to do in order
        if (autoSelect.getSelected().toUpperCase().equals("1BALL")){
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new HammerDrop(intakeSubsystem, 0.1));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.1, 0.5));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, 0, 0.6, shooterSubsystem.getShooterRPMLeft()*20, shooterSubsystem.getShooterRPMRight()*20));
            addCommands(new Wait(3));
            addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }else if (autoSelect.getSelected().toUpperCase().equals("TAXI")){
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }else if (autoSelect.getSelected().toUpperCase().equals("RED")){
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new HammerDrop(intakeSubsystem, 0.1));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.1, 0.5));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, 0, 0.6, shooterSubsystem.getShooterRPMLeft()*20, shooterSubsystem.getShooterRPMRight()*20));
            addCommands(new Wait(3));
            addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
            addCommands(new AutoSearchRight(swerveDriveSubsystem, limeLightSubsystem, 1));
            // addCommands(new PickUpBall(swerveDriveSubsystem, intakeSubsystem, limeLightSubsystem, 1));
            // addCommands(new AutoSearchLeft(swerveDriveSubsystem, limeLightSubsystem, 0));
            // addCommands(new LineUpToShoot(swerveDriveSubsystem, limeLightSubsystem, 2));
            // addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, 0, 0.6, 1500, 1500));
            // addCommands(new Wait(3));
            // addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
            addCommands(new ServoTuck(limeLightSubsystem));
            // addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }else  if (autoSelect.getSelected().toUpperCase().equals("BLUE")){
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new HammerDrop(intakeSubsystem, 0.1));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.1, 0.5));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, 0, 0.6, shooterSubsystem.getShooterRPMLeft()*20, shooterSubsystem.getShooterRPMRight()*20));
            addCommands(new Wait(3));
            addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
            addCommands(new AutoSearchRight(swerveDriveSubsystem, limeLightSubsystem, 2));
            // addCommands(new PickUpBall(swerveDriveSubsystem, intakeSubsystem, limeLightSubsystem, 2));
            // addCommands(new AutoSearchLeft(swerveDriveSubsystem, limeLightSubsystem, 0));
            // addCommands(new LineUpToShoot(swerveDriveSubsystem, limeLightSubsystem, 2));
            // addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, 0, 0.6, 1500, 1500));
            // addCommands(new Wait(3));
            // addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
            addCommands(new ServoTuck(limeLightSubsystem));
            // addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }
    }

}
