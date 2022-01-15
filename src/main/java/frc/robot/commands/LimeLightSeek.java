package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLightSubsystem;

public class LimeLightSeek extends CommandBase {

    private LimeLightSubsystem limeLightSubsystem;
    private int degrees;

    public LimeLightSeek(LimeLightSubsystem limeLightSubsystem) {
        this.limeLightSubsystem = limeLightSubsystem;
    }

    @Override
    public void initialize() {
        if (isFinished())
            return;

        limeLightSubsystem.setPipeline(0);

        degrees = 50;
    }

    @Override
    public void execute() {
        if (isFinished())
            return;

        limeLightSubsystem.setServoAngle(degrees);
        degrees++;
        if (degrees > 95) {
            degrees = 50;
        }
    }

    @Override
    public boolean isFinished() {
        return limeLightSubsystem.hasTarget();
    }

}