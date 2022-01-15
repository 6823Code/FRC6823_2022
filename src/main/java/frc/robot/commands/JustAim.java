package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class JustAim extends CommandBase {

    private SwerveDriveSubsystem swerveDriveSubsystem;
    private LimeLightSubsystem limeLightSubsystem;
    private PIDController aimController;

    public JustAim(SwerveDriveSubsystem swerveDriveSubsystem, LimeLightSubsystem limeLightSubsystem) {
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.limeLightSubsystem = limeLightSubsystem;
    }

    @Override
    public void execute() {
        double aimCommand = aimController.calculate(limeLightSubsystem.getTx());

        swerveDriveSubsystem.drive(0, 0, aimCommand * -1);
    }

    @Override
    public void initialize() {
        limeLightSubsystem.setPipeline(0);
        aimController = new PIDController(.016, 0, 0);
        aimController.setSetpoint(0);

    }
}