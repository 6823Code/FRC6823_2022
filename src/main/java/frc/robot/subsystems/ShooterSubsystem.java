package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
//import com.revrobotics.SparkMaxAlternateEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private TalonFX leftMotor;
    private TalonFX rightMotor;
    private CANSparkMax angleMotor;
    private CANSparkMax loadMotor;
    private AnalogInput angleEncoder;
    private int offset;
    private int velocity;
    private int test;
    private DigitalInput frontLimit;
    private DigitalInput backLimit;
    private PIDController speedController;
    //private SparkMaxAlternateEncoder.Type altEncoderType = SparkMaxAlternateEncoder.Type.kQuadrature;
    //private Encoder encoder;

    public ShooterSubsystem() {
        this.leftMotor = new TalonFX(11);
        this.rightMotor = new TalonFX(12);
        this.angleMotor = new CANSparkMax(14, CANSparkMaxLowLevel.MotorType.kBrushed);
        this.loadMotor = new CANSparkMax(13, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.angleEncoder = new AnalogInput(0);
        this.speedController = new PIDController(0.0001, 0, 0);
        velocity = 0;
        offset = 0;
        test = 0;

        SendableRegistry.addLW(this, "Shooter");
    }

    public void shoot(int rpm) {
        velocity = rpm * 4096 * 600; //rpm * 4096 units/rotation * intervals of 100 ms per minute
        velocity *= -1;
        leftMotor.set(ControlMode.Velocity, velocity); //velocity in encoder units per 100 ms
        rightMotor.set(ControlMode.Velocity, -velocity);
        //leftMotor.getSelectedSensorVelocity();
        //test += 100;
    }

    public void shoot(double power) {
        leftMotor.set(ControlMode.PercentOutput, -power); //velocity in encoder units per 100 ms
        rightMotor.set(ControlMode.PercentOutput, power);
        //leftMotor.getSelectedSensorVelocity();
    }

    public void prep(double anglePower, double load) {
        //if(((angleEncoder.getValue() > offset && anglePower > 0) || (angleEncoder.getValue() < offset + 90 && anglePower <= 0)) && !frontLimit.get() && !backLimit.get()){
            angleMotor.set(-anglePower);
        //} else{angleMotor.set(0);}
        loadMotor.set(load);

        SmartDashboard.putNumber("Shooter Angle", angleEncoder.getValue());
    }

    public void loadStop() {
        loadMotor.set(0);
    }

    public void backLoad(double power) {
        loadMotor.set(-power);
    }

}