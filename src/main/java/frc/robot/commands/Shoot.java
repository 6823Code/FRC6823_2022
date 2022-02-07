package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.JoystickHandler;
import frc.robot.subsystems.ShooterSubsystem;

public class Shoot extends CommandBase {
    private ShooterSubsystem shooter;
    private JoystickHandler joystickHandler;

    public Shoot(ShooterSubsystem subsystem, 
    JoystickHandler joystickHandler) {
        //Instantiate subsystem, Joystick Handler
        this.shooter = subsystem;
        this.joystickHandler = joystickHandler;

        addRequirements(shooter);
    }

    @Override
    public void execute() {
        double aimRate = joystickHandler.getAxis1()*0.5;
        double loadRate;
        double shootRate;
        if(joystickHandler.getAxis3() != 0){
            loadRate = 0.5;
            shootRate = 0.75;
        }else{
            loadRate = 0;
            shootRate = 0;
        }
        shooter.prep(aimRate, loadRate);
        shooter.shoot(shootRate);
    }

    public void zero() { //Zeroes direction

    }
}