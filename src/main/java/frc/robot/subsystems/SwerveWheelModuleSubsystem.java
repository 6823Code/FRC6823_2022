package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.MathUtil;

public class SwerveWheelModuleSubsystem extends SubsystemBase {

    private TalonFX angleMotor;
    private TalonFX speedMotor;
    private CANCoder angleEncoder;
    private boolean calibrateMode;
    private double encoderOffset;
    private String motorName;
    private SimpleWidget calibrateState;

    public SwerveWheelModuleSubsystem(int angleMotorChannel, int speedMotorChannel, int angleEncoderChannel,
            String motorName, SimpleWidget calibrate) {
        // We're using TalonFX motors on CAN.
        this.angleMotor = new TalonFX(angleMotorChannel);
        this.speedMotor = new TalonFX(speedMotorChannel);
        this.angleEncoder = new CANCoder(angleEncoderChannel); // CANCoder Encoder
        this.speedMotor.setNeutralMode(NeutralMode.Coast);
        this.motorName = motorName;

        angleEncoder.configAbsoluteSensorRange(AbsoluteSensorRange.Unsigned_0_to_360);
    

        SendableRegistry.addChild(this, angleMotor);
        SendableRegistry.addChild(this, speedMotor);
        SendableRegistry.addChild(this, angleEncoder);
        SendableRegistry.addLW(this, "Swerve Wheel Module");
        calibrateState = calibrate;

    }

    public void setZero(double offset) {
        encoderOffset = offset;
    }

    // angle is a value between -1 to 1
    public void drive(double speed, double angle) {

        double currentEncoderValue = angleMotor.getSelectedSensorPosition(); // Combines reading from
        // encoder

        // Optimal offset can be calculated here.
        angle *= 180;
        angle += encoderOffset;
        angle = MathUtil.mod(angle, 360); // ensure setpoint is on scale 0-360

        // if the setpoint is more than 90 degrees away form the current position, then
        // just reverse the speed
        // Set a variable to hold wheel position in degrees or one to hold
        // units/rotation
        if (MathUtil.getCyclicalDistance(unitsToDegrees(currentEncoderValue), angle, 360) > 90) {
            speed *= -1;
            angle = (angle + 180) % 360;
        }

        speedMotor.set(ControlMode.PercentOutput, speed); // sets motor speed //22150 units/100 ms at 12.4V

        angle /= 360; // Angle position in rotations
        angle *= 26227; // Angle Position in encoder units

        if (calibrateMode)
            angleMotor.set(ControlMode.PercentOutput, 0); // Sends new pidOut (in units/100 ms) to velocity control
        else
            angleMotor.set(ControlMode.Position, angle);

        SmartDashboard.putNumber("Encoder " + motorName, unitsToDegrees(angleMotor.getSelectedSensorPosition()));
    }

    // this method outputs position of the encoder to the smartDashBoard, useful for
    // calibrating the encoder offsets
    public double getPosition() {
        return angleEncoder.getPosition() * 180;
    }

    public void stop() {
        speedMotor.set(ControlMode.PercentOutput, 0);
        angleMotor.set(ControlMode.PercentOutput, 0);
    }

    public void restart() {
    }

    @Override
    public void periodic() {
        calibrateMode = calibrateState.getEntry().getBoolean(false);
    }

    public double autoCali() {
        double offset;
        if (calibrateMode) {
            offset = (unitsToDegrees(angleMotor.getSelectedSensorPosition()) + 180) % 360;
            setZero(offset);
            return offset;
        } else {
            return 0;
        }
    }

    public double autoCaliZero(){
        setZero(0);
        return 0;
    }

    private double unitsToDegrees(double units){
        units = units / 26227 * 360 ;
        return MathUtil.mod(units, 360);
    }

    public void coast(){
        speedMotor.setNeutralMode(NeutralMode.Coast);
    }

    public void brake(){
        speedMotor.setNeutralMode(NeutralMode.Brake);
    }
}
