// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import com.revrobotics.CANSparkMax;
import frc.robot.subsystems.LeadScrew;

public class ManualControl extends CommandBase {
  private LeadScrew leadScrew;
  private DoubleSupplier powerSupplier;


  /** Creates a new LocalLeadScrew. */
  public ManualControl(DoubleSupplier powerSupplier, LeadScrew localLead) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(localLead);
    this.leadScrew = localLead;
    this.powerSupplier = powerSupplier;
    //this.power = power;
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //leadScrew.setPower(power);
    this.leadScrew.setPower(this.powerSupplier.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    leadScrew.setPower(0);
  }
  

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
