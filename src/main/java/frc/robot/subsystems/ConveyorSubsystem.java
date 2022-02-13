package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ConveyorSubsystem extends SubsystemBase {

    private CANSparkMax conveyorMotor;
    private double conveyorPower;

    public ConveyorSubsystem() {
        this.conveyorMotor = new CANSparkMax(15, CANSparkMaxLowLevel.MotorType.kBrushless);
        conveyorPower = 0.5;

        SendableRegistry.addChild(this, conveyorMotor);
        SendableRegistry.addLW(this, "Conveyor");

    }

    public void backConvey() {
        conveyorMotor.set(conveyorPower);
    }

    public void convey() {
        conveyorMotor.set(-conveyorPower);
    }

    public void stopConvey() {
        conveyorMotor.set(0);
    }
}
