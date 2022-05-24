package frc.robot.commands;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.NavXHandler;
import frc.robot.subsystems.ConveyorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LiftSubsystem;
import frc.robot.subsystems.LimeLightSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;

public class AutoPlayback extends CommandBase { //Logger should log joystick 3 to axes 0-6 and buttons 1-16, and joystick 4 to axes 7-12 and buttons 17-26
    //Declare subsystem, Joystick Handler, NavX
    private SwerveDriveSubsystem swerveDrive;
    private NavXHandler navXHandler;
    private double speedRate;
    private double turnRate;

    private double fieldAngle = 0; //Angle of away from driver from zero
    private double shooterAngle = 60;

    public SwerveDriveSubsystem swerveDriveSubsystem;
    public NavXHandler navX;
    public ShooterSubsystem shooterSubsystem;
    public IntakeSubsystem intakeSubsystem;
    public ConveyorSubsystem conveyorSubsystem;
    public Load backLoad;
    public LiftSubsystem liftSubsystem;
    private AutoCommandGroup auton;
    public LimeLightSubsystem limeLightSubsystem;

    public AutoPlayback(SwerveDriveSubsystem subsystem, ShooterSubsystem shooterSubsystem, IntakeSubsystem intakeSubsystem, ConveyorSubsystem conveyorSubsystem, 
    Input[] inputs, NavXHandler navXHandler) {
        //Instantiate subsystem, Joystick Handler, NavX
        this.swerveDrive = subsystem;
        this.shooterSubsystem = shooterSubsystem;
        this.intakeSubsystem = intakeSubsystem;
        this.conveyorSubsystem = conveyorSubsystem;
        this.navXHandler = navXHandler;
        speedRate = 0.5; //change to recording 
        turnRate = 0.5; //change to recording
        addRequirements(swerveDrive);
    }

    @Override
    public void execute() {
        for(int i = 0; i < inputs.length; i++){
            Input currentInput = inputs[i];
            navXHandler.printEverything();

            //Set xval, yval, spinval to the scaled values from the joystick, bounded on [-1, 1]
        double xval = Math.max(Math.min(currentInput.getAxis0() * -speedRate, 1), -1);
        double yval = Math.max(Math.min(currentInput.getAxis1() * speedRate, 1), -1);
        double spinval = Math.max(Math.min(currentInput.getAxis5() * turnRate, 1), -1);
        double robotAngle = navXHandler.getAngleRad() - fieldAngle;

        // mapping field space to robot space
        double txval = getTransX(xval, yval, robotAngle);
        double tyval = getTransY(xval, yval, robotAngle);

        swerveDrive.drive(txval, tyval, spinval);

        currentInput.button(8).whileHeld(() -> swerveDriveSubsystem.drive(0,
                0.1, 0), swerveDriveSubsystem);

        currentInput.button(22).whileHeld(backLoad);

        currentInput.button(22).whenReleased(backLoad::stop);

        currentInput.button(17).whileActiveContinuous(() -> intakeSubsystem.backAngle(), intakeSubsystem)
                .whenInactive(intakeSubsystem::stopAngle);
        currentInput.button(18).whileActiveContinuous(() -> intakeSubsystem.intake(), intakeSubsystem)
                .whenInactive(intakeSubsystem::stopIntake);
        currentInput.button(19).whileActiveContinuous(() -> intakeSubsystem.backIntake(), intakeSubsystem)
                .whenInactive(intakeSubsystem::stopIntake);
        currentInput.button(20).whileActiveContinuous(() -> intakeSubsystem.angle(), intakeSubsystem)
                .whenInactive(intakeSubsystem::stopAngle);

        currentInput.button(23).whenPressed(() -> swerveDriveSubsystem.autoCali(), swerveDriveSubsystem);
        // joystickHandler4.button(8).whileHeld(() ->
        // shooterSubsystem.setShooterAngle(30), shooterSubsystem);
        currentInput.button(1).whileHeld(() ->
        liftSubsystem.liftUp(), liftSubsystem)
        .whenInactive(liftSubsystem::liftStop);

        currentInput.button(6).whileHeld(() ->
        liftSubsystem.liftDown(), liftSubsystem)
        .whenInactive(liftSubsystem::liftStop);

        currentInput.button(9).whileHeld(() ->
        liftSubsystem.leftUp(), liftSubsystem)
        .whenInactive(liftSubsystem::liftStop);
        currentInput.button(10).whileHeld(() ->
        liftSubsystem.leftDown(), liftSubsystem)
        .whenInactive(liftSubsystem::liftStop);
        currentInput.button(11).whileHeld(() ->
        liftSubsystem.rightUp(), liftSubsystem)
        .whenInactive(liftSubsystem::liftStop);
        currentInput.button(12).whileHeld(() ->
        liftSubsystem.rightDown(), liftSubsystem)
        .whenInactive(liftSubsystem::liftStop);

        double loadRate;
        int shootRateLeft;
        int shootRateRight;
        if(currentInput.getAxis10() != 0){
            loadRate = shooterSubsystem.getLoadPercent();
            shootRateLeft = shooterSubsystem.getShooterRPMLeft()*20;
            shootRateRight = shooterSubsystem.getShooterRPMRight()*20;
            // shootRateLeft = (int)160000;
            // shootRateRight = (int)160000;
            //shootRate = 1.0;
            conveyorSubsystem.convey();
        }else if (currentInput.getAxis11() != 0){
            loadRate = 0;
            shootRateLeft = 0;
            shootRateRight = 0;
            conveyorSubsystem.convey();
        }else{
            loadRate = 0;
            shootRateLeft = 0;
            shootRateRight = 0;
            conveyorSubsystem.stopConvey();
        }
      
        if (currentInput.getAxis8() < -0.75){
            shooterAngle = 70;
        }else if (currentInput.getAxis8() > 0.75){
            shooterAngle = 50;
        // }else if (joystickHandler.getAxis0() < -0.75){
        //     shooterAngle = 65;
        }else if (currentInput.getAxis7() > 0.75){
            shooterAngle = 60;
        }
        shooterSubsystem.setShooterAngle(shooterAngle);
        shooterSubsystem.prep(loadRate);
        shooterSubsystem.shoot(shootRateLeft, shootRateRight);
        }
    }

    private double getTransX(double x, double y, double angle) { //Returns x direction robot needs to move in
        return x * Math.cos(angle) + -y * Math.sin(angle);
    }

    private double getTransY(double x, double y, double angle) { //Returns y direction robot needs to move in
        return x * Math.sin(angle) + y * Math.cos(angle);
    }

    public void zero() { //Zeroes direction
        this.fieldAngle = navXHandler.getAngleRad(); // + Math.PI
        swerveDrive.setFieldAngle(fieldAngle);
    }
}
