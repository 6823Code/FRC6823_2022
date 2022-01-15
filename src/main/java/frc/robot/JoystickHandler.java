package frc.robot;

//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.util.MathUtil;

public class JoystickHandler {
    public static final int T1 = 9;
    public static final int T2 = 10;
    public static final int T3 = 11;
    public static final int T4 = 12;
    public static final int T5 = 13;
    public static final int T6 = 14;

    private Joystick joystick;
    private double deadZone;

    public JoystickHandler() {
        this.joystick = new Joystick(3);
        this.deadZone = Robot.PREFS.getDouble("DeadZone", .05);
	}

	public double getRawAxis0() {
        return joystick.getRawAxis(0);
    }

    public double getRawAxis1() {
        return joystick.getRawAxis(1);
    }

    public double getRawAxis5() {
        return joystick.getRawAxis(5);
    }

    public double getRawAxis2() {
        return joystick.getRawAxis(2);
    }

    public double getRawAxis6() {
        return joystick.getRawAxis(6);
    }

    public boolean isYeet() {
        // -1 is forward, 1 is backward
        // so if forward, yeet.
        return getRawAxis2() < -.9;
    }

    public double getAxis0() {
        return MathUtil.clipToZero(getRawAxis0(), deadZone);
    }

    public double getAxis1() {
        return MathUtil.clipToZero(getRawAxis1(), deadZone);
    }

    public double getAxis5() {
        return MathUtil.clipToZero(getRawAxis5(), deadZone);
    }

    public Joystick joy() {
        return joystick;
    }

    public JoystickButton button(int buttonNumber) {
        return new JoystickButton(joystick, buttonNumber);
    }

    public boolean isJoyInUse() {
        return getAxis0() != 0 || getAxis1() != 0 || getAxis5() != 0;
    }
}
