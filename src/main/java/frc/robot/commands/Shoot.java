package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class Shoot extends CommandBase {
    private ShooterSubsystem shooterSubsystem;
    private double conveyorPower;
    private Timer timer;
    private int time;
    private SwerveDriveSubsystem swerveDriveSubsystem;

    private LongRange2dAutoShoot.DoubleContainer rpm;

    public Shoot(ShooterSubsystem shooterSubsystem, double rpm, double conveyorPower, int time,
            SwerveDriveSubsystem swerveDriveSubsystem) {
        this(shooterSubsystem, new LongRange2dAutoShoot.DoubleContainer(rpm), conveyorPower, time,
                swerveDriveSubsystem);
    }

    public Shoot(ShooterSubsystem shooterSubsystem, LongRange2dAutoShoot.DoubleContainer rpm, double conveyorPower,
            int time, SwerveDriveSubsystem swerveDriveSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        this.rpm = rpm;
        this.conveyorPower = conveyorPower;
        this.time = time;
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        addRequirements(shooterSubsystem, swerveDriveSubsystem);
    }

    @Override
    public void execute() {
        // shooterSubsystem.shooterPID(rpm, 20);
        shooterSubsystem.shooterPID(rpm.value, 70, conveyorPower);
        swerveDriveSubsystem.drive(0, 0, 0);
    }

    @Override
    public void initialize() {
        shooterSubsystem.startTimer();
        timer = new Timer();
        timer.start();
    }

    @Override
    public boolean isFinished() {
        return timer.hasPeriodPassed(time);
    }

    @Override
    public void end(boolean inturrupted) {
        shooterSubsystem.stopTimer();
        timer.stop();
        shooterSubsystem.stopShooterSpin();
    }
}