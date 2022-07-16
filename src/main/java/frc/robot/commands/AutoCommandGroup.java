package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.NavXHandler;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;
import frc.robot.util.Constants;

/**
 * A set of CommandGroups that each run a list of commands in sequence during Autonomous.
 */
public class AutoCommandGroup extends SequentialCommandGroup {

    //Declare subsystems and NavX used
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private ShooterSubsystem shooterSubsystem;
    private ConveyorSubsystem conveyorSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private LimeLightSubsystem limeLightSubsystem;
    private NavXHandler navX;

    /**
    * Creates a new AutoCommandGroup. The given commands will be run sequentially, with the
    * CommandGroup finishing when the last command finishes.
    *
    * @param robotContainer the robot container instance for the robot
    * @param selection the idenifier string for the autonomous sequence to run
    */
    public AutoCommandGroup(RobotContainer robotContainer, String selection) {
        //Pull instances of subsystems and NavX from robotContainer
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
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, intakeSubsystem, 0.6, 1500, 1500));
            addCommands(new Wait(5));
            addCommands(new FullHalt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem, intakeSubsystem));
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }else if (selection.toUpperCase().equals("TAXI")){
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }else if (selection.toUpperCase().equals("BALL 4") || selection.toUpperCase().equals("BALL 10")){
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new HammerDrop(intakeSubsystem, 0.15));
            addCommands(new PickUpSeconds(swerveDriveSubsystem, intakeSubsystem, 0.2, 2.2));
            addCommands(new RotateToAngle(swerveDriveSubsystem, navX, Math.PI));
            addCommands(new GoBackwards(swerveDriveSubsystem, -0.2, 0.5));
            addCommands(new Wait(.1));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, intakeSubsystem, 0.6, 1812, 1812)); //About 5000 rpm, 2.76 rpm/unit
            addCommands(new Wait(5));
            addCommands(new FullHalt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem, intakeSubsystem));
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }else if (selection.toUpperCase().equals("BALL 1") || selection.toUpperCase().equals("BALL 7")){
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new HammerDrop(intakeSubsystem, 0.15));
            addCommands(new PickUpSeconds(swerveDriveSubsystem, intakeSubsystem, 0.2, 2.2));
            addCommands(new RotateToAngle(swerveDriveSubsystem, navX, 177 * Constants.degToRad));
            addCommands(new GoBackwards(swerveDriveSubsystem, -0.2, 0.5));
            addCommands(new Wait(.1));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, intakeSubsystem, 0.6, 1812, 1812)); //About 5000 rpm, 2.76 rpm/unit
            addCommands(new Wait(5));
            addCommands(new FullHalt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem, intakeSubsystem));
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }else if (selection.toUpperCase().equals("BALL 2") || selection.toUpperCase().equals("BALL 8")){
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new HammerDrop(intakeSubsystem, 0.15));
            addCommands(new PickUpSeconds(swerveDriveSubsystem, intakeSubsystem, 0.2, 2.2));
            addCommands(new RotateToAngle(swerveDriveSubsystem, navX, 160 * Constants.degToRad));
            addCommands(new GoBackwards(swerveDriveSubsystem, -0.2, 0.5));
            addCommands(new Wait(.1));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, intakeSubsystem, 0.6, 1812, 1812)); //About 5000 rpm, 2.76 rpm/unit
            addCommands(new Wait(5));
            addCommands(new FullHalt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem, intakeSubsystem));
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }else if (selection.toUpperCase().equals("4 BLUE")){
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new HammerDrop(intakeSubsystem, 0.15));
            addCommands(new PickUpSeconds(swerveDriveSubsystem, intakeSubsystem, 0.2, 2.2));
            addCommands(new RotateToAngle(swerveDriveSubsystem, navX, Math.PI * 8 / 9));
            addCommands(new GoBackwards(swerveDriveSubsystem, -0.2, 0.5));
            addCommands(new Wait(.1));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, intakeSubsystem, 0.6, 1812, 1812)); //About 5000 rpm, 2.76 rpm/unit
            addCommands(new Wait(3));
            addCommands(new FullHalt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem, intakeSubsystem));
            addCommands(new RotateToAngle(swerveDriveSubsystem, navX, 0));
            addCommands(new AutoSearchRight(swerveDriveSubsystem, limeLightSubsystem, 2));
            addCommands(new PickUpUntilSize(swerveDriveSubsystem, intakeSubsystem, limeLightSubsystem, 2));
            addCommands(new Wait(2));
            addCommands(new AutoSearchRight(swerveDriveSubsystem, limeLightSubsystem, 0));
            addCommands(new PickUpUntilSize(swerveDriveSubsystem, intakeSubsystem, limeLightSubsystem, 0));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, intakeSubsystem, 0.6, 1812, 1812));
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }else if (selection.toUpperCase().equals("4 RED")){
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new HammerDrop(intakeSubsystem, 0.15));
            addCommands(new PickUpSeconds(swerveDriveSubsystem, intakeSubsystem, 0.2, 2.2));
            addCommands(new RotateToAngle(swerveDriveSubsystem, navX, 160 * Constants.degToRad));
            addCommands(new GoBackwards(swerveDriveSubsystem, -0.2, 0.5));
            addCommands(new Wait(.1));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, intakeSubsystem, 0.6, 1812, 1812)); //About 5000 rpm, 2.76 rpm/unit
            addCommands(new Wait(3));
            addCommands(new FullHalt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem, intakeSubsystem));
            addCommands(new RotateToAngle(swerveDriveSubsystem, navX, 0));
            addCommands(new Wait(0.1));
            addCommands(new AutoSearchRight(swerveDriveSubsystem, limeLightSubsystem, 1));
            addCommands(new PickUpUntilSize(swerveDriveSubsystem, intakeSubsystem, limeLightSubsystem, 1));
            addCommands(new Wait(2));
            addCommands(new AutoSearchRight(swerveDriveSubsystem, limeLightSubsystem, 0));
            addCommands(new PickUpUntilSize(swerveDriveSubsystem, intakeSubsystem, limeLightSubsystem, 0));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, intakeSubsystem, 0.6, 1812, 1812));
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }else if (selection.toUpperCase().equals("NAV")){
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new HammerDrop(intakeSubsystem, 0.15));
            addCommands(new PickUpSeconds(swerveDriveSubsystem, intakeSubsystem, 0.2, 2.2));
            addCommands(new RotateToAngle(swerveDriveSubsystem, navX, Math.PI));
            addCommands(new GoBackwards(swerveDriveSubsystem, -0.2, 0.5));
            addCommands(new Wait(.1));
            addCommands(new AutoShoot(shooterSubsystem, conveyorSubsystem, intakeSubsystem, 0.6, 1812, 1812)); //About 5000 rpm, 2.76 rpm/unit
            addCommands(new Wait(5));
            addCommands(new FullHalt(swerveDriveSubsystem, shooterSubsystem, conveyorSubsystem, intakeSubsystem));
            addCommands(new ServoTuck(limeLightSubsystem));
            addCommands(new GoBackwards(swerveDriveSubsystem, 0.6, 0.5));
        }
    }
}
