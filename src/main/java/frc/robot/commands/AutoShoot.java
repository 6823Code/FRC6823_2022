package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class AutoShoot extends CommandBase {
    private ShooterSubsystem shooter;
    private boolean isFinished;

    public AutoShoot(ShooterSubsystem shooter) {
        //Instantiate subsystem, Joystick Handler
        this.shooter = shooter;
        isFinished = false;

        addRequirements(this.shooter);
    }

    @Override
    public void execute() {
        double aimRate = 0;
        double loadRate = 0.6;
        int shootRate = 3000;
        shooter.prep(aimRate, loadRate);
        shooter.shoot(shootRate);
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