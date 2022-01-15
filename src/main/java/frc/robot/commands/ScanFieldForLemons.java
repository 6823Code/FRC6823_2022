package frc.robot.commands;

//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class ScanFieldForLemons extends CommandBase {

    private LimeLightSubsystem limeLightSubsystem;
    //private Timer timer;
    //private boolean timerOn = false;
    private boolean isFinished = false;
    private double angleToLemon;

    public ScanFieldForLemons(LimeLightSubsystem limeLightSubsystem) {
        this.limeLightSubsystem = limeLightSubsystem;
        addRequirements(limeLightSubsystem);
    }

    @Override
    public void execute() {

        if (limeLightSubsystem.hasTarget()) {
            angleToLemon = limeLightSubsystem.getTx();
            isFinished = true;
            AutoCommandGroup.setAngleToLemon(angleToLemon);
        }

    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    public double getAngleToLemon() {
        return angleToLemon;
    }

    @Override
    public void initialize() {
        limeLightSubsystem.setPipeline(1);
    }

    @Override
    public void end(boolean interrupted) {
    }
}
