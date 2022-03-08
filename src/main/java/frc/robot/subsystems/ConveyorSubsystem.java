package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ConveyorSubsystem extends SubsystemBase {

    private CANSparkMax conveyorMotor;
    private double conveyorPower;
    private SimpleWidget conveyorWidget;

    public ConveyorSubsystem() {
        this.conveyorMotor = new CANSparkMax(15, CANSparkMaxLowLevel.MotorType.kBrushless);
        conveyorWidget = Shuffleboard.getTab("Preferences").addPersistent("conveyorPower", 0.3).withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 1));
        SendableRegistry.addChild(this, conveyorMotor);
        SendableRegistry.addLW(this, "Conveyor");
    }

    public void backConvey() {
        conveyorMotor.set(-conveyorPower);
    }

    public void convey() {
        conveyorMotor.set(conveyorPower);
    }

    public void stopConvey() {
        conveyorMotor.set(0);
    }

    @Override
    public void periodic() {
        conveyorPower = conveyorWidget.getEntry().getDouble(-2);
    }
}
