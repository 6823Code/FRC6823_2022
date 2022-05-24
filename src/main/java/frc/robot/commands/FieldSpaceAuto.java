package frc.robot.commands;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.JoystickHandler;
import frc.robot.NavXHandler;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class FieldSpaceAuto extends CommandBase {
    //Declare subsystem, Joystick Handler, NavX
    private SwerveDriveSubsystem swerveDrive;
    private NavXHandler navXHandler;
    private double speedRate;
    private double turnRate;

    private double fieldAngle = 0; //Angle of away from driver from zero

    public FieldSpaceAuto(SwerveDriveSubsystem subsystem, 
    Input[] inputs, NavXHandler navXHandler) {
        //Instantiate subsystem, Joystick Handler, NavX
        this.swerveDrive = subsystem;
        this.navXHandler = navXHandler;
        speedRate = 0.5; //change to recording 
        turnRate = 0.5; //change to recording
        addRequirements(swerveDrive);
    }

    @Override
    public void execute() {
        for(int i = 0; i < inputs.length; i++){
            Input currentInput = inputs[i];
            navXHandler.printEverything();

        //Set xval, yval, spinval to the scaled values from the joystick, bounded on [-1, 1]
        double xval = Math.max(Math.min(currentInput.getAxis0() * -speedRate, 1), -1);
        double yval = Math.max(Math.min(currentInput.getAxis1() * speedRate, 1), -1);
        double spinval = Math.max(Math.min(currentInput.getAxis5() * turnRate, 1), -1);
        double robotAngle = navXHandler.getAngleRad() - fieldAngle;

        // mapping field space to robot space
        double txval = getTransX(xval, yval, robotAngle);
        double tyval = getTransY(xval, yval, robotAngle);

        swerveDrive.drive(txval, tyval, spinval);
        }
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
