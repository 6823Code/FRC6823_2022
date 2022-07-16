package frc.robot.commands;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.JoystickHandler;
import frc.robot.NavXHandler;
import frc.robot.subsystems.SwerveDriveSubsystem;

/**
 * A Command class for driving the robot in field space mode
 *
 * <p>This command is run periodically in teleoperated mode.
 */
public class FieldSpaceDrive extends CommandBase {
    //Declare subsystem, Joystick Handler, NavX
    private SwerveDriveSubsystem swerveDrive;
    private JoystickHandler joystickHandler;
    private NavXHandler navXHandler;
    private SimpleWidget speedRateWidget;
    private SimpleWidget turnRateWidget;

    private double fieldAngle = 0; //Angle of away from driver from zero

    /**
    * A constructor for the FieldSpaceDrive class.
    *
    * @param subsystem the swerve drive subsystem to drive
    * @param joystickHandler the joystick to use as inputs
    * @param navxHandler the NavX to use to determine robot orientation
    */
    public FieldSpaceDrive(SwerveDriveSubsystem subsystem, 
    JoystickHandler joystickHandler, NavXHandler navXHandler) {
        //Instantiate subsystem, Joystick Handler, NavX, Widgets for spped rate and turn rate
        this.swerveDrive = subsystem;
        this.joystickHandler = joystickHandler;
        this.navXHandler = navXHandler;
        this.speedRateWidget = Shuffleboard.getTab("Preferences").addPersistent("Speed Rate", 0.5)
        .withWidget(BuiltInWidgets.kNumberSlider);
        this.turnRateWidget = Shuffleboard.getTab("Preferences").addPersistent("Turn Rate", 0.5)
        .withWidget(BuiltInWidgets.kNumberSlider);
        //adds the requirement that the swerve drive is
        addRequirements(swerveDrive);
    }

    @Override
    public void execute() {
        navXHandler.printEverything();
        joystickHandler.updateDeadZone();

        //Set speed and turn rates for full throttle and not full throttle
        double speedRate = speedRateWidget.getEntry().getDouble(0.5);
        double turnRate = turnRateWidget.getEntry().getDouble(0.5);

        //Set xval, yval, spinval to the scaled values from the joystick, bounded on [-1, 1]
        double xval = Math.max(Math.min(joystickHandler.getAxis0() * -speedRate, 1), -1);
        double yval = Math.max(Math.min(joystickHandler.getAxis1() * speedRate, 1), -1);
        double spinval = Math.max(Math.min(joystickHandler.getAxis5() * turnRate, 1), -1);

        double robotAngle = navXHandler.getAngleRad() - fieldAngle;

        // mapping field space to robot space
        double txval = getTransX(xval, yval, robotAngle);
        double tyval = getTransY(xval, yval, robotAngle);

        swerveDrive.drive(txval, tyval, spinval);
    }

    private double getTransX(double x, double y, double angle) { //Returns x direction robot needs to move in
        return x * Math.cos(angle) + -y * Math.sin(angle);
    }

    private double getTransY(double x, double y, double angle) { //Returns y direction robot needs to move in
        return x * Math.sin(angle) + y * Math.cos(angle);
    }

    public void zero() { //Zeroes direction
        this.fieldAngle = navXHandler.getAngleRad(); // + Math.PI
        swerveDrive.setFieldAngle(fieldAngle);
    }
}
