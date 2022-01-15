package frc.robot;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.util.MathUtil;

public class WheelDrive {
    private final double MAX_VOLTS = 4.95; // Voltage for the Andymark Absolute Encoders used in the SDS kit.

    private TalonFX angleMotor;
    private TalonFX speedMotor;
    private PIDController pidController;
    private CANCoder angleEncoder;

    private double encoderOffset;

    public WheelDrive(int angleMotor, int speedMotor, CANCoder angleEncoder, double encoderOffset) {
        this.angleMotor = new TalonFX(angleMotor);
        this.speedMotor = new TalonFX(speedMotor); // We're using CANSparkMax controllers, but
                                                                             // not their encoders.
        this.angleEncoder = angleEncoder;
        this.encoderOffset = encoderOffset;

        // pidController = new PIDController(1, 0, 0, new AnalogInput(encoder),
        // this.angleMotor);
        pidController = new PIDController(.5, 0, 0); // This is the PID constant, we're not using any
                                                     // Integral/Derivative control but changing the P value will make
                                                     // the motors more aggressive to changing to angles.

        // pidController.setTolerance(20); //sets tolerance, shouldn't be needed.

        pidController.enableContinuousInput(0, MAX_VOLTS); // This makes the PID controller understand the fact that for
                                                           // our setup, 4.95V is the same as 0 since the wheel loops.
    }

    public void setZero(double offset) {
        encoderOffset = offset;
    }

    // angle is a value between -1 to 1
    public void drive(double speed, double angle) {

        double currentEncoderValue = (angleEncoder.getBusVoltage() + encoderOffset) % MAX_VOLTS; // Combines reading from
                                                                                              // encoder

        // Optimization offset can be calculated here.
        double setpoint = angle * (MAX_VOLTS * 0.5) + (MAX_VOLTS * 0.5); // Optimization offset can be calculated here.
        setpoint += MAX_VOLTS;
        setpoint %= MAX_VOLTS; // ensure setpoint is on scale 0-4.95

        // if the setpoint is more than pi/4 rad away form the current position, then
        // just reverse the speed
        if (MathUtil.getCyclicalDistance(currentEncoderValue, setpoint, MAX_VOLTS) > MAX_VOLTS / 4) {
            speed *= -1;
            setpoint = (setpoint + MAX_VOLTS / 2) % MAX_VOLTS;
        }

        speedMotor.set(ControlMode.PercentOutput, speed); // sets motor speed.
        pidController.setSetpoint(setpoint);

        double pidOut = pidController.calculate(currentEncoderValue, setpoint);

        angleMotor.set(ControlMode.PercentOutput, -pidOut);

        // if (Robot.PREFS.getBoolean("DEBUG_MODE", false)) {
        //     Robot.PREFS.putDouble("Encoder [" + angleEncoder.getChannel() + "] currentEncoderValue",
        //             currentEncoderValue);
        //     Robot.PREFS.putDouble("Encoder [" + angleEncoder.getChannel() + "] setpoint", setpoint);
        //     Robot.PREFS.putDouble("Encoder [" + angleEncoder.getChannel() + "] pidOut", pidOut);
        // }

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
        //Robot.PREFS.putDouble("Encoder [" + angleEncoder.getChannel() + "] getVoltage", angleEncoder.getVoltage());
        return angleEncoder.getBusVoltage();
    }

    public void stop() {
        pidController.setP(0);
        speedMotor.set(ControlMode.PercentOutput, 0);
    }

    public void restart() {
        pidController.setP(.5);
    }
}
