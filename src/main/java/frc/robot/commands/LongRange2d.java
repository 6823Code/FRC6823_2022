package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
// import edu.wpi.first.wpilibj2.command.CommandScheduler;
// import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
//import frc.robot.Robot;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class LongRange2d extends CommandBase {

    private SwerveDriveSubsystem swerveDriveSubsystem;
    private LimeLightSubsystem limeLightSubsystem;
    //private ShooterSubsystem shooterSubsystem;
    private PIDController aimController;

    private LongRange2dAutoShoot.DoubleContainer rpm;
    private int pipeline;

    public LongRange2d(SwerveDriveSubsystem swerveDriveSubsystem, LimeLightSubsystem limeLightSubsystem,
            ShooterSubsystem shooterSubsystem, LongRange2dAutoShoot.DoubleContainer rpm, int pipeline) {
        this.limeLightSubsystem = limeLightSubsystem;
        this.swerveDriveSubsystem = swerveDriveSubsystem;
        //this.shooterSubsystem = shooterSubsystem;

        this.rpm = rpm;
        this.pipeline = pipeline;

        aimController = new PIDController(.02, 0, 0);

        this.addRequirements(limeLightSubsystem, swerveDriveSubsystem);
    }

    @Override
    public void execute() {
        if (!limeLightSubsystem.hasTarget())
            return;

        double aimCommand = aimController.calculate(limeLightSubsystem.getTx());

        swerveDriveSubsystem.drive(0, 0, aimCommand * -1);

        limeLightSubsystem.setServoAngle(limeLightSubsystem.getServoAngle() + .05 * limeLightSubsystem.getTy());
    }

    @Override
    public void initialize() {
        limeLightSubsystem.setPipeline(this.pipeline);
        aimController.setSetpoint(0);
    }

    @Override
    public boolean isFinished() {
        return limeLightSubsystem.hasTarget()
                && (Math.abs(aimController.getPositionError()) < 1 && Math.abs(limeLightSubsystem.getTy()) < 1.25);
    }

    @Override
    public void end(boolean interrupted) {

        System.out.println("ending longrange2d command");

        swerveDriveSubsystem.drive(0, 0, 0);
        //int rpm = (int) Robot.PREFS.getDouble("RPMControl", 0);
        double t = limeLightSubsystem.getServoAngle();
        // int rpm = (int) (.0233 * Math.pow(t, 4) - 189.39 * Math.pow(t, 3) + 16304 * t
        // * t - 622601 * t
        // + 9 * Math.pow(10, 6));// (int) limeLightSubsystem.getServoAngle(); // PUT
        // FUNCTION
        // HERE
        if (this.rpm != null)
            this.rpm.value = rpmFromAngle(t);
        // CommandScheduler.getInstance().schedule(new Shoot(shooterSubsystem, rpm, .35,
        // 5));
        // new JustAim(swerveDriveSubsystem, limeLightSubsystem)));
    }

    private double rpmFromAngle(double angle) {
        return 1.863e6 * Math.pow(Math.E, -.1652 * angle) + 5975;
    }
}