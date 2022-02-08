package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private CANSparkMax angleMotor;
    private CANSparkMax intakeMotor;
    private double inTakePower = 0.433;

    private double anglePower = 0.4;

    public IntakeSubsystem() {
        this.angleMotor = new CANSparkMax(10, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.intakeMotor = new CANSparkMax(9, CANSparkMaxLowLevel.MotorType.kBrushless);

        SendableRegistry.addChild(this, angleMotor);
        SendableRegistry.addChild(this, intakeMotor);
        SendableRegistry.addLW(this, "Swerve Wheel Module");

    }

    public void backIntake() {
        intakeMotor.set(-inTakePower);
    }

    public void backAngle() {
        angleMotor.set(-anglePower);
    }

    public void intake() {
        intakeMotor.set(inTakePower);
    }

    public void angle() {
        angleMotor.set(anglePower);
    }

    public void stopIntake() {
        intakeMotor.set(0);
    }

    public void stopAngle(){
        angleMotor.set(0);
    }
}