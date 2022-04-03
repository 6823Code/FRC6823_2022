package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.NavXHandler;
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
    private NavXHandler navX;

    public AutoCommandGroup(RobotContainer robotContainer, String selection) {
        //Instantiate subsystems and NavX; set limelight to desired pipeline
        swerveDriveSubsystem = robotContainer.getSwervedriveSubsystem();
        shooterSubsystem = robotContainer.getShooterSubsystem();
        conveyorSubsystem = robotContainer.getConveyorSubsystem();
        intakeSubsystem = robotContainer.getIntakeSubsystem();
        limeLightSubsystem = robotContainer.getLimeLightSubsystem();
        navX = robotContainer.getNavXHandler();
        
        //Add each command you want the robot to do in order
        if (selection.toUpperCase().equals("1BALL")){
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new HammerDrop(intakeSubsystem, 0.1));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.1, 0.5));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, intakeSubsystem, 0, 0.6, 1500, 1500));
            addCommands(new Wait(3));
            addCommands(new FullHalt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem, intakeSubsystem));
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }else if (selection.toUpperCase().equals("TAXI")){
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        // }else if (selection.toUpperCase().equals("RED")){
        //     addCommands(new ServoTuck(limeLightSubsystem));
        //     addCommands(new HammerDrop(intakeSubsystem, 0.1));
        //     addCommands(new GoBackwards(swerveDriveSubsystem, 0.1, 0.5));
        //     addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, intakeSubsystem, 0, 0.6, 1500, 1500));
        //     addCommands(new Wait(3));
        //     addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
        //     addCommands(new AutoSearchRight(swerveDriveSubsystem, limeLightSubsystem, 1));
        //     addCommands(new PickUpSeconds(swerveDriveSubsystem, intakeSubsystem, limeLightSubsystem, 1, 0.3, 0.5));
        //     addCommands(new AutoSearchLeft(swerveDriveSubsystem, limeLightSubsystem, 0));
        //     addCommands(new LineUpToShoot(swerveDriveSubsystem, limeLightSubsystem, 2));
        //     // addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, 0, 0.6, 1500, 1500));
        //     // addCommands(new Wait(3));
        //     // addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
        //     addCommands(new ServoTuck(limeLightSubsystem));
        //     // addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        // }else  if (selection.toUpperCase().equals("BLUE")){
        //     addCommands(new ServoTuck(limeLightSubsystem));
        //     addCommands(new HammerDrop(intakeSubsystem, 0.1));
        //     addCommands(new GoBackwards(swerveDriveSubsystem, 0.1, 0.5));
        //     addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, intakeSubsystem, 0, 0.6, 1500, 1500));
        //     addCommands(new Wait(3));
        //     addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
        //     addCommands(new AutoSearchRight(swerveDriveSubsystem, limeLightSubsystem, 2));
        //     // addCommands(new PickUpBall(swerveDriveSubsystem, intakeSubsystem, limeLightSubsystem, 2));
        //     // addCommands(new AutoSearchLeft(swerveDriveSubsystem, limeLightSubsystem, 0));
        //     // addCommands(new LineUpToShoot(swerveDriveSubsystem, limeLightSubsystem, 2));
        //     // addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, 0, 0.6, 1500, 1500));
        //     // addCommands(new Wait(3));
        //     // addCommands(new Halt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem));
        //     addCommands(new ServoTuck(limeLightSubsystem));
        //     // addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }else if (selection.toUpperCase().equals("BEHIND")){
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new HammerDrop(intakeSubsystem, 0.15));
            addCommands(new PickUpSeconds(swerveDriveSubsystem, intakeSubsystem, 0.2, 2.2));
            addCommands(new RotateToAngle(swerveDriveSubsystem, navX, Math.PI));
            addCommands(new GoBackwards(swerveDriveSubsystem, -0.2, 0.5));
            addCommands(new Wait(.1));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, intakeSubsystem, 0, 0.6, 1812, 1812)); //About 5000 rpm, 2.76 rpm/unit
            addCommands(new Wait(3));
            addCommands(new FullHalt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem, intakeSubsystem));
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }
    }

}
