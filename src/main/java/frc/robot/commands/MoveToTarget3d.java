package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.NavXHandler;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class MoveToTarget3d extends CommandBase {

    private SwerveDriveSubsystem swerveDriveSubsystem;
    private LimeLightSubsystem limeLightSubsystem;
    private PIDController strafeController, distController, aimController;
    private NavXHandler navX;
    private double x, z;

    private double currentAngle = -10000;

    private PIDController angleController;

    public MoveToTarget3d(SwerveDriveSubsystem swerveDriveSubsystem, LimeLightSubsystem limeLightSubsystem,
            NavXHandler navx, double x, double z) {
        this.limeLightSubsystem = limeLightSubsystem;
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.x = x;
        this.z = z;
        this.navX = navx;

        addRequirements(limeLightSubsystem, swerveDriveSubsystem);
    }

    @Override
    public void execute() {
        currentAngle = ((navX.getAngleRad() % (2 * Math.PI) + (2 * Math.PI)) % (2 * Math.PI));
        double rotateCommand = angleController.calculate(currentAngle);

        limeLightSubsystem.setPipeline(0);
        double strafeCommand = strafeController.calculate(limeLightSubsystem.getTx());
        double distanceCommand = distController.calculate(limeLightSubsystem.getZ());
        double aimCommand = aimController.calculate(limeLightSubsystem.getTx());
        SmartDashboard.putNumber("distance from thing", limeLightSubsystem.getZ());
        if (distanceCommand > 0.5) {
            distanceCommand = 0.5;
        } else if (distanceCommand < -0.5) {
            distanceCommand = -0.5;
        }

        if (rotateCommand > 0.1) {
            rotateCommand = 0.1;
        } else if (rotateCommand < -0.1) {
            rotateCommand = -0.1;
        }

        if (limeLightSubsystem.hasTarget())
            swerveDriveSubsystem.drive(distanceCommand * -1, strafeCommand + rotateCommand * -1, aimCommand);
    }

    @Override
    public void initialize() {

        angleController = new PIDController(.3, 0, 0);
        angleController.enableContinuousInput(0, Math.PI * 2);
        angleController.setSetpoint(navX.getInitialAngle());

        limeLightSubsystem.setPipeline(0);
        limeLightSubsystem.setServoAngle(70);

        strafeController = new PIDController(.01, 0, 0);
        distController = new PIDController(.015, 0, 0);
        aimController = new PIDController(.016, 0, 0);

        strafeController.setSetpoint(x);
        distController.setSetpoint(z);
        aimController.setSetpoint(0);

    }

    @Override
    public boolean isFinished() {
        // if (Math.abs(strafeController.getPositionError()) < 5 &&
        // Math.abs(distController.getPositionError()) < 3
        // && Math.abs(aimController.getPositionError()) < 2) {
        if (Math.abs(strafeController.getPositionError()) < 1 && Math.abs(distController.getPositionError()) < 1
                && Math.abs(currentAngle - navX.getInitialAngle()) < 0.1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        swerveDriveSubsystem.drive(0, 0, 0);
    }
}