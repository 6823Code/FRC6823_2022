package frc.robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

import com.kauailabs.navx.frc.AHRS;

public class NavXHandler {
    private AHRS ahrs;

    public NavXHandler() {
        try {
            /***********************************************************************
             * navX-MXP: - Communication via RoboRIO MXP (SPI, I2C) and USB. - See
             * http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
             ***********************************************************************/
            ahrs = new AHRS(SerialPort.Port.kMXP);
            ahrs.resetDisplacement();
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
    }

    public void printEverythingDammit() {

        SmartDashboard.putNumber("getAngle()", ahrs.getAngle());
        SmartDashboard.putNumber("	getActualUpdateRate()", ahrs.getActualUpdateRate());
        SmartDashboard.putNumber("getDisplacementX()", ahrs.getDisplacementX());
        SmartDashboard.putNumber("getDisplacementY()", ahrs.getDisplacementY());
        SmartDashboard.putNumber("getDisplacementZ()", ahrs.getDisplacementZ());

        SmartDashboard.putNumber("getVelocityX()", ahrs.getVelocityX());
        SmartDashboard.putNumber("getVelocityY()", ahrs.getVelocityY());
        SmartDashboard.putNumber("getVelocityZ()", ahrs.getVelocityZ());
    }

    public double getAngleRad() {
        return ahrs.getAngle() * 2 * Math.PI / 360d;
    }

    public double yeetPerSecond() {
        return Math.sqrt(Math.pow(ahrs.getVelocityX(), 2) + Math.pow(ahrs.getVelocityY(), 2));
    }
}