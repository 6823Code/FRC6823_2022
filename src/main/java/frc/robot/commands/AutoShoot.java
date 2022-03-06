package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;

public class AutoShoot extends CommandBase {
    private ShooterSubsystem shooter;
    private ConveyorSubsystem conveyor;
    //private double aimRate;
    private double loadRate;
    private int leftShootRate;
    private int rightShootRate;
    private boolean isFinished;

    public AutoShoot(ShooterSubsystem shooter, ConveyorSubsystem conveyor, double aimRate, double loadRate, int leftShootRate, int rightShootRate) {
        //Instantiate subsystem, Joystick Handler
        this.shooter = shooter;
        this.conveyor = conveyor;
        //this.aimRate = aimRate;
        this.loadRate = loadRate;
        this.leftShootRate = leftShootRate;
        this.rightShootRate = rightShootRate;
        isFinished = false;

        addRequirements(this.shooter);
    }

    @Override
    public void execute() {
        shooter.prep(loadRate);
        shooter.shoot(-50 * leftShootRate, -50 * rightShootRate);
        conveyor.convey();
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