package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
//import frc.robot.WheelDrive;

public class SwerveDriveSubsystem extends SubsystemBase {
    /**
     * What does this class do? some weird math to take controller inputs and
     * convert them into rotation and speed values for each motor (which is
     * controlled via the WheelDrive class)
     * <p>
     * This code heavily attributed from Jacob Misirian of FIRST Robotics Team 2506
     * of Franklin, WI.
     */
    public final double L = 1; //
    public final double W = 1; // These are from the Length and Width between wheels. CHANGE THESE IF YOUR
    // ROBOT IS NOT A SQUARE

    private SwerveWheelModuleSubsystem backRight;
    private SwerveWheelModuleSubsystem backLeft;
    private SwerveWheelModuleSubsystem frontRight;
    private SwerveWheelModuleSubsystem frontLeft;

    private PIDController angleController;
    private double fieldangle = 0;

    public void setFieldAngle(double fieldangle) {
        this.fieldangle = fieldangle;
        angleController.setSetpoint(this.fieldangle);

    }

    public SwerveDriveSubsystem() {
        backRight = new SwerveWheelModuleSubsystem(7, 6, 3, 90);// These are the motors and encoder ports for swerve drive,
        backLeft = new SwerveWheelModuleSubsystem(5, 4, 2, 90);
        frontRight = new SwerveWheelModuleSubsystem(3, 2, 1, 45);
        frontLeft = new SwerveWheelModuleSubsystem(1, 8, 0, 270);// angle,speed,encoder,offset (offset gets changed by
      // smartdashboard in calibration.)

        SendableRegistry.addChild(this, backRight);
        SendableRegistry.addChild(this, backLeft);
        SendableRegistry.addChild(this, frontRight);
        SendableRegistry.addChild(this, frontLeft);

        SendableRegistry.addLW(this, "Swerve Drive Subsystem");

        angleController = new PIDController(.3, 0, 0);
        angleController.enableContinuousInput(0, Math.PI * 2);
        angleController.setSetpoint(0);

    }

    public void drive(double x1, double y1, double x2) {
        double r = Math.sqrt((L * L) + (W * W));
        double backRightSpeed;//
        double backLeftSpeed;
        double frontRightSpeed;
        double frontLeftSpeed;//

        double backRightAngle;
        double backLeftAngle;
        double frontRightAngle;
        double frontLeftAngle;
        // if (x1 == 0 && y1 == 0 && x2 == 0) {

        // backRightSpeed = 0;//
        // backLeftSpeed = 0;
        // frontRightSpeed = 0;
        // frontLeftSpeed = 0;//

        // // backRightAngle = 0;
        // // backLeftAngle = 0;
        // // frontRightAngle = 0;
        // // frontLeftAngle = 0;

        // } else {
        // x1, y1 are from the position of the joystick, x2 is from the rotation

        // y1 *= -1;
        // x1 *= -1;

        double a = x1 - x2 * (L / r);
        double b = x1 + x2 * (L / r);
        double c = y1 - x2 * (W / r);
        double d = y1 + x2 * (W / r);

        backRightSpeed = Math.sqrt((b * b) + (c * c));//
        backLeftSpeed = Math.sqrt((a * a) + (c * c));
        frontRightSpeed = Math.sqrt((b * b) + (d * d));
        frontLeftSpeed = Math.sqrt((a * a) + (d * d));//

        backRightAngle = Math.atan2(b, c) / Math.PI;
        backLeftAngle = Math.atan2(a, c) / Math.PI;
        frontRightAngle = Math.atan2(b, d) / Math.PI;
        frontLeftAngle = Math.atan2(a, d) / Math.PI;
        // }
        backRight.drive(backRightSpeed, 1-backRightAngle);
        backLeft.drive(backLeftSpeed, 1-backLeftAngle);
        frontRight.drive(frontRightSpeed, 1-frontRightAngle);
        frontLeft.drive(frontLeftSpeed, 1-frontLeftAngle);

        SmartDashboard.putNumber("Backright Speed", backRightSpeed);
        SmartDashboard.putNumber("Backleft Speed", backLeftSpeed);
        SmartDashboard.putNumber("Frontright Speed", frontRightSpeed);
        SmartDashboard.putNumber("Frontleft Speed", frontLeftSpeed);

    }

    @Override
    public void periodic() {
        // if (Robot.PREFS.getBoolean("PracticeBot", false)) {
        //     backRight.setZero(Robot.PREFS.getDouble("BROffsetPractice", 0) + 1.25);
        //     backLeft.setZero(Robot.PREFS.getDouble("BLOffsetPractice", 0) + 1.25);
        //     frontRight.setZero(Robot.PREFS.getDouble("FROffsetPractice", 0) + 1.25);
        //     frontLeft.setZero(Robot.PREFS.getDouble("FLOffsetPractice", 0) + 1.25);
        // } else {

        //Do NOT make negative!!!!
        //adding is counter clockwise, subtratcting is clockwise
        backRight.setZero(80);
        backLeft.setZero(175);
        frontRight.setZero(280);
        frontLeft.setZero(340);
        
        // }
    }
}
