package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.Timer;

public class StartTimer extends CommandBase {
    private Timer timer;
    private boolean isFinished;

    public StartTimer(Timer timer) {
        this.timer = timer;
    }

    @Override
    public void execute() {
        timer.reset();
        timer.start();
        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void end(boolean interrupted) {
        isFinished = false;
    }
}
