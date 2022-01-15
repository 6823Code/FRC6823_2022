package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.NavXHandler;
import frc.robot.subsystems.*;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.math.controller.PIDController;

public class GoForward extends CommandBase {
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private boolean isFinished = false;
    private double direction;
    private Timer timer;

    private NavXHandler navX;

    private static double initialAngle = 0;

    public GoForward(SwerveDriveSubsystem swerveDriveSubsystem, NavXHandler navx) {
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        // direction is always forward in radians (right is 0, left is PI, forward is
        // Pi/2. back is 3PI/2)
        this.direction = Math.PI / 2.0;
        addRequirements(swerveDriveSubsystem);
        timer = new Timer();

        this.navX = navx;

    }

    @Override
    public void execute() {

        double xval = -0.9;
        double yval = 0;
        double spinval = 0;

        double robotAngle = navX.getAngleRad() - initialAngle;

        // mapping field space to robot space
        double txval = getTransX(xval, yval, robotAngle);
        double tyval = getTransY(xval, yval, robotAngle);

        swerveDriveSubsystem.drive(txval, tyval, spinval);// zoooooom

        // swerveDriveSubsystem.weirdDrive(-1, 0, navX.getAngleRad());
        if (timer.hasPeriodPassed(1.5)) {

            isFinished = true;
        }

    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void initialize() {
        this.swerveDriveSubsystem.setFieldAngle(this.navX.getAngleRad());

        timer.reset();
        timer.start();
    }

    @Override
    public void end(boolean interrupted) {
        isFinished = false;
        swerveDriveSubsystem.drive(0, 0, Math.PI / 2);
    }

    private double getTransX(double x, double y, double angle) {
        return x * Math.cos(angle) + -y * Math.sin(angle);
    }

    private double getTransY(double x, double y, double angle) {
        return x * Math.sin(angle) + y * Math.cos(angle);
    }

    public static void zero(NavXHandler navx) {
        initialAngle = navx.getAngleRad();
    }
}
