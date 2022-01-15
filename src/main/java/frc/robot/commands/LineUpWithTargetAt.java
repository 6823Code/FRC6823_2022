package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.NavXHandler;
import frc.robot.commands.EstimateDistance;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class LineUpWithTargetAt extends CommandBase {
    private double initialAngle;
    private double finalAngle;
    private double distanceToMaintain;

    private PIDController centerTarget;
    private PIDController maintainDistance;
    private PIDController strafePID;

    private LimeLightSubsystem limelight;
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private NavXHandler navX;

    private int pipeline;

    private boolean isFinished = false;

    private double targetAngle;

    public LineUpWithTargetAt(SwerveDriveSubsystem swerveDriveSubsystem, LimeLightSubsystem limeLightSubsystem,
            int pipeline, NavXHandler navX, double distance) {

        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.limelight = limeLightSubsystem;
        this.navX = navX;
        addRequirements(swerveDriveSubsystem, limeLightSubsystem);
        this.pipeline = pipeline;

        this.distanceToMaintain = distance;

    }

    @Override
    public void execute() {
        double angleNow = navX.getAngleRad();
        if (angleNow > Math.PI) {
            angleNow = angleNow % (2 * Math.PI) - 2 * Math.PI;
        }
        double strafeDirection = strafePID.calculate(angleNow);
        double rotateDirection = centerTarget.calculate(limelight.getTxRad());
        double distanceNow = EstimateDistance.getDistance(90, limelight.getServoAngleFromGroundRad(),
                limelight.getTyRad());
        double zDirection = maintainDistance.calculate(distanceNow);

        SmartDashboard.putNumber("Distance From the target", distanceNow);
        SmartDashboard.putNumber("ROTATE", angleNow);

        if (zDirection > 0.5) {
            zDirection = 0.5;
        } else if (zDirection < -0.5) {
            zDirection = -0.5;
        }
        if (strafeDirection > 0.5) {
            strafeDirection = 0.5;
        } else if (strafeDirection < -0.5) {
            strafeDirection = -0.5;
        }
        if (rotateDirection > 0.3) {
            rotateDirection = 0.3;
        } else if (rotateDirection < -0.3) {
            rotateDirection = -0.3;
        }

        SmartDashboard.putNumber("angleNow", angleNow);

        // if (Math.abs(distanceNow - distanceToMaintain) < 3 &&
        // Math.abs(limelight.getTxRad()) < 2) {
        // isFinished = true;
        // }

        if (Math.abs(angleNow) < 0.04 && Math.abs(limelight.getTxRad() + 0.01) < (0.01)
                && Math.abs(distanceNow - distanceToMaintain) < 2)
            isFinished = true;

        // // swerveDriveSubsystem.drive(0, 0, rotateDirection * -1);
        swerveDriveSubsystem.drive(zDirection, strafeDirection * -1, rotateDirection * -1);
        // swerveDriveSubsystem.drive(0, 0 * -1, rotateDirection * -1);

        // swerveDriveSubsystem.drive(0, strafeDirection * -1, rotateDirection * -1);

        // swerveDriveSubsystem.drive(0, 0 * -1, 0 * -1);
        // swerveDriveSubsystem.drive(0, strafeDirection * -1, 0 * -1);

    }

    @Override
    public void initialize() {
        targetAngle = navX.getInitialAngle();
        limelight.setPipeline(pipeline);
        if (pipeline == 0) {
            limelight.setServoAngle(70);
        } else if (pipeline == 1) {
            limelight.setServoAngle(15);
        }

        centerTarget = new PIDController(0.4, 0.05, 0.1);
        centerTarget.setSetpoint(0 - 0.01);
        // centerTarget.setSetpoint(0 - 0.04);

        maintainDistance = new PIDController(0.005, 0, 0);
        maintainDistance.setSetpoint(distanceToMaintain);

        strafePID = new PIDController(0.8, 0, 0.1);
        strafePID.setSetpoint(targetAngle);
        // strafePID.enableContinuousInput(0, Math.PI * 2);

    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void end(boolean interrupted) {
        isFinished = false;
        //swerveDriveSubsystem.stopMomentum();
    }
}