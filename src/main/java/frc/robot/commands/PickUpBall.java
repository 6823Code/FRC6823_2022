package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class PickUpBall extends CommandBase {
    private SwerveDriveSubsystem swerveDriveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private LimeLightSubsystem limeLightSubsystem;

    private PIDController distController, aimController;

    private long whenStartedGorging;
    private int stage = -1; // -1= never started before, 0=noteating, 1=spinningwithoutball, 2=chomping
    private boolean isItFinished = false;

    public PickUpBall(SwerveDriveSubsystem swerveDriveSubsystem, IntakeSubsystem intakeSubsystem,
            LimeLightSubsystem limeLightSubsystem) {

        this.swerveDriveSubsystem = swerveDriveSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        this.limeLightSubsystem = limeLightSubsystem;

        stage = 0;

        addRequirements(swerveDriveSubsystem, intakeSubsystem, limeLightSubsystem);
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
                intakeSubsystem.intake();
            }
            if (!hasSeenBall) {
                isItFinished = true;
            }
        } else if (stage == 1) {
            // close to ball, move towards it despite not seeing it
            swerveDriveSubsystem.drive(-.6, 0, 0);

            // stop after 2 seconds
            if (System.currentTimeMillis() - whenStartedGorging > 4500) {
                isItFinished = true;
                intakeSubsystem.stopIntake();
                swerveDriveSubsystem.stop();

            }
        }
    }

    @Override
    public void initialize() {
        limeLightSubsystem.setPipeline(1);
        aimController = new PIDController(.016, 0, 0);
        distController = new PIDController(.05, 0, 0);

        distController.setSetpoint(0);
        aimController.setSetpoint(0);

        stage = 0;
    }

    @Override
    public boolean isFinished() {
        return isItFinished;
    }

    @Override
    public void end(boolean interrupted) {
        swerveDriveSubsystem.stop();
        intakeSubsystem.stopIntake();
        stage = 0;
        isItFinished = false;
        hasSeenBall = false;
    }
}