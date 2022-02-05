// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Lerp;
import frc.robot.MiniPID;

public class LeadScrew extends SubsystemBase {
  private final CANSparkMax motor = new CANSparkMax(10, MotorType.kBrushed); 

  private double power = 0;
  private boolean isHomed = false;
  private double setPoint = 6;

  public final double kLeftInches = 4;
  public final double kRightInches = 13.625;

  private final Encoder encoder = new Encoder(8,9);

  //switches DigitalInput
  DigitalInput leftSwitch = new DigitalInput(7);
  DigitalInput rightSwitch = new DigitalInput(6);

  //Lerp (og: 7833)
  Lerp ticksToInches = new Lerp(0, 7876, kLeftInches, kRightInches);
  MiniPID pid = new MiniPID (1/0.75,0,0); //Your moving this PIDControl, afterwards delete this line

  /** Creates a new LeadScrew. */
  public LeadScrew() {}

  // setter for power
  public void setPower(double newpower){
    this.power = newpower;
  }

  public boolean isLeftSwitchPressed(){
    return leftSwitch.get() == false;
  }
  public boolean isRightSwitchPressed(){
    return rightSwitch.get() == false;
  }

  public double getPositionInches(){
    return ticksToInches.get(encoder.get());
  }
  public void setPosition(double setpoint) {
    this.setPoint = setpoint;
  }

  public void runPID() {
    power = pid.getOutput(getPositionInches(), setPoint);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
    if(isHomed){
      power = pid.getOutput(getPositionInches(), setPoint);
    }


    //safety/switch logic
    if (isLeftSwitchPressed() && power <0) {
      power = 0;
    }
    if (isRightSwitchPressed() && power >0) {
      power = 0;
    }

    if (isLeftSwitchPressed()) {
      isHomed = true; 
      // reset encoder 
      encoder.reset();
    }

    motor.set(this.power);
    SmartDashboard.putNumber("LeadScrew/power", this.power);
    SmartDashboard.putNumber("leadscrew/EncoderValue", encoder.get());
    SmartDashboard.putNumber("leadscrew/positionInches", ticksToInches.get(encoder.get()));
  }
public void setHomed(boolean newIsHomed) {
  isHomed = newIsHomed;
}
public Encoder getEncoder() {
  return encoder;
}
public MiniPID getPid() {
  return pid;
}
}