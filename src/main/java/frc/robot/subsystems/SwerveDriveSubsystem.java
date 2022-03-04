package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDriveSubsystem extends SubsystemBase {
    /**
     * This subsystem does calculations to take controller inputs and
     * convert them into rotation and speed values for each motor (which is
     * controlled via the SwerveWheelModuleSubsystem class)
     * <p>
     * This code heavily attributed from Jacob Misirian of FIRST Robotics Team 2506
     * of Franklin, WI.
     */
    public final double L = 1;
    public final double W = 1; // These are from the Length and Width between wheels. 
    //CHANGE THESE IF THE ROBOT IS NOT A SQUARE

    private SwerveWheelModuleSubsystem backRight;
    private SwerveWheelModuleSubsystem backLeft;
    private SwerveWheelModuleSubsystem frontRight;
    private SwerveWheelModuleSubsystem frontLeft;

    private PIDController angleController;
    private double fieldangle = 0; //
    // private int FLAngle;
    // private int FRAngle;
    // private int BLAngle;
    // private int BRAngle;

    public void setFieldAngle(double fieldangle) {
        this.fieldangle = fieldangle;
        angleController.setSetpoint(this.fieldangle);

    }

    public SwerveDriveSubsystem() {
        backRight = new SwerveWheelModuleSubsystem(1, 8, 0, "BR");// These are the motors and encoder ports for swerve drive
        backLeft = new SwerveWheelModuleSubsystem(3, 2, 1, "BL");
        frontRight = new SwerveWheelModuleSubsystem(5, 4, 2, "FR");
        frontLeft = new SwerveWheelModuleSubsystem(7, 6, 3, "FL");//The order is angle, speed, encoder, offset 
        //(offset gets changed by calibration.)
        zero();
        SendableRegistry.addChild(this, backRight);
        SendableRegistry.addChild(this, backLeft);
        SendableRegistry.addChild(this, frontRight);
        SendableRegistry.addChild(this, frontLeft);

        SendableRegistry.addLW(this, "Swerve Drive Subsystem");

        angleController = new PIDController(.3, 0, 0);
        angleController.enableContinuousInput(0, Math.PI * 2);
        angleController.setSetpoint(0);
        SmartDashboard.putString("Ready Call", "Autobots, Roll Out!");
        periodic();
    }

    public void drive(double x1, double y1, double x2) {
        double r = Math.sqrt((L * L) + (W * W)); //diagonal of robot
        double backRightSpeed;
        double backLeftSpeed;
        double frontRightSpeed;
        double frontLeftSpeed;

        double backRightAngle;
        double backLeftAngle;
        double frontRightAngle;
        double frontLeftAngle;

        //From here to the next comment sets each module to the <x,y> from 
        //the joystick plus rotation times the diagonal to that module 
        //(i.e. FR is set to <x1,y1> + x2*<diagonal BL->FR>)
        double a = x1 - x2 * (L / r);
        double b = x1 + x2 * (L / r);
        double c = y1 - x2 * (W / r);
        double d = y1 + x2 * (W / r);

        backRightSpeed = Math.sqrt((b * b) + (c * c));
        backLeftSpeed = Math.sqrt((a * a) + (c * c));
        frontRightSpeed = Math.sqrt((b * b) + (d * d));
        frontLeftSpeed = Math.sqrt((a * a) + (d * d));

        frontRightAngle = Math.atan2(b, c) / Math.PI;
        backRightAngle = Math.atan2(a, c) / Math.PI;
        frontLeftAngle = Math.atan2(b, d) / Math.PI;
        backLeftAngle = Math.atan2(a, d) / Math.PI;

        backLeft.drive(-backLeftSpeed, -backLeftAngle);
        backRight.drive(backRightSpeed, -backRightAngle);
        frontRight.drive(frontRightSpeed, -frontRightAngle);
        frontLeft.drive(-frontLeftSpeed, -frontLeftAngle);

        //Print speed values
        SmartDashboard.putNumber("Backright Speed", backRightSpeed);
        SmartDashboard.putNumber("Backleft Speed", backLeftSpeed);
        SmartDashboard.putNumber("Frontright Speed", frontRightSpeed);
        SmartDashboard.putNumber("Frontleft Speed", frontLeftSpeed);

    }

    @Override
    public void periodic() {
        //Do NOT make negative!!!!
        //adding is counter clockwise, subtratcting is clockwise?
        if (!Preferences.containsKey("FLAngle") || Preferences.getDouble("FLAngle", -2) == -2)
            Preferences.setDouble("FLAngle", 346);
        frontLeft.setZero(Preferences.getDouble("FLAngle", -2));
        if (!Preferences.containsKey("FRAngle") || Preferences.getDouble("FRAngle", -2) == -2)
            Preferences.setDouble("FRAngle", 250);
        frontRight.setZero(Preferences.getDouble("FRAngle", -2));
        if (!Preferences.containsKey("BLAngle") || Preferences.getDouble("BLAngle", -2) == -2)
            Preferences.setDouble("BLAngle", 163);
        backLeft.setZero(Preferences.getDouble("BLAngle", -2));
        if (!Preferences.containsKey("BRAngle") || Preferences.getDouble("BRAngle", -2) == -2)
            Preferences.setDouble("BRAngle", 252);
        backRight.setZero(Preferences.getDouble("BRAngle", -2));
    }
    
    public void stop(){
        backRight.stop();
        backLeft.stop();
        frontRight.stop();
        frontLeft.stop();
    }

    public void zero(){
        if (frontLeft.autoCali() != -2){
            Preferences.setDouble("FLAngle", frontLeft.zeroCali());
            Preferences.setDouble("FRAngle", frontRight.zeroCali());
            Preferences.setDouble("BLAngle", backLeft.zeroCali());
            Preferences.setDouble("BRAngle", backRight.zeroCali());
        }
    }

    public void autoCali(){
        if (frontLeft.autoCali() != -2){
            Preferences.setDouble("FLAngle", frontLeft.autoCali());
            Preferences.setDouble("FRAngle", frontRight.autoCali());
            Preferences.setDouble("BLAngle", backLeft.autoCali());
            Preferences.setDouble("BRAngle", backRight.autoCali());
        }
    }
}
