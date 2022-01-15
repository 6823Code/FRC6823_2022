package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class LongRange2dAutoShoot extends SequentialCommandGroup {
    // private LimeLightSubsystem limeLightSubsystem;
    // private SwerveDriveSubsystem swerveDriveSubsystem;
    // private ShooterSubsystem shooterSubsystem;

    private DoubleContainer rpm;

    public LongRange2dAutoShoot(LimeLightSubsystem limeLightSubsystem, ShooterSubsystem shooterSubsystem,
            SwerveDriveSubsystem swerveDriveSubsystem) {
        // this.shooterSubsystem = shooterSubsystem;
        // this.limeLightSubsystem = limeLightSubsystem;
        // this.swerveDriveSubsystem = swerveDriveSubsystem;

        // super.addCommands(new ChangePipeline(limeLightSubsystem, 2),
        // new LongRange2d(swerveDriveSubsystem, limeLightSubsystem, shooterSubsystem,
        // rpm),
        // new ParallelRaceGroup(new Shoot(shooterSubsystem, rpm, .35, 5),
        // new JustAim(swerveDriveSubsystem, limeLightSubsystem)));

        this.rpm = new DoubleContainer(0);

        // addCommands(new ChangePipeline(limeLightSubsystem, 2),
        // new LongRange2d(swerveDriveSubsystem, limeLightSubsystem, shooterSubsystem,
        // rpm, 0));
        addCommands(new LongRange2d(swerveDriveSubsystem, limeLightSubsystem, shooterSubsystem, rpm, 0),
                new ParallelRaceGroup(new Shoot(shooterSubsystem, rpm, .75, 5, swerveDriveSubsystem)));
    }

    public static class DoubleContainer {
        public DoubleContainer(double value) {
            this.value = value;
        }

        public double value;
    }
}