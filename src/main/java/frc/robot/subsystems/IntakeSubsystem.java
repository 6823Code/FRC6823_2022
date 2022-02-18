package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    private CANSparkMax angleMotor;
    private CANSparkMax intakeMotor;
    private RelativeEncoder angleEncoder;
    private double inTakePower;
    private double anglePower;
    private int offset;
    private DigitalInput frontLimit;
    private DigitalInput backLimit;

    public IntakeSubsystem() {
        this.angleMotor = new CANSparkMax(10, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.intakeMotor = new CANSparkMax(9, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.angleEncoder = angleMotor.getEncoder();
        this.frontLimit = new DigitalInput(0);
        this.backLimit = new DigitalInput(1);

        inTakePower = -0.433;
        anglePower = -0.4;
        offset = 0;

        SendableRegistry.addChild(this, angleMotor);
        SendableRegistry.addChild(this, intakeMotor);
        SendableRegistry.addLW(this, "Intake");

    }

    public void backIntake() {
        intakeMotor.set(-inTakePower);
    }

    public void backAngle() {
        //if (angleEncoder.getPosition() > offset && !backLimit.get()){
            angleMotor.set(-anglePower);
        //}else{
            //stopAngle();
        //}
    }

    public void intake() {
        intakeMotor.set(inTakePower);
    }

    public void angle() {
        //if (angleEncoder.getPosition() < offset + 100 && !frontLimit.get()){
            angleMotor.set(anglePower * 1.5);
        //}else{
            //stopAngle();
        //}
    }

    public void stopIntake() {
        intakeMotor.set(0);
    }

    public void stopAngle(){
        angleMotor.set(0);
    }
}