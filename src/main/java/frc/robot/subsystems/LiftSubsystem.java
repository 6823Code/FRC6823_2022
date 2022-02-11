package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LiftSubsystem extends SubsystemBase {

    private CANSparkMax leftLiftMotor;
    private CANSparkMax rightLiftMotor;
    private double liftPower;

    public LiftSubsystem() {
        this.leftLiftMotor = new CANSparkMax(16, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.rightLiftMotor = new CANSparkMax(17, CANSparkMaxLowLevel.MotorType.kBrushless);
        liftPower = 0.5;

        SendableRegistry.addChild(this, leftLiftMotor);
        SendableRegistry.addChild(this, rightLiftMotor);
        SendableRegistry.addLW(this, "Lift");

    }

    public void liftDown() {
        leftLiftMotor.set(-liftPower);
        rightLiftMotor.set(liftPower);
    }

    public void liftUp() {
        leftLiftMotor.set(liftPower);
        rightLiftMotor.set(-liftPower);
    }

    public void liftStop() {
        leftLiftMotor.set(0);
        rightLiftMotor.set(0);
    }
}
