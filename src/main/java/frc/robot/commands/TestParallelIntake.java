package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;

public class TestParallelIntake extends CommandBase {
    private IntakeSubsystem intakeSubsystem;
    private Timer timer;
    private Timer globalTimer;
    private double seconds;
    private boolean isItFinished;

    public TestParallelIntake(IntakeSubsystem intakeSubsystem, double seconds, Timer globalTimer) {
        this.intakeSubsystem = intakeSubsystem;

        this.timer = new Timer();
        this.globalTimer = globalTimer;


        this.seconds = seconds;
        isItFinished = false;

        addRequirements(intakeSubsystem);
    }

    @Override
    public void execute() {
        intakeSubsystem.intake();

        if (timer.hasElapsed(seconds)){
            isItFinished = true;
        }
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        SmartDashboard.putNumber("Intake Start", globalTimer.get());
    }

    @Override
    public boolean isFinished() {
        return isItFinished;
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.stopIntake();
        isItFinished = false;
        SmartDashboard.putNumber("Intake End Time", globalTimer.get());
    }
}