package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class NewShoot extends CommandBase {
    private ShooterSubsystem shooterSubsystem;
    private double conveyorPower;
    private Timer timer;
    private int time;
    private SwerveDriveSubsystem swerveDriveSubsystem;

    // private LongRange2dAutoShoot.DoubleContainer rpm;
    private double power;

    private boolean aBallHasReachedTheTop = false;

    // public NewShoot(ShooterSubsystem shooterSubsystem, double rpm, double
    // conveyorPower, int time,
    // SwerveDriveSubsystem swerveDriveSubsystem) {
    // this(shooterSubsystem, new LongRange2dAutoShoot.DoubleContainer(rpm),
    // conveyorPower, time,
    // swerveDriveSubsystem);
    // }

    public NewShoot(ShooterSubsystem shooterSubsystem, double power, double conveyorPower, int time,
            SwerveDriveSubsystem swerveDriveSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        this.power = power;
        this.conveyorPower = conveyorPower;
        this.time = time;
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        addRequirements(shooterSubsystem, swerveDriveSubsystem);
    }

    @Override
    public void execute() {
        // shooterSubsystem.shooterPID(rpm, 20);
        // shooterSubsystem.shooterPID(rpm.value, 20, conveyorPower);
        // if (!aBallHasReachedTheTop) {
        // if (shooterSubsystem.ballAtTop()) {
        // aBallHasReachedTheTop = true;
        // }
        // }
        // // if (aBallHasReachedTheTop) {
        // // } else {
        // // shooterSubsystem.shooterPower(0.6, 20, 0.4);

        shooterSubsystem.shooterPower(power, 0, conveyorPower);

        SmartDashboard.putBoolean("A ball has reached the top", aBallHasReachedTheTop);
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
        aBallHasReachedTheTop = false;
    }
}