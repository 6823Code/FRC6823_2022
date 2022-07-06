package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class AutoSearchRightTeleOp extends CommandBase {
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private LimeLightSubsystem limeLightSubsystem;
    private boolean isFinished = false;
    // private double margin = 1; // margin of degrees
    private PIDController angleController;
    private int pipeline;

    public AutoSearchRightTeleOp(SwerveDriveSubsystem swerveDriveSubsystem, LimeLightSubsystem limeLightSubsystem,
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

            // if (rotateCommand > 0.4) {
            //     rotateCommand = 0.4;
            // } else if (rotateCommand < -0.4) {
            //     rotateCommand = -0.4;
            // }
            
            //rotateCommand = -limeLightSubsystem.getTxRad();
        }
        swerveDriveSubsystem.drive(0, 0, rotateCommand);
        isFinished = true;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void initialize() {
        limeLightSubsystem.setPipeline(pipeline);
        angleController = new PIDController(1, 0.001, 0);
        angleController.setSetpoint(0);
    }

    @Override
    public void end(boolean interrupted) {
        // swerveDriveSubsystem.stop();
    }
}