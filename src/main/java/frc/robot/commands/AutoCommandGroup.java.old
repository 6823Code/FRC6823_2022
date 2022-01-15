package frc.robot.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class AutoCommandGroup extends SequentialCommandGroup {

    public AutoCommandGroup(RobotContainer robotContainer) {
        boolean leftRight = Preferences.getInstance().getBoolean("leftRight", false);
        boolean back = Preferences.getInstance().getBoolean("backShoot", false);
        boolean sideShoot = Preferences.getInstance().getBoolean("sideShoot", false);
        int waitSeconds = (int) Preferences.getInstance().getDouble("waitTime", 1);

        addCommands(new Wait(waitSeconds));

        if (leftRight && !back) {
            addCommands(new MoveTo3d(robotContainer.swerveDriveSubsystem, robotContainer.limeLightSubsystem,
                    robotContainer.limeLightSubsystem.getX(), -56));
        } else if (leftRight && back) {
            addCommands(new MoveTo3d(robotContainer.swerveDriveSubsystem, robotContainer.limeLightSubsystem,
                    robotContainer.limeLightSubsystem.getX(), -130));
        }

        if (!sideShoot) {
            double conveyorSpeed = .5;
            int rpm = 7250;
            if (back) {
                addCommands(
                        new MoveTo3d(robotContainer.swerveDriveSubsystem, robotContainer.limeLightSubsystem, 0, -140));
                rpm = 6700;
                conveyorSpeed = .6;

            } else {
                addCommands(
                        new MoveTo3d(robotContainer.swerveDriveSubsystem, robotContainer.limeLightSubsystem, 0, -56));
                rpm = 5925;
                conveyorSpeed = .5;
            }
            addCommands(new ParallelRaceGroup(new Shoot(robotContainer.shooterSubsystem, rpm, conveyorSpeed, 5),
                    new JustAim(robotContainer.swerveDriveSubsystem, robotContainer.limeLightSubsystem)));
        } else {
            addCommands(new LongRange2d(robotContainer.swerveDriveSubsystem, robotContainer.limeLightSubsystem,
                    robotContainer.shooterSubsystem, null, 0));
        }

        // addCommands(new MoveTo3d(robotContainer.swerveDriveSubsystem,
        // robotContainer.limeLightSubsystem, 0, -56),
        // new InstantCommand(robotContainer.fieldSpaceDriveCommand::zero),
        // new Shoot(robotContainer.shooterSubsystem, 8500, .65, 5));
    }

}