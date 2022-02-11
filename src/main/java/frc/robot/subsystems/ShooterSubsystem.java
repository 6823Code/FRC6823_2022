package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private TalonFX leftMotor;
    private TalonFX rightMotor;
    private CANSparkMax angleMotor;
    private CANSparkMax loadMotor;
    private AnalogInput angleEncoder;
    private int offset;
    private DigitalInput frontLimit;
    private DigitalInput backLimit;
    private PIDController speedController;
    //private Encoder encoder;

    public ShooterSubsystem() {
        this.leftMotor = new TalonFX(11);
        this.rightMotor = new TalonFX(12);
        this.angleMotor = new CANSparkMax(14, CANSparkMaxLowLevel.MotorType.kBrushed);
        this.loadMotor = new CANSparkMax(13, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.angleEncoder = new AnalogInput(0); //Change later
        this.speedController = new PIDController(0.0001, 0, 0);

        SendableRegistry.addLW(this, "Shooter");
    }

    public void shoot(double shootVal) {
        leftMotor.set(ControlMode.PercentOutput, -shootVal);
        rightMotor.set(ControlMode.PercentOutput, shootVal);
        //leftMotor.getSelectedSensorVelocity();
    }

    public void prep(double anglePower, double load) {
        //if(((angleEncoder.getValue() > offset && anglePower > 0) || (angleEncoder.getValue() < offfset + 90 && anglePower <= 0)) && !frontLimit.get() && !backLimit.get()){
            angleMotor.set(-anglePower);
        //} else{angleMotor.set(0);}
        loadMotor.set(load);
    }

    public void loadStop() {
        loadMotor.set(0);
    }

    public void backLoad(double power) {
        loadMotor.set(-power);
    }

}