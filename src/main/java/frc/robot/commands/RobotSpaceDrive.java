package frc.robot.commands;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.JoystickHandler;

import frc.robot.subsystems.SwerveDriveSubsystem;

public class RobotSpaceDrive extends CommandBase {
    // The subsystem the command runs on
    private final SwerveDriveSubsystem swerveDrive;
    private JoystickHandler joystickHandler;

    public RobotSpaceDrive(SwerveDriveSubsystem subsystem, JoystickHandler joystickHandler) {
        this.swerveDrive = subsystem;
        this.joystickHandler = joystickHandler;

        addRequirements(swerveDrive);
    }

    @Override
    public void execute() {
        double speedRate = Preferences.getDouble("SpeedRate", 0.3);
        double turnRate = Preferences.getDouble("TurnRate", 0.1);

        double xval = joystickHandler.getAxis1() * speedRate;
        double yval = joystickHandler.getAxis0() * speedRate;
        double spinval = joystickHandler.getAxis5() * turnRate;

        swerveDrive.drive(xval, yval, spinval);// zoooooom
    }
}
