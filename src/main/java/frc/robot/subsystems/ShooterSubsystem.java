package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private final double P = .04;
    private final double I = .00001;
    private TalonFX leftMotor;
    private TalonFX rightMotor;
    private CANSparkMax angleMotor;
    private CANSparkMax loadMotor;
    //private AnalogInput angleEncoder;
    //private int offset;
    private int velocity;
    //private int test;
    // private DigitalInput frontLimit;
    // private DigitalInput backLimit;
    // private PIDController speedController;
    //private SparkMaxAlternateEncoder.Type altEncoderType = SparkMaxAlternateEncoder.Type.kQuadrature;
    //private Encoder encoder;
    private int shooterRPM;
    private double shooterAnglePercentBack;
    private double shooterAnglePercentForward;
    private double loadPercent;
    private DutyCycleEncoder encoder;
    private PIDController pidController;
    private SimpleWidget RPM;
    private SimpleWidget loadWidget;

    public ShooterSubsystem() {
        this.leftMotor = new TalonFX(11);
        this.rightMotor = new TalonFX(12);
        this.angleMotor = new CANSparkMax(14, CANSparkMaxLowLevel.MotorType.kBrushed);
        this.loadMotor = new CANSparkMax(13, CANSparkMaxLowLevel.MotorType.kBrushless);
        //this.angleEncoder = new AnalogInput(0);
        //this.speedController = new PIDController(0.0001, 0, 0);
        this.encoder = new DutyCycleEncoder(1);
        this.pidController = new PIDController(P, I, 0);
        pidController.setTolerance(20);
        velocity = 0;
        // offset = 0;
        // test = 0;
        RPM = Shuffleboard.getTab("Preferences").add("shooterRPM", 3000).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 6000));
        loadWidget = Shuffleboard.getTab("Preferences").add("LoadRate", 0.6).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -1, "max", 1));
        SendableRegistry.addLW(this, "Shooter");
    }

    public void shoot(int rpm) {
        velocity = rpm; //rpm * 2048 units/rotation * intervals of 100 ms per minute
        velocity *= -1;
        leftMotor.set(ControlMode.Velocity, -velocity); //velocity in encoder units per 100 ms
        rightMotor.set(ControlMode.Velocity, velocity);
        SmartDashboard.putNumber("velocity target", velocity);
        //leftMotor.getSelectedSensorVelocity();
        //test += 100;
    }

    // public void shoot(double power) {
    //     leftMotor.set(ControlMode.PercentOutput, -power); //velocity in encoder units per 100 ms
    //     rightMotor.set(ControlMode.PercentOutput, power);
    //     //leftMotor.getSelectedSensorVelocity();
    // }

    public void prep(double load) {
        loadMotor.set(load);
    }

    public void loadStop() {
        loadMotor.set(0);
    }

    public void backLoad(double power) {
        loadMotor.set(-power);
    }
    public int getShooterRPM()
    {
        return shooterRPM;
    }
    public double getShooterAnglePercentBack()
    {
        return shooterAnglePercentBack;
    }
    public double getShooterAnglePercentForward()
    {
        return shooterAnglePercentForward;
    }
    public double getLoadPercent()
    {
        return loadPercent;
    }
    
    public void shootStop(){
        leftMotor.set(ControlMode.PercentOutput, 0);
        rightMotor.set(ControlMode.PercentOutput, 0);
    }

    public void setShooterAngle(double angle)
    {
        double currentEncoderValue = (encoder.get()-0.584) * 360;
        double setpoint = -Math.abs(angle);
        pidController.setSetpoint(setpoint);
        double pidOut = pidController.calculate(currentEncoderValue, setpoint);
        angleMotor.set(-pidOut);
        SmartDashboard.putBoolean("settingSA", true);
        SmartDashboard.putNumber("shooterPIDout", pidOut);
    }

    public void temp()
    {
        SmartDashboard.putBoolean("settingSA", false);
    }

    @Override
    public void periodic() {
        shooterRPM = RPM.getEntry().getNumber(-2).intValue();
        loadPercent = loadWidget.getEntry().getDouble(-1);

        // SmartDashboard.putNumber("Left shoot rpm", leftMotor.getSelectedSensorVelocity() / 600 / 2048);
        // SmartDashboard.putNumber("Right shoot rpm", rightMotor.getSelectedSensorVelocity() / 600 / 2048);
        SmartDashboard.putNumber("Left shoot rpm", leftMotor.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Right shoot rpm", rightMotor.getSelectedSensorVelocity());
        SmartDashboard.putNumber("shooter angle", encoder.get());
    }

}