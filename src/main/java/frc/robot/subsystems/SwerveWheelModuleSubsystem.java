package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveWheelModuleSubsystem extends SubsystemBase {
    private final double P = .008;
    private final double I = .00001;

    private TalonFX angleMotor;
    private TalonFX speedMotor;
    private PIDController pidController;
    private CANCoder angleEncoder;

    private double encoderOffset;
    private int angleEncoderChannel;

    public SwerveWheelModuleSubsystem(int angleMotorChannel, int speedMotorChannel, int angleEncoderChannel) {
        // We're using TalonFX motors on CAN.
        this.angleMotor = new TalonFX(angleMotorChannel);
        this.speedMotor = new TalonFX(speedMotorChannel);
        this.angleEncoder = new CANCoder(angleEncoderChannel); //CANCoder Encoder
        this.angleEncoderChannel = angleEncoderChannel;

        pidController = new PIDController(P, I, 0); // This is the PID constant, we're not using any
        // Integral/Derivative control but increasing the P value will make
        // the motors more aggressive to changing to angles.

        angleEncoder.configAbsoluteSensorRange(AbsoluteSensorRange.Unsigned_0_to_360);

        pidController.setTolerance(20); //sets tolerance, shouldn't be needed.

        pidController.enableContinuousInput(0, 360); // This makes the PID controller understand the fact that for
        //our setup, 360 degrees is the same as 0 since the wheel loops.

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

        // Optimal offset can be calculated here.
        double setpoint = angle * 180 + 180 + encoderOffset;
        setpoint %= 360; // ensure setpoint is on scale 0-360

        // if the setpoint is more than 90 degrees away form the current position, then
        // just reverse the speed
        // if (MathUtil.getCyclicalDistance(currentEncoderValue, setpoint, 360) > 90) {
        //     speed *= -1;
        //     setpoint = (setpoint + 180) % 360;
        // }

        speedMotor.set(ControlMode.PercentOutput, speed); // sets motor speed //22150 units/100 ms at 12.4V
        SmartDashboard.putNumber("Speed " + angleEncoderChannel, speed);

        //Sets angle motor to angle
        pidController.setSetpoint(setpoint);
        double pidOut = pidController.calculate(currentEncoderValue, setpoint);
        //pidOut *= 3000 * 4096 * 600; //pidOut is on [-1, 1], pidOut * 3000 (Max rpm) * 4096 units/revolution * (600*100)ms/min
        //angleMotor.set(ControlMode.Velocity, pidOut); //Sends new pidOut (in units/100 ms) to velocity control
        angleMotor.set(ControlMode.PercentOutput, pidOut);

        SmartDashboard.putNumber("Encoder [" + angleEncoderChannel + "] currentEncoderValue", currentEncoderValue);
        SmartDashboard.putNumber("Encoder [" + angleEncoderChannel + "] setpoint", setpoint);
        SmartDashboard.putNumber("Encoder [" + angleEncoderChannel + "] pidOut", pidOut);
    }

    // this method outputs position of the encoder to the smartDashBoard, useful for
    // calibrating the encoder offsets
    public double getPosition() {
        return angleEncoder.getPosition() * 180;
    }

    public void stop() {
        pidController.setP(0);
        pidController.setI(0);
        speedMotor.set(ControlMode.PercentOutput, 0);
    }

    public void restart() {
        pidController.setP(P);
        pidController.setP(I);
    }

    @Override
    public void periodic() {
    }
}
