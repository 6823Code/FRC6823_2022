// package frc.robot.commands;

// public class AutoPlaybackShooter {
//     // currentInput.button(8).whileHeld(() -> swerveDriveSubsystem.drive(0,
//         //         0.1, 0), swerveDriveSubsystem);

//         // currentInput.button(22).whileHeld(backLoad);

//         // currentInput.button(22).whenReleased(backLoad::stop);

//         // currentInput.button(17).whileActiveContinuous(() -> intakeSubsystem.backAngle(), intakeSubsystem)
//         //         .whenInactive(intakeSubsystem::stopAngle);
//         // currentInput.button(18).whileActiveContinuous(() -> intakeSubsystem.intake(), intakeSubsystem)
//         //         .whenInactive(intakeSubsystem::stopIntake);
//         // currentInput.button(19).whileActiveContinuous(() -> intakeSubsystem.backIntake(), intakeSubsystem)
//         //         .whenInactive(intakeSubsystem::stopIntake);
//         // currentInput.button(20).whileActiveContinuous(() -> intakeSubsystem.angle(), intakeSubsystem)
//         //         .whenInactive(intakeSubsystem::stopAngle);

//         // currentInput.button(23).whenPressed(() -> swerveDriveSubsystem.autoCali(), swerveDriveSubsystem);
//         // // joystickHandler4.button(8).whileHeld(() ->
//         // // shooterSubsystem.setShooterAngle(30), shooterSubsystem);
//         // currentInput.button(1).whileHeld(() ->
//         // liftSubsystem.liftUp(), liftSubsystem)
//         // .whenInactive(liftSubsystem::liftStop);

//         // currentInput.button(6).whileHeld(() ->
//         // liftSubsystem.liftDown(), liftSubsystem)
//         // .whenInactive(liftSubsystem::liftStop);

//         // currentInput.button(9).whileHeld(() ->
//         // liftSubsystem.leftUp(), liftSubsystem)
//         // .whenInactive(liftSubsystem::liftStop);
//         // currentInput.button(10).whileHeld(() ->
//         // liftSubsystem.leftDown(), liftSubsystem)
//         // .whenInactive(liftSubsystem::liftStop);
//         // currentInput.button(11).whileHeld(() ->
//         // liftSubsystem.rightUp(), liftSubsystem)
//         // .whenInactive(liftSubsystem::liftStop);
//         // currentInput.buttonPressed(12).whileHeld(() ->
//         // liftSubsystem.rightDown(), liftSubsystem)
//         // .whenInactive(liftSubsystem::liftStop);

//         double loadRate;
//         int shootRateLeft;
//         int shootRateRight;
//         if(currentInput.getAxis10() != 0){
//             loadRate = shooterSubsystem.getLoadPercent();
//             shootRateLeft = shooterSubsystem.getShooterRPMLeft()*20;
//             shootRateRight = shooterSubsystem.getShooterRPMRight()*20;
//             // shootRateLeft = (int)160000;
//             // shootRateRight = (int)160000;
//             //shootRate = 1.0;
//             conveyorSubsystem.convey();
//         }else if (currentInput.getAxis11() != 0){
//             loadRate = 0;
//             shootRateLeft = 0;
//             shootRateRight = 0;
//             conveyorSubsystem.convey();
//         }else{
//             loadRate = 0;
//             shootRateLeft = 0;
//             shootRateRight = 0;
//             conveyorSubsystem.stopConvey();
//         }
      
//         if (currentInput.getAxis8() < -0.75){
//             shooterAngle = 70;
//         }else if (currentInput.getAxis8() > 0.75){
//             shooterAngle = 50;
//         // }else if (joystickHandler.getAxis0() < -0.75){
//         //     shooterAngle = 65;
//         }else if (currentInput.getAxis7() > 0.75){
//             shooterAngle = 60;
//         }
//         shooterSubsystem.setShooterAngle(shooterAngle);
//         shooterSubsystem.prep(loadRate);
//         shooterSubsystem.shoot(shootRateLeft, shootRateRight);
//         }
// }
