package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.util.MovingAverage;

public class LimeLightHandler extends SubsystemBase {
    private NetworkTable table;
    private NetworkTableEntry tx, ty, threeD;
    private PIDController aimPidController, distancePidController, strafePidController;
    private Servo servo;
    private boolean lemon, aimed;
    private ShooterSubsystem shooterSubsystem;
    private Timer timer;

    MovingAverage xFilter;

    public LimeLightHandler(int servoPort, ShooterSubsystem shooterSubsystem) {
        double KpDistance = Robot.PREFS.getDouble("KpDistance", .18); // speed in which distance is adjusted when
        // autoaiming
        double KpAim = Robot.PREFS.getDouble("KpAim", -.036); // speed in which aiming is adjusted when autoaiming
        double KpStrafe = Robot.PREFS.getDouble("KpStrafe", .1);
        aimPidController = new PIDController(KpAim, 0, 0);
        distancePidController = new PIDController(KpDistance, 0, 0);
        strafePidController = new PIDController(KpStrafe, 0, 0);
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        threeD = table.getEntry("camtran");
        this.servo = new Servo(servoPort);
        xFilter = new MovingAverage(4);
        this.shooterSubsystem = shooterSubsystem;
        timer = new Timer();
        aimed = false;

        this.register(); // registers limelight as a subsystem
    }

    public void aimReset() {
        aimed = false;
    }

    public void updatePrefs() {
        double KpDistance = Robot.PREFS.getDouble("KpDistance", .18); // speed in which distance is adjusted when
                                                                      // autoaiming
        double KpAim = Robot.PREFS.getDouble("KpAim", -.036);
        double KpStrafe = Robot.PREFS.getDouble("KpStrafe", 0); // speed in which aiming is adjusted when autoaiming
        aimPidController.setP(KpAim);
        distancePidController.setP(KpDistance);
        strafePidController.setP(KpStrafe);
        if (SmartDashboard.getBoolean("LemonPipeline", false)) {
            table.getEntry("pipeline").setNumber(1);
        } else {
            table.getEntry("pipeline").setNumber(0);
        }

    }

    public double aim() {
        double x = tx.getDouble(0.0);
        xFilter.nextVal(x);
        x = xFilter.get();
        double y = ty.getDouble(0.0);

        Robot.PREFS.putDouble("x", x);
        Robot.PREFS.putDouble("y", y);

        double aimCommand = aimPidController.calculate(x, 0);
        double distanceCommand = distancePidController.calculate(y, 0);
        Robot.PREFS.putDouble("aimCommand", aimCommand);
        Robot.PREFS.putDouble("distanceCommand", distanceCommand);

        return aimCommand;

    }

    public double[] aimSteerAndStrafe() { // this is the big boy method, it do all the things
        double skew = threeD.getDoubleArray(new double[] { 0 })[0]; // (x,y,z,pitch,yaw,roll)
        // if(tx.getDouble(0)>0){
        // strafePidController.setSetpoint(-90);
        // }
        // else
        strafePidController.setSetpoint(0);

        double skewCommand = strafePidController.calculate(skew);
        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0);
        xFilter.nextVal(skew);
        skew = xFilter.get();

        Robot.PREFS.putDouble("x", x);

        double aimCommand = aimPidController.calculate(x, 0);
        double distanceCommand = distancePidController.calculate(y, 0);

        Robot.PREFS.putDouble("aimCommand", aimCommand);

        if (Math.abs(x) < 5 && Math.abs(y) < 5 && Math.abs(skew) < 6) {
            SmartDashboard.putBoolean("Shoot", true);
            Robot.rgb.setLimeLight(true);
        } else {
            SmartDashboard.putBoolean("Shoot", false);
            Robot.rgb.setLimeLight(false);
        }

        return new double[] { aimCommand, skewCommand * -1, distanceCommand };//
    }

    public double[] goToPolar(double distance, double theta) { // this is the big boy method, it do all the things, but
                                                               // it also takes command in radians and inches for
                                                               // getting a polar point from target, Negative is left,
                                                               // Postitive Right.
                                                               //
                                                               // NOTE: Currently this Method computes distance using
                                                               // the Limelight 3D pipeline, this is experimental, and
                                                               // if unstable, we may want to use the standard limelight
                                                               // pipeline instead

        double skewOffset = threeD.getDoubleArray(new double[] { 0 })[0]; // (x,y,z,pitch,yaw,roll)
        double skew = distance * Math.sin(theta);
        double ySetpoint = distance * Math.cos(theta);
        double out;

        strafePidController.setSetpoint(skew);

        double skewCommand = strafePidController.calculate(skewOffset);
        double x = tx.getDouble(0.0);
        double r = threeD.getDoubleArray(new double[] { 0 })[2];
        xFilter.nextVal(skewOffset);
        skewOffset = xFilter.get();

        Robot.PREFS.putDouble("x", x);

        double aimCommand = aimPidController.calculate(x, 0);
        double distanceCommand = distancePidController.calculate(r, ySetpoint);

        Robot.PREFS.putDouble("aimCommand", aimCommand);

        if (Math.abs(x) < 15 && (Math.abs(r - distance)) < 15
                && Math.abs(threeD.getDoubleArray(new double[] { 0 })[0]) < 15) {
            SmartDashboard.putBoolean("Shoot", true);
            out = 1;
        } else {
            SmartDashboard.putBoolean("Shoot", false);
            out = 0;
        }

        return new double[] { aimCommand, skewCommand, distanceCommand * -1, out };//
    }

    public double[] goTo(double x, double z) { // this is the big boy method, it do all the things, but
                                               // it also takes command in radians and inches for
                                               // getting a coordinate point from target, Negative is left,
                                               // Postitive Right.
                                               //
                                               // NOTE: Currently this Method computes distance using
                                               // the Limelight 3D pipeline, this is experimental, and
                                               // if unstable, we may want to use the standard limelight
                                               // pipeline instead.
        this.pipeLineSelect(true);
        double skewOffset = threeD.getDoubleArray(new double[] { 0 })[0]; // (x,y,z,pitch,yaw,roll)
        double xtheta = tx.getDouble(0.0);
        double r = threeD.getDoubleArray(new double[] { 0 })[2];
        double out;
        // xFilter.nextVal(skewOffset);
        // skewOffset = xFilter.get();

        // prefs.putDouble("x", xtheta);

        double aimCommand = aimPidController.calculate(xtheta, 0);
        double distanceCommand = distancePidController.calculate(r, z);
        double skewCommand = strafePidController.calculate(skewOffset, x);

        // prefs.putDouble("aimCommand", aimCommand);

        if (Math.abs(xtheta) < 3 && Math.abs(r) < 3 && Math.abs(skewOffset) < 5) { // for SmartDashBoard Green light
            SmartDashboard.putBoolean("Shoot", true);
            out = 1;
        } else {
            SmartDashboard.putBoolean("Shoot", false);
            out = 0;
        }

        return new double[] { aimCommand, skewCommand * -1, -1 * distanceCommand, out };//
    }

    public double[] strafeAndAim() {
        double skew = threeD.getDoubleArray(new double[] { 0 })[0]; // (x,y,z,pitch,yaw,roll)
        // if(tx.getDouble(0)>0){
        // strafePidController.setSetpoint(-90);
        // }
        // else
        strafePidController.setSetpoint(0);

        double skewCommand = strafePidController.calculate(skew);
        double x = tx.getDouble(0.0);
        xFilter.nextVal(skew);
        skew = xFilter.get();

        Robot.PREFS.putDouble("x", x);

        double aimCommand = aimPidController.calculate(x, 0);

        Robot.PREFS.putDouble("aimCommand", aimCommand);

        return new double[] { aimCommand, skewCommand * -1 };//

    }

    public void pipeLineSwitch() {
        this.lemon = !this.lemon;
        if (this.lemon) {
            servo.setAngle(Preferences.getInstance().getDouble("LemonAngle", 45));

        } else {
            servo.setAngle(Preferences.getInstance().getDouble("NonLemonAngle", 20));
        }
        if (this.lemon) {
            table.getEntry("pipeline").setNumber(1);
        } else {
            table.getEntry("pipeline").setNumber(0);
        }
    }

    public void pipeLineSelect(boolean lemon) {
        if (lemon) {
            servo.setAngle(Preferences.getInstance().getDouble("LemonAngle", 45));

        } else {
            servo.setAngle(Preferences.getInstance().getDouble("NonLemonAngle", 20));

        }
    }

    public boolean LemonPipeline() {
        return lemon;
    }

    public double[] programmedDistances(double input) {
        this.updatePrefs();
        // if (aimed) {
        // return new double[] { 0, 0, 0 };
        // }
        double rpm;

        double[] array;
        if (input < .33) { // lo
            this.pipeLineSelect(false);
            array = goToPolar(-56, 0);
            rpm = 8500;
        } else { // med
            this.pipeLineSelect(false);
            array = goToPolar(-170, 0);
            rpm = 8850;
        }
        Preferences.getInstance().putDouble("RPMControl", rpm);

        // if (!(array[3] == 1)) {
        return new double[] { array[0], array[1], array[2] };
        // } else {
        // shooterSubsystem.shooterPIDAuto(rpm);
        // aimed = true;
        // return new double[] { 0, 0, 0 };
        // }

    }

}