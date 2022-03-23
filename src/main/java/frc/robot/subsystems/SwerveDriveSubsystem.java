package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
//import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.Map;

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
    // CHANGE THESE IF THE ROBOT IS NOT A SQUARE

    private SwerveWheelModuleSubsystem backRight;
    private SwerveWheelModuleSubsystem backLeft;
    private SwerveWheelModuleSubsystem frontRight;
    private SwerveWheelModuleSubsystem frontLeft;

    private PIDController angleController;
    private double fieldangle = 0; //
    private SimpleWidget FLAngle;
    private SimpleWidget FRAngle;
    private SimpleWidget BLAngle;
    private SimpleWidget BRAngle;
    private SimpleWidget calibrateWidget;
    private SimpleWidget invertWidget;

    public void setFieldAngle(double fieldangle) {
        this.fieldangle = fieldangle;
        angleController.setSetpoint(this.fieldangle);

    }

    public SwerveDriveSubsystem() {
        calibrateWidget = Shuffleboard.getTab("Preferences").add("Calibrate?", false)
                .withWidget(BuiltInWidgets.kToggleButton);
        invertWidget = Shuffleboard.getTab("Preferences").add("Invert?", false)
                .withWidget(BuiltInWidgets.kToggleButton);

        backRight = new SwerveWheelModuleSubsystem(1, 8, 26, "BR", calibrateWidget);// These are the motors and encoder
                                                                                   // ports for swerve drive
        backLeft = new SwerveWheelModuleSubsystem(3, 2, 27, "BL", calibrateWidget);
        frontRight = new SwerveWheelModuleSubsystem(5, 4, 28, "FR", calibrateWidget);
        frontLeft = new SwerveWheelModuleSubsystem(7, 6, 25, "FL", calibrateWidget);// The order is angle, speed,
                                                                                   // encoder, offset
        // (offset gets changed by calibration.)
        SendableRegistry.addChild(this, backRight);
        SendableRegistry.addChild(this, backLeft);
        SendableRegistry.addChild(this, frontRight);
        SendableRegistry.addChild(this, frontLeft);

        SendableRegistry.addLW(this, "Swerve Drive Subsystem");

        angleController = new PIDController(.3, 0, 0);
        angleController.enableContinuousInput(0, Math.PI * 2);
        angleController.setSetpoint(0);
        //SmartDashboard.putString("Ready Call", "Autobots, Roll Out!");
        FLAngle = Shuffleboard.getTab("Calibrate").add("FLAngle", 0).withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("min", 0, "max", 360));
        FRAngle = Shuffleboard.getTab("Calibrate").add("FRAngle", 0).withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("min", 0, "max", 360));
        BLAngle = Shuffleboard.getTab("Calibrate").add("BLAngle", 0).withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("min", 0, "max", 360));
        BRAngle = Shuffleboard.getTab("Calibrate").add("BRAngle", 0).withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("min", 0, "max", 360));
        //autoCaliZero();
    }

    public void drive(double x1, double y1, double x2) {
        double r = Math.sqrt((L * L) + (W * W)); // diagonal of robot
        double backRightSpeed;
        double backLeftSpeed;
        double frontRightSpeed;
        double frontLeftSpeed;

        double backRightAngle;
        double backLeftAngle;
        double frontRightAngle;
        double frontLeftAngle;

        // From here to the next comment sets each module to the <x,y> from
        // the joystick plus rotation times the diagonal to that module
        // (i.e. FR is set to <x1,y1> + x2*<diagonal BL->FR>)
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

        if (invertWidget.getEntry().getBoolean(false)){
            backLeft.drive(-backLeftSpeed, -backLeftAngle);
            backRight.drive(-backRightSpeed, -backRightAngle);
            frontRight.drive(-frontRightSpeed, -frontRightAngle);
            frontLeft.drive(-frontLeftSpeed, -frontLeftAngle);
        }else{
            backLeft.drive(-backLeftSpeed, -backLeftAngle);
            backRight.drive(backRightSpeed, -backRightAngle);
            frontRight.drive(frontRightSpeed, -frontRightAngle);
            frontLeft.drive(frontLeftSpeed, -frontLeftAngle);
        }

        //Print speed values
        SmartDashboard.putNumber("Backright Speed", backRightSpeed);
        SmartDashboard.putNumber("Backleft Speed", backLeftSpeed);
        SmartDashboard.putNumber("Frontright Speed", frontRightSpeed);
        SmartDashboard.putNumber("Frontleft Speed", frontLeftSpeed);

    }

    // @Override
    public void periodic() {
        // Do NOT make negative!!!!
        // adding is counter clockwise, subtratcting is clockwise?
        frontLeft.setZero(FLAngle.getEntry().getDouble(0));
        frontRight.setZero(FRAngle.getEntry().getDouble(0));
        backLeft.setZero(BLAngle.getEntry().getDouble(0));
        backRight.setZero(BRAngle.getEntry().getDouble(0));
    }

    public void stop() {
        backRight.stop();
        backLeft.stop();
        frontRight.stop();
        frontLeft.stop();
    }

    public void autoCali() {
        FLAngle.getEntry().setDouble(frontLeft.autoCali());
        FRAngle.getEntry().setDouble(frontRight.autoCali());
        BLAngle.getEntry().setDouble(backLeft.autoCali());
        BRAngle.getEntry().setDouble(backRight.autoCali());
    }

    public void autoCaliZero(){
        if (frontLeft.autoCali() != -2){
            FLAngle.getEntry().setNumber(frontLeft.autoCaliZero());
            FRAngle.getEntry().setNumber(frontRight.autoCaliZero());
            BLAngle.getEntry().setNumber(backLeft.autoCaliZero());
            BRAngle.getEntry().setNumber(backRight.autoCaliZero());
        }
    }
}
