package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SwitchBetweenWeirdAndNormal extends CommandBase {
    private boolean isNormal = true;
    private boolean isFinished = false;

    public SwitchBetweenWeirdAndNormal() {

    }

    public boolean getisNormal() {
        return isNormal;
    }

    @Override
    public void execute() {
        isNormal = !isNormal;
        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void end(boolean interrupted) {
        isFinished = false;
    }
}