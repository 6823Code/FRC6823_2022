package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

/**
 * A Command class for searching for an object to the right using limelight.
 *
 * <p>This class uses limelight to search clockwise to center the view on a limelight target
 */
public class AutoSearchRight extends CommandBase {
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private LimeLightSubsystem limeLightSubsystem;
    private boolean isFinished = false;
    private double margin = 1; // margin of degrees
    private PIDController angleController;
    private int pipeline;

    /**
    * A constructor for the AutoSearchRight command.
    *
    * @param swerveDriveSubsystem the swerve drive subsystem that runs this command
    * @param limeLightSubsystem the limelight subsystem that this command reads from
    * @param pipeline the pipeline to use to search for a target
    */
    public AutoSearchRight(SwerveDriveSubsystem swerveDriveSubsystem, LimeLightSubsystem limeLightSubsystem,
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
        }else{
            double currentAngle = limeLightSubsystem.getTxRad();
            rotateCommand = angleController.calculate(currentAngle);

            if (rotateCommand > 0.4) {
                rotateCommand = 0.4;
            } else if (rotateCommand < -0.4) {
                rotateCommand = -0.4;
            }
            if (Math.abs(limeLightSubsystem.getTx()) < margin) {
                isFinished = true;
            }
        }
        swerveDriveSubsystem.drive(0, 0, rotateCommand);
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void initialize() {
        limeLightSubsystem.setPipeline(pipeline);
        angleController = new PIDController(1, 0, 0);
        angleController.setSetpoint(0);
    }

    @Override
    public void end(boolean interrupted) {
        swerveDriveSubsystem.stop();
    }
}