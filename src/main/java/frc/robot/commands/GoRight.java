package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.NavXHandler;
import frc.robot.subsystems.*;

public class GoRight extends CommandBase {
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private boolean isFinished = false;
    private static double fieldAngle;
    private double startTime;
    private boolean timerStarted = false;
    private NavXHandler navXHandler;
    private double time;

    public GoRight(SwerveDriveSubsystem swerveDriveSubsystem, double time, NavXHandler navXHandler) {
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.time = time;

        addRequirements(swerveDriveSubsystem);
    }

    @Override
    public void execute() {
        double amplitude = 0.2;

        double robotAngle = navXHandler.getAngleRad() - fieldAngle;

        double xval = 1;
        double yval = 0;
        // mapping field space to robot space
        double txval = getTransX(xval, yval, robotAngle) * amplitude;
        double tyval = getTransY(xval, yval, robotAngle) * amplitude;

        swerveDriveSubsystem.drive(txval, tyval, 0);// zoooooom

        if (!timerStarted) {
            startTime = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - startTime > time * 1000.0) {
            isFinished = true;

        }

    }

    private double getTransX(double x, double y, double angle) {
        return x * Math.cos(angle) + -y * Math.sin(angle);
    }

    private double getTransY(double x, double y, double angle) {
        return x * Math.sin(angle) + y * Math.cos(angle);
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void initialize() {

    }

    public static void zero(double angle) {
        fieldAngle = angle;
    }

    @Override
    public void end(boolean interrupted) {
        swerveDriveSubsystem.drive(0, 0, 0);
    }
}
