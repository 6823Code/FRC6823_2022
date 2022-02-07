package frc.robot;

import frc.robot.commands.AutoCommandGroup;
import frc.robot.commands.FieldSpaceDrive;
import frc.robot.commands.RobotSpaceDrive;
import frc.robot.commands.RotateToAngle;
import frc.robot.commands.RotateToZero;
import frc.robot.subsystems.SwerveDriveSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;

public class RobotContainer {
    public SwerveDriveSubsystem swerveDriveSubsystem;
    public NavXHandler navX;

    public FieldSpaceDrive fieldSpaceDriveCommand;
    private RobotSpaceDrive robotSpaceDriveCommand;
    private AutoCommandGroup auton; 
    private JoystickHandler joystickHandler;
    public LimeLightSubsystem limeLightSubsystem;

    public LimeLightSubsystem getLimeLightSubsystem() {
        return limeLightSubsystem;
    }

    public SwerveDriveSubsystem getSwervedriveSubsystem() {
        return swerveDriveSubsystem;
    }

    public NavXHandler getNavXHandler() {
        return navX;
    }

    public RobotContainer() {
        swerveDriveSubsystem = new SwerveDriveSubsystem();
        joystickHandler = new JoystickHandler();
        limeLightSubsystem = new LimeLightSubsystem(0);

        navX = new NavXHandler(); // navx input

        //Field space uses navx to get its angle
        fieldSpaceDriveCommand = new FieldSpaceDrive(swerveDriveSubsystem, joystickHandler, navX);
        robotSpaceDriveCommand = new RobotSpaceDrive(swerveDriveSubsystem, joystickHandler);
        swerveDriveSubsystem.setDefaultCommand(fieldSpaceDriveCommand);

        limeLightSubsystem.setServoAngle(35);
        limeLightSubsystem.setPipeline(1);
        RotateToZero.setInitialAngle(navX.getAngleRad());
        navX.setInitialAngle();

        configureButtonBindings();
    }

    public AutoCommandGroup getAutoCommandGroup() {
        auton = new AutoCommandGroup(this);
        return auton;
    }

    private void configureButtonBindings() {
        RotateToAngle.setInitialAngle(navX.getAngleRad());
        RotateToZero.setInitialAngle(navX.getAngleRad());
        // Hold button 8 to set the swerve just forward, this is for calibration
        // purposes
        joystickHandler.button(8).whileHeld(() -> swerveDriveSubsystem.drive(0.1, 
        0, 0), swerveDriveSubsystem);

        // This will set the current orientation to be "forward" for field drive
        joystickHandler.button(3).whenPressed(fieldSpaceDriveCommand::zero);

        // Holding 7 will enable robot space drive, instead of field space
        joystickHandler.button(7).whenHeld(robotSpaceDriveCommand);
    }
}
