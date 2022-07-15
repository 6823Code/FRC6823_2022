package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.Map;

public class IntakeSubsystem extends SubsystemBase {

    private CANSparkMax angleMotor;
    private CANSparkMax intakeMotor;
    private double inTakePower;
    private double anglePower;
    private SimpleWidget intakeWidget;
    private SimpleWidget angleWidget;

    public IntakeSubsystem() {
        this.angleMotor = new CANSparkMax(10, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.intakeMotor = new CANSparkMax(9, CANSparkMaxLowLevel.MotorType.kBrushless);
        intakeWidget = Shuffleboard.getTab("Preferences").addPersistent("intakePercent", 0.433)
                .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("min", 0, "max", 1));
        angleWidget = Shuffleboard.getTab("Preferences").addPersistent("hammerPercent", 0.433)
                .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("min", 0, "max", 1));

        SendableRegistry.addChild(this, angleMotor);
        SendableRegistry.addChild(this, intakeMotor);
        SendableRegistry.addLW(this, "Intake");

    }

    public void backIntake() {
        intakeMotor.set(-inTakePower * 0.5);
    }

    public void angle() {
        angleMotor.set(-1.5 * anglePower);
    }

    public void intake() {
        intakeMotor.set(inTakePower);
    }

    public void backAngle() {
        angleMotor.set(anglePower);
    }

    public void backAngle(double power){
        angleMotor.set(power);
    }

    public void stopIntake() {
        intakeMotor.set(0);
    }

    public void stopAngle() {
        angleMotor.set(0);
    }

    @Override
    public void periodic() {
        inTakePower = intakeWidget.getEntry().getDouble(-2);
        anglePower = angleWidget.getEntry().getDouble(-2);
        SmartDashboard.putNumber("Intake Speed", intakeMotor.getEncoder().getVelocity());
    }
}