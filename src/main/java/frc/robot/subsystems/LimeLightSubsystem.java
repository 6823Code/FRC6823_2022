package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
//import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
//import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLightSubsystem extends SubsystemBase {

    private Servo servo;
    // private int pipeline;
    private NetworkTable table;

    private double lastknownZ = -75;
    private double lastKnownX = 0;
    public static final int towardsGround = 15, forward = 40, towardsTarget = 65;

    public LimeLightSubsystem(int servo) {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        this.servo = new Servo(servo);
        this.setPipeline(0);

        SendableRegistry.addLW(this, "LimeLight Subsystem");
    }

    public void setPipeline(int pipeline) {
        table.getEntry("pipeline").setNumber(pipeline);
        // if (pipeline == 0) {
        // this.setServoAngle(65);
        // } else if (pipeline == 1) {
        // this.setServoAngle(15);
        // }
        // else if..... (for other pipeline based servo angles)
    }

    public double getServoAngle() {
        SmartDashboard.putNumber("THE servo angel", this.servo.getAngle());
        return this.servo.getAngle();
    }

    private double forwardAngle = 40;

    public double getServoAngleFromGroundRad() {
        return ((this.servo.getAngle() - forwardAngle) / 360.0) * (2 * Math.PI);
    }

    public int getPipeline() {
        return (int) table.getEntry("getpipe").getDouble(0);
    }

    public void setServoAngle(double degrees) {
        servo.setAngle(degrees);
    }

    // "T"x is for theta, aka this is from 2d pipelines, non "T" methods get 3d
    // pipline functions.
    public double getTx() {

        return table.getEntry("tx").getDouble(0);
    }

    public double getTxRad() {
        return (table.getEntry("tx").getDouble(0) / 360.0) * (2.0 * Math.PI);
    }

    public double getTy() {
        return table.getEntry("ty").getDouble(0);
    }

    public double getTyRad() {
        return (table.getEntry("ty").getDouble(0) / 360.0) * (2.0 * Math.PI);
    }

    // this is the 3d Distance, should always be negative
    public double getZ() {
        double newZ = table.getEntry("camtran").getDoubleArray(new double[] { 0 })[2];
        if (newZ < -15 && newZ > -300) {
            lastknownZ = newZ;
            lastKnownX = table.getEntry("camtran").getDoubleArray(new double[] { 0 })[0];
        }
        return lastknownZ;
    }

    // this is the 3d strafe
    public double getX() {
        return lastKnownX;
    }

    public boolean hasTarget() {
        return table.getEntry("tv").getDouble(0) != 0;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Servo_Angle", getServoAngle());
    }

    // @Override
    // public void initialize() {

    // }
}