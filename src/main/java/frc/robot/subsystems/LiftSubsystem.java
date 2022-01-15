/*package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LiftSubsystem extends SubsystemBase {

    private CANSparkMax leftMotor;
    private CANSparkMax rightMotor;
    private DigitalInput sensor;

    public LiftSubsystem(int leftMotor, int rightMotor) {
        this.leftMotor = new CANSparkMax(leftMotor, MotorType.kBrushless);
        this.rightMotor = new CANSparkMax(rightMotor, MotorType.kBrushless);
        this.sensor = new DigitalInput(5);
    }

    public void startUp() {
        if (sensor.get()) {
            leftMotor.set(Robot.PREFS.getDouble("LiftPower", 0));
            rightMotor.set(Robot.PREFS.getDouble("LiftPower", 0) * -1);
        } else {
            this.stop();
        }
    }

    public void startReverse() {
        leftMotor.set(Robot.PREFS.getDouble("LiftPower", 0) * -1);
        rightMotor.set(Robot.PREFS.getDouble("LiftPower", 0));
    }

    public void stop() {
        leftMotor.set(0);
        rightMotor.set(0);
    }

}
*/