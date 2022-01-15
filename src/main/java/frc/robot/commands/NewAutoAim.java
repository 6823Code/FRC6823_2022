package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.NavXHandler;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NewAutoAim extends SequentialCommandGroup {

    public NewAutoAim(LimeLightSubsystem limeLightSubsystem, ShooterSubsystem shooterSubsystem,
            SwerveDriveSubsystem swerveDriveSubsystem, int position, NavXHandler navX) {
        // commitCheck
        double distance = 0;
        double rpm = 0;
        double power = 0;

        // SmartDashboard.putNumber("Position From the target", position);
        if (position == 0) {
            // distance = -56;
            // rpm = 5925;
            distance = 116;
            // rpm = 6850;
            power = 0.49;
        } else if (position == 1) {
            // distance = -105;
            // rpm = 6575;
            distance = 300;
            // rpm = 9400;
            rpm = 9000;

            power = 0.73;

        } else if (position == -1) {
            // distance = -37
            // distance = 6000
            distance = 43; // inches
            rpm = 9000;
            power = 0.8;
        } else if (position == 2) {
            distance = 400; // ?
            // rpm = 20000;
            power = 1;
        }

        super.addCommands(new LineUpWithTargetAt(swerveDriveSubsystem, limeLightSubsystem, 0, navX, distance));
        // super.addCommands(new MoveTo3d(swerveDriveSubsystem, limeLightSubsystem, 0,
        // distance * -1));
        if (position == 2)
            super.addCommands(new NewShoot(shooterSubsystem, power, 0.6, 20, swerveDriveSubsystem));
        else
            super.addCommands(new NewShoot(shooterSubsystem, power, 0.4, 20, swerveDriveSubsystem));

        // if (power == 0)
        // super.addCommands(new NewShoot(shooterSubsystem, power, 0.4, 20,
        // swerveDriveSubsystem));
        // else
        // super.addCommands(new Shoot(shooterSubsystem, rpm, .4, 20,
        // swerveDriveSubsystem));

        // if (position == 1 || position == 2) {
        // super.addCommands(new Shoot(shooterSubsystem, rpm, .4, 20,
        // swerveDriveSubsystem));
        // } else {
        // super.addCommands(new Shoot(shooterSubsystem, rpm, .3, 20,
        // swerveDriveSubsystem));
        // }
    }
}
