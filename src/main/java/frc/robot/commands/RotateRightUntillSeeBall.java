package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import frc.robot.NavXHandler;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class RotateRightUntillSeeBall extends CommandBase {
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private LimeLightSubsystem limeLightSubsystem;
    // private NavXHandler navXHandler;
    private boolean isFinished = false;
    private double newDirection;
    private double margin = 0.05; // margin of degrees
    private PIDController angleController;
    private int pipeline;
    private static double initialDegrees;

    public RotateRightUntillSeeBall(SwerveDriveSubsystem swerveDriveSubsystem, LimeLightSubsystem limeLightSubsystem,
            int pipeline) {

        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.limeLightSubsystem = limeLightSubsystem;
        addRequirements(swerveDriveSubsystem, limeLightSubsystem);
        this.pipeline = pipeline;

    }

    @Override
    public void execute() {
        double rotateCommand;
        if (!limeLightSubsystem.hasTarget()) {
            rotateCommand = -0.2;
            swerveDriveSubsystem.drive(0, 0, rotateCommand * -1);
        }

        if (limeLightSubsystem.hasTarget()) {
            double currentAngle = limeLightSubsystem.getTxRad();
            rotateCommand = angleController.calculate(currentAngle) * -1;

            if (rotateCommand > 0.4) {
                rotateCommand = 0.4;
            } else if (rotateCommand < -0.4) {
                rotateCommand = -0.4;
            }
            SmartDashboard.putNumber("ROTATE", rotateCommand);
            swerveDriveSubsystem.drive(0, 0, rotateCommand);
            if (Math.abs(limeLightSubsystem.getTxRad()) < margin) {
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
        angleController = new PIDController(.3, 0, 0);
        angleController.setSetpoint(0);
    }

    @Override
    public void end(boolean interrupted) {
        swerveDriveSubsystem.drive(0, 0, 0);
    }
}