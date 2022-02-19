package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.JoystickHandler;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class Shoot extends CommandBase {
    private ShooterSubsystem shooter;
    private ConveyorSubsystem conveyor;
    private JoystickHandler joystickHandler;

    public Shoot(ShooterSubsystem shooter, ConveyorSubsystem conveyor,
    JoystickHandler joystickHandler) {
        //Instantiate subsystem, Joystick Handler
        this.shooter = shooter;
        this.conveyor = conveyor;
        this.joystickHandler = joystickHandler;

        addRequirements(this.shooter);
    }

    @Override
    public void execute() {
        double aimRate = joystickHandler.getAxis1();
        if (aimRate > 0){
            aimRate *= shooter.getShooterAnglePercentForward();
        }else{
            aimRate *= shooter.getShooterAnglePercentBack();
        }
        double loadRate;
        int shootRate;
        if(joystickHandler.getAxis3() != 0){
            loadRate = shooter.getLoadPercent();
            shootRate = shooter.getShooterRPM()*20;
            //shootRate = 1.0;
            conveyor.convey();
        }else if (joystickHandler.getAxis2() != 0){
            loadRate = 0;
            shootRate = 0;
            conveyor.convey();
        }else{
            loadRate = 0;
            shootRate = 0;
            conveyor.stopConvey();
        }
        shooter.prep(aimRate, loadRate);
        shooter.shoot(shootRate);
    }

    public void zero() { //Zeroes direction

    }
}