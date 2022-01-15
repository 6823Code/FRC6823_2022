package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLightSubsystem;

public class ChangePipeline extends CommandBase {
    private LimeLightSubsystem limeLightSubsystem;
    private int pipeline;
    private Timer timer;

    public ChangePipeline(LimeLightSubsystem limeLightSubsystem, int pipeline) {
        this.pipeline = pipeline;
        this.limeLightSubsystem = limeLightSubsystem;
    }

    @Override
    public void initialize() {
        limeLightSubsystem.setPipeline(pipeline);
        timer = new Timer();
        timer.start();
        limeLightSubsystem.setServoAngle(58);
    }

    @Override
    public void execute() {
        return;
    }

    @Override
    public boolean isFinished() {
        return timer.hasPeriodPassed(1);
    }

    @Override
    public void end(boolean inturrupt) {
        timer.stop();
    }
}