package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.NavXHandler;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class RotateToZero extends CommandBase {
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private NavXHandler navXHandler;
    private boolean isFinished = false;
    private double margin = 0.1; // margin of Radians
    private PIDController angleController;
    public static final double kToleranceDegrees = 2.0f;

    private static double initialDegrees;

    public RotateToZero(SwerveDriveSubsystem swerveDriveSubsystem, NavXHandler navXHandler) {

        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.navXHandler = navXHandler;
        addRequirements(swerveDriveSubsystem);

    }

    public static void setInitialAngle(double angle) {
        RotateToZero.initialDegrees = ((angle % (2 * Math.PI) + (2 * Math.PI)) % (2 * Math.PI));
    }

    @Override
    public void execute() {
        double currentAngle = ((navXHandler.getAngleRad() % (2 * Math.PI) + (2 * Math.PI)) % (2 * Math.PI));
        double rotateCommand = angleController.calculate(currentAngle);

        if (rotateCommand > 0.4) {
            rotateCommand = 0.4;
        } else if (rotateCommand < -0.4) {
            rotateCommand = -0.4;
        }

        SmartDashboard.putNumber("ROTATE", rotateCommand);
        swerveDriveSubsystem.drive(0, 0, rotateCommand);
        if (Math.abs(rotateCommand) < 0.05) {
            isFinished = true;
        }

    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void initialize() {
        angleController = new PIDController(.3, 0, 0);
        angleController.enableContinuousInput(0, Math.PI * 2);
        angleController.setSetpoint(initialDegrees);
    }

    @Override
    public void end(boolean interrupted) {
        swerveDriveSubsystem.drive(0, 0, 0);
        isFinished = false;
    }

}
