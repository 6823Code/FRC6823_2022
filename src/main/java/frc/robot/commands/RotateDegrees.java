package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.NavXHandler;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class RotateDegrees extends CommandBase {
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private NavXHandler navXHandler;
    private boolean isFinished = false;
    private double newDirection;
    private double margin = 0.5; // margin of degrees
    private PIDController angleController;

    //private static double initialDegrees;

    public RotateDegrees(SwerveDriveSubsystem swerveDriveSubsystem, NavXHandler navXHandler, double newDirection) {

        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.navXHandler = navXHandler;
        this.newDirection = newDirection;
        addRequirements(swerveDriveSubsystem);

    }

    public static void setInitialDegrees(double initialDegrees) {
        //RotateDegrees.initialDegrees = initialDegrees;
    }

    @Override
    public void execute() {
        double currentAngle = navXHandler.getAngle();
        double rotateCommand = angleController.calculate(currentAngle);

        if (rotateCommand > 0.2) {
            rotateCommand = 0.2;
        } else if (rotateCommand < -0.2) {
            rotateCommand = -0.2;
        }

        swerveDriveSubsystem.drive(0, 0, rotateCommand * -1);
        if (Math.abs(currentAngle - newDirection) < margin) {
            isFinished = true;
        }

    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void initialize() {
        angleController = new PIDController(.016, 0, 0);
        angleController.setSetpoint(newDirection);
    }

    @Override
    public void end(boolean interrupted) {
        swerveDriveSubsystem.drive(0, 0, 0);
    }

}
