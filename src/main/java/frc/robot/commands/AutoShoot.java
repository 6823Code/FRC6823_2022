package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

/**
 * A Command class for shooting a game piece in autonomous.
 *
 * <p>This class does not automatically stop its subsystems after it ends
 */
public class AutoShoot extends CommandBase {
    private ShooterSubsystem shooter;
    private ConveyorSubsystem conveyor;
    private IntakeSubsystem intake;
    private double loadRate;
    private int leftShootRate;
    private int rightShootRate;
    private boolean isFinished;

    /**
    * A constructor for the AutoShoot command
    *
    * @param shooter the shooter subsystem to use to shoot
    * @param conveyor the conveyor subsystem carrying the game pieces to the shooter
    * @param intake the intake subsystem to intake the game pieces onto the conveyor
    * @param loadRate the rate (0-1) to run the feeder roller of the shooter
    * @param leftShootRate the rate (0-1) to run the left side of the shooter
    * @param rightShootRate the rate (0-1) to run the right side of the shooter
    */
    public AutoShoot(ShooterSubsystem shooter, ConveyorSubsystem conveyor, IntakeSubsystem intake, double loadRate, int leftShootRate, int rightShootRate) {
        //Instantiate subsystem, Joystick Handler
        this.shooter = shooter;
        this.conveyor = conveyor;
        this.intake = intake;
        this.loadRate = loadRate;
        this.leftShootRate = leftShootRate;
        this.rightShootRate = rightShootRate;
        isFinished = false;

        addRequirements(this.shooter);
    }

    @Override
    public void execute() {
        shooter.prep(loadRate);
        shooter.shoot(50 * leftShootRate, 50 * rightShootRate);
        intake.intake();
        conveyor.convey();
        shooter.setShooterAngle(70);
        isFinished = true;
    }

    @Override
    public void initialize() {

    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void end(boolean inturrupted) {
        isFinished = false;
    }
}