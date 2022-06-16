package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestParallelDrive extends CommandBase {
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private boolean isFinished;
    private double percent;
    private double seconds;
    private Timer timer;
    private Timer globalTimer;

    public TestParallelDrive(SwerveDriveSubsystem swerveDriveSubsystem, double percentPower, Timer globalTimer) {
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        addRequirements(swerveDriveSubsystem);
        timer = new Timer();
        this.globalTimer = globalTimer;
        isFinished = false;
        this.percent = percentPower;
        this.seconds = 0.1;
    }

    @Override
    public void execute() {
        swerveDriveSubsystem.drive(0, percent, 0);

        if (timer.hasElapsed(seconds)) {
            isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        SmartDashboard.putNumber("Drive Start", globalTimer.get());
    }

    @Override
    public void end(boolean interrupted) {
        isFinished = false;
        swerveDriveSubsystem.stop();
        SmartDashboard.putNumber("Drive End", globalTimer.get());
    }
}
