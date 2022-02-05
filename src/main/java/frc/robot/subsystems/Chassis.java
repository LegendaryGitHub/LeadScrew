// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Chassis extends SubsystemBase {
  //Creates the motors
  private CANSparkMax motorLeft = new CANSparkMax  (1, MotorType.kBrushless);
  private CANSparkMax motorRight = new CANSparkMax  (4, MotorType.kBrushless);
  //Creates the differentialdrive
  public DifferentialDrive driveTrain = new DifferentialDrive(motorLeft, motorRight);
  /** Creates a new Chassis. */
  public Chassis() {

  }
  public void arcadeDrive (double power, double turn) {
    //The drivetrain code
    driveTrain.arcadeDrive(power * -1, Math.abs(turn) < .25 ? 0 : turn ); //power * -1, Math.abs(turn) < .3 ? 0 : turn 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
