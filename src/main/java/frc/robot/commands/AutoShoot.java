package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ConveyorSubsystem;

public class AutoShoot extends CommandBase {
    private ShooterSubsystem shooter;
    private ConveyorSubsystem conveyor;
    private double aimRate;
    private double loadRate;
    private int shootRate;
    private boolean isFinished;

    public AutoShoot(ShooterSubsystem shooter, ConveyorSubsystem conveyor, double aimRate, double loadRate, int shootRate) {
        //Instantiate subsystem, Joystick Handler
        this.shooter = shooter;
        this.conveyor = conveyor;
        this.aimRate = aimRate;
        this.loadRate = loadRate;
        this.shootRate = shootRate;
        isFinished = false;

        addRequirements(this.shooter);
    }

    @Override
    public void execute() {
        shooter.prep(aimRate, loadRate);
        shooter.shoot(shootRate);
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