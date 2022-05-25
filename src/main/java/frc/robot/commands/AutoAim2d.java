package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class AutoAim2d extends CommandBase { //Not currently used?
    //Defining variables for subsystems, command state, margin, theta x, and limelight pipeline
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private LimeLightSubsystem limeLightSubsystem;
    private boolean isFinished = false;
    private final double MARGIN = 0.05; // margin of degrees
    private double tX;
    private int pipeline;

    public AutoAim2d(SwerveDriveSubsystem swerveDriveSubsystem, LimeLightSubsystem limeLightSubsystem,
            int pipeline) {
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.limeLightSubsystem = limeLightSubsystem;
        addRequirements(swerveDriveSubsystem, limeLightSubsystem);
        this.pipeline = pipeline;
        tX = 0;
    }

    @Override
    public void execute() {
        if (!limeLightSubsystem.hasTarget() && tX == 0) {
            double rotateCommand = -0.2;
            swerveDriveSubsystem.drive(0, 0, rotateCommand);
        }

        if (limeLightSubsystem.hasTarget()) {
            double currentAngle = limeLightSubsystem.getTxRad();
            if (currentAngle != 0){
                tX = currentAngle / Math.PI;
            }
            swerveDriveSubsystem.drive(0, 0, tX);
            if (Math.abs(currentAngle) < MARGIN) {
                isFinished = true;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void initialize() {
        limeLightSubsystem.setPipeline(pipeline);
    }

    @Override
    public void end(boolean interrupted) {
        swerveDriveSubsystem.stop();
    }
}