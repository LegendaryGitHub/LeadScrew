// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LeadScrew;

public class LeadScrewGoHome extends CommandBase {
  LeadScrew leadScrew;
  DoubleSupplier localPowerSupplier;
  /** Creates a new LeadScrewGoHome. */
  public LeadScrewGoHome(LeadScrew leadScrew, DoubleSupplier powerSupplier) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.leadScrew = leadScrew;
    this.localPowerSupplier = powerSupplier;
    addRequirements(leadScrew);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    leadScrew.setPower(localPowerSupplier.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    leadScrew.setPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if((leadScrew.isLeftSwitchPressed() && localPowerSupplier.getAsDouble() < 0) || (leadScrew.isRightSwitchPressed() && localPowerSupplier.getAsDouble() < 0)) {
        leadScrew.setHomed(true);
        leadScrew.getEncoder().reset();
        return true;
    }
    else {
      return false;
    }
  }
}
