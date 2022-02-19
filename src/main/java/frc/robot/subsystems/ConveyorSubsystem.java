package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ConveyorSubsystem extends SubsystemBase {

    private CANSparkMax conveyorMotor;
    private double conveyorPower;

    public ConveyorSubsystem() {
        this.conveyorMotor = new CANSparkMax(15, CANSparkMaxLowLevel.MotorType.kBrushless);
        if (!Preferences.containsKey("conveyorPower") || Preferences.getDouble("conveyPower", -1) == -1)
            Preferences.setDouble("conveyorPower", 0.3);
        conveyorPower = Preferences.getDouble("conveyPower", -1);
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

    @Override
    public void periodic(){
        if (!Preferences.containsKey("conveyorPower") || Preferences.getDouble("conveyPower", -1) == -1)
            Preferences.setDouble("conveyorPower", 0.3);
        conveyorPower = Preferences.getDouble("conveyPower", -1);
    }
}
