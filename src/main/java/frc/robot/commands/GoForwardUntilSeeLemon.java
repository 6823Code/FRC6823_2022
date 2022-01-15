package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class GoForwardUntilSeeLemon extends CommandBase {
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private LimeLightSubsystem limeLightSubsystem;
    private boolean isFinished = false;
    private double direction;

    public GoForwardUntilSeeLemon(SwerveDriveSubsystem swerveDriveSubsystem, LimeLightSubsystem limeLightSubsystem) {
        this.limeLightSubsystem = limeLightSubsystem;
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        // direction is always forward in radians (right is 0, left is PI, forward is
        // Pi/2. back is 3PI/2)
        this.direction = Math.PI / 2.0;
        addRequirements(swerveDriveSubsystem, limeLightSubsystem);
    }

    @Override
    public void execute() {
        double amplitude = 0.2;
        double xDirection = Math.cos(direction) * amplitude;
        double yDirection = Math.sin(direction) * amplitude;
        swerveDriveSubsystem.drive(yDirection, xDirection, 0);
        if (limeLightSubsystem.hasTarget()) {
            isFinished = true;
        }

    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void initialize() {
        limeLightSubsystem.setPipeline(1);
        limeLightSubsystem.setServoAngle(LimeLightSubsystem.towardsGround);
    }

    @Override
    public void end(boolean interrupted) {
        swerveDriveSubsystem.drive(0, 0, 0);
    }
}
