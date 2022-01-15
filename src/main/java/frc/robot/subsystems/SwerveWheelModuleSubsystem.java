package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.util.MathUtil;

public class SwerveWheelModuleSubsystem extends SubsystemBase {
    private final double MAX_VOLTS = 4.95; // Voltage for the Andymark Absolute Encoders used in the SDS kit.
    private final double P = .008;

    private TalonFX angleMotor;
    private TalonFX speedMotor;
    private PIDController pidController;
    private CANCoder angleEncoder;

    private double encoderOffset;
    private int angleEncoderChannel;

    public SwerveWheelModuleSubsystem(int angleMotorChannel, int speedMotorChannel, int angleEncoderChannel,
            double encoderOffset) {
        // We're using TalonFX motors on CAN.
        this.angleMotor = new TalonFX(angleMotorChannel);
        this.speedMotor = new TalonFX(speedMotorChannel);
        this.angleEncoder = new CANCoder(angleEncoderChannel);
        this.angleEncoderChannel = angleEncoderChannel;
        this.encoderOffset = encoderOffset;

        pidController = new PIDController(P, 0, 0); // This is the PID constant, we're not using any
        // Integral/Derivative control but changing the P value will make
        // the motors more aggressive to changing to angles.

        angleEncoder.configAbsoluteSensorRange(AbsoluteSensorRange.Unsigned_0_to_360);

        pidController.setTolerance(20); //sets tolerance, shouldn't be needed.

        pidController.enableContinuousInput(0, 360); // This makes the PID controller understand the fact that for
        // our setup, 360 degrees is the same as 0 since the wheel loops.

        SendableRegistry.addChild(this, angleMotor);
        SendableRegistry.addChild(this, speedMotor);
        SendableRegistry.addChild(this, angleEncoder);
        SendableRegistry.addLW(this, "Swerve Wheel Module");

    }

    public void setZero(double offset) {
        encoderOffset = offset;
    }

    // angle is a value between -1 to 1
    public void drive(double speed, double angle) {

        double currentEncoderValue = angleEncoder.getAbsolutePosition(); // Combines reading from
        // encoder

        // Optimization offset can be calculated here.
        double setpoint = angle * 180 + 180 + encoderOffset; // Optimization offset can be calculated here.
        // setpoint += 360;
        setpoint %= 360; // ensure setpoint is on scale 0-360

        // if the setpoint is more than 90 degrees away form the current position, then
        // just reverse the speed
        // if (MathUtil.getCyclicalDistance(currentEncoderValue, setpoint, 360) > 90) {
        //     speed *= -1;
        //     setpoint = (setpoint + 180) % 360;
        // }

        speedMotor.set(ControlMode.PercentOutput, speed); // sets motor speed. Set back to speed
        pidController.setSetpoint(setpoint);

        double pidOut = pidController.calculate(currentEncoderValue, setpoint);

        //if (angleMotor.getDeviceID() == 3)
            angleMotor.set(ControlMode.PercentOutput, pidOut);
        //else
            //angleMotor.set(ControlMode.PercentOutput, -pidOut);

        SmartDashboard.putNumber("Encoder [" + angleEncoderChannel + "] currentEncoderValue", currentEncoderValue);
        SmartDashboard.putNumber("Encoder [" + angleEncoderChannel + "] setpoint", setpoint);
        SmartDashboard.putNumber("Encoder [" + angleEncoderChannel + "] pidOut", pidOut);

        // This is for testing to find the max value of an encoder. The encoders we use
        // (and most encoders) give values from 0 - 4.95.
        /*
         * if (angleEncoder.getChannel() == 0) { if (angleEncoder.getVoltage() > maxVal)
         * { maxVal = angleEncoder.getVoltage();
         * Robot.prefs.putDouble("Encoder ["+angleEncoder.getChannel()+"] getVoltageMax"
         * , maxVal); } }
         */

    }

    // this method outputs voltages of the encoder to the smartDashBoard, useful for
    // calibrating the encoder offsets
    public double getVoltages() {
        return angleEncoder.getPosition() * 4.95 / 360;
    }

    public void stop() {
        pidController.setP(0);
        speedMotor.set(ControlMode.PercentOutput, 0);
    }

    public void restart() {
        pidController.setP(P);
    }

    @Override
    public void periodic() {
        // Robot.PREFS.putDouble("Encoder [" + angleEncoder.getChannel() + "]
        // getVoltage", angleEncoder.getVoltage());
    }
}
