package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLightSubsystem;

public class SwitchPipelineCommand extends CommandBase {
    //Declare subsystem, isFinished, and intended pipeline
    private LimeLightSubsystem limeLightSubsystem;
    private boolean isFinished = false;
    private int pipeline;

    public SwitchPipelineCommand(LimeLightSubsystem limeLightSubsystem, int pipeline) {
        //Instantiate subsystem, isFinished, and intended pipeline
        this.limeLightSubsystem = limeLightSubsystem;
        this.pipeline = pipeline;
        limeLightSubsystem.setPipeline(pipeline);
    }

    @Override
    public void execute() {
        limeLightSubsystem.setPipeline(pipeline);
    }

    @Override
    public void initialize() {
        limeLightSubsystem.setPipeline(pipeline);
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void end(boolean inturrupted) {
        isFinished = false;
    }
}