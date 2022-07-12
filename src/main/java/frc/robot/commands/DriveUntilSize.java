package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class DriveUntilSize extends CommandBase {
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private LimeLightSubsystem limeLightSubsystem;

    private PIDController aimController;
    private PIDController distController;

    private int pipeline;
    private boolean isItFinished;
    private final double SIZE = 0.9; //in % of picture

    public DriveUntilSize(SwerveDriveSubsystem swerveDriveSubsystem,
            LimeLightSubsystem limeLightSubsystem, int pipeline) {

        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.limeLightSubsystem = limeLightSubsystem;

        this.pipeline = pipeline;
        isItFinished = false;

        addRequirements(swerveDriveSubsystem, limeLightSubsystem);
    }

    @Override
    public void execute() {
        double aimCommand = aimController.calculate(limeLightSubsystem.getTx());
        double distCommand = distController.calculate(limeLightSubsystem.getTy());

        limeLightSubsystem.setPipeline(pipeline);

        if(limeLightSubsystem.hasTarget()){
            swerveDriveSubsystem.drive(0, -distCommand, aimCommand * -1);
        }else{
            swerveDriveSubsystem.stop();
        }

        if (limeLightSubsystem.getTa() > SIZE){
            isItFinished = true;
        }
    }

    @Override
    public void initialize() {
        aimController = new PIDController(.016, 0, 0);
        aimController.setSetpoint(0);
        distController = new PIDController(.016, 0, 0);
        distController.setSetpoint(0);
    }

    @Override
    public boolean isFinished() {
        return isItFinished;
    }

    @Override
    public void end(boolean interrupted) {
        swerveDriveSubsystem.stop();
        isItFinished = false;
    }
}