package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private TalonFX leftMotor;
    private TalonFX rightMotor;
    private CANSparkMax angleMotor;
    private CANSparkMax loadMotor;

    public ShooterSubsystem() {
        this.leftMotor = new TalonFX(1);
        this.rightMotor = new TalonFX(2);
        this.angleMotor = new CANSparkMax(4, CANSparkMaxLowLevel.MotorType.kBrushed);
        this.loadMotor = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);

        SendableRegistry.addLW(this, "Shooter Subsystem");
    }

    public void shoot(double shootVal) {
        leftMotor.set(ControlMode.PercentOutput, -shootVal);
        rightMotor.set(ControlMode.PercentOutput, shootVal);
    }

    public void prep(double angle, double load) {
        angleMotor.set(angle);
        loadMotor.set(-load);
    }

    public void loadStop() {
        loadMotor.set(0);
    }

    public void backLoad(double power) {
        loadMotor.set(power);
    }

}