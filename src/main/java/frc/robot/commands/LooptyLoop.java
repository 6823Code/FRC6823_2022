package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.NavXHandler;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class LooptyLoop extends CommandBase {
    private double initialAngle;
    private double finalAngle;
    private double distanceToMaintain = 40;
    private int direction = 1;// -1 = clockwise, 1 = counterclockwise

    private PIDController centerTarget;
    private PIDController maintainDistance;

    private LimeLightSubsystem limelight;
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private NavXHandler navX;

    private int pipeline;

    private boolean hasPassedZero = false;

    private double startAngle;
    private double stopAngle;

    public LooptyLoop(SwerveDriveSubsystem swerveDriveSubsystem, LimeLightSubsystem limeLightSubsystem, int pipeline,
            NavXHandler navX, int direction) {

        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.limelight = limeLightSubsystem;
        this.navX = navX;
        addRequirements(swerveDriveSubsystem, limeLightSubsystem);
        this.pipeline = pipeline;

        this.startAngle = startAngle;
        this.stopAngle = stopAngle;

        this.direction = direction;

    }

    @Override
    public void execute() {
        double strafeDirection = direction * 0.3;
        double rotateDirection = centerTarget.calculate(limelight.getTxRad());
        double distanceNow = EstimateDistance.getDistance(0, limelight.getServoAngleFromGroundRad(),
                limelight.getTyRad());
        double zDirection = maintainDistance.calculate(distanceNow);

        SmartDashboard.putNumber("Distance Loop", distanceNow);
        if (zDirection > 0.5) {
            zDirection = 0.5;
        }
        if (zDirection < -0.5) {
            zDirection = -0.5;
        }

        if (Math.abs(distanceNow - distanceToMaintain) < 7) {
            swerveDriveSubsystem.drive(zDirection, strafeDirection, rotateDirection * -1);

        } else {
            swerveDriveSubsystem.drive(zDirection, 0, rotateDirection * -1);
        }
    }

    @Override
    public void initialize() {
        limelight.setPipeline(pipeline);
        if (pipeline == 0) {
            limelight.setServoAngle(50);
        } else if (pipeline == 1) {
            limelight.setServoAngle(15);
        }

        centerTarget = new PIDController(0.8, 0, 0);
        centerTarget.setSetpoint(0);

        maintainDistance = new PIDController(0.015, 0, 0);
        maintainDistance.setSetpoint(distanceToMaintain);

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        swerveDriveSubsystem.drive(0, 0, 0);
    }
}