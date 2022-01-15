package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
//import edu.wpi.first.util.sendable.SendableRegistry;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class LimeLightPickupBall extends CommandBase {
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private ShooterSubsystem shooterSubsystem;
    private LimeLightSubsystem limeLightSubsystem;

    private PIDController distController, aimController;
    private double y;

    private long whenStartedGorging;
    private int stage = -1; // -1= never started before, 0=noteating, 1=spinningwithoutball, 2=chomping
    private boolean isItFinished = false;

    public LimeLightPickupBall(SwerveDriveSubsystem swerveDriveSubsystem, ShooterSubsystem shooterSubsystem,
            LimeLightSubsystem limeLightSubsystem, double y) {

        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.shooterSubsystem = shooterSubsystem;
        this.limeLightSubsystem = limeLightSubsystem;

        this.y = y;
        stage = 0;

        addRequirements(swerveDriveSubsystem, shooterSubsystem, limeLightSubsystem);
    }

    public int getStage() {
        return stage;
    }

    private boolean hasSeenBall = false;

    @Override
    public void execute() {
        stage = stage == -1 ? 0 : stage;

        double distanceCommand = distController.calculate(limeLightSubsystem.getTy());
        double aimCommand = aimController.calculate(limeLightSubsystem.getTx());

        limeLightSubsystem.setPipeline(1);
        limeLightSubsystem.setServoAngle(15);
        SmartDashboard.putNumber("Ball Eat Stage", stage);
        SmartDashboard.putNumber("Ball Distance", distanceCommand);
        SmartDashboard.putNumber("Aim Command Ball", aimCommand);

        if (limeLightSubsystem.hasTarget()) {
            hasSeenBall = true;

        }
        if (stage == 0) {
            // far from ball, need to move towards it using limelight
            swerveDriveSubsystem.drive(distanceCommand, 0, aimCommand * -1);

            if (Math.abs(distController.getPositionError()) < 5 && hasSeenBall) {
                stage = 1;
                whenStartedGorging = System.currentTimeMillis();
                shooterSubsystem.startIntakeSpin();
            }
            if (!hasSeenBall) {
                isItFinished = true;
            }
        } else if (stage == 1) {
            // close to ball, move towards it despite not seeing it
            // if (!limeLightSubsystem.hasTarget()) {
            // } else {
            // }
            swerveDriveSubsystem.drive(-.6, 0, 0);

            if (shooterSubsystem.doesSenseBall() == true) {
                stage = 2;
                swerveDriveSubsystem.drive(0, 0, 0);
            }

            // stop after 2 seconds
            if (System.currentTimeMillis() - whenStartedGorging > 4500) {
                isItFinished = true;
                shooterSubsystem.stopIntakeSpin();

            }
        } else if (stage == 2) {
            // sensor has ball, eating it
            if (shooterSubsystem.doesSenseBall() == false) {
                shooterSubsystem.stopIntakeSpin();
                isItFinished = true;
            }
            // stop after 3 seconds
            if (System.currentTimeMillis() - whenStartedGorging > 4000) {
                isItFinished = true;
                shooterSubsystem.stopIntakeSpin();

            }
        }

    }

    @Override
    public void initialize() {
        limeLightSubsystem.setPipeline(1);
        aimController = new PIDController(.016, 0, 0);
        distController = new PIDController(.05, 0, 0);

        distController.setSetpoint(y);
        aimController.setSetpoint(0);

        stage = 0;
    }

    @Override
    public boolean isFinished() {
        return isItFinished;
        // if (Math.abs(distController.getPositionError()) < 5 &&
        // Math.abs(aimController.getPositionError()) < 2) {
        // return true;
        // } else {
        // return false;
        // }
    }

    @Override
    public void end(boolean interrupted) {
        swerveDriveSubsystem.drive(0, 0, 0);
        shooterSubsystem.stopIntakeSpin();
        stage = 0;
        isItFinished = false;
        hasSeenBall = false;
    }
}