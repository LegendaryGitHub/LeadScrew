// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.LeadScrewGoHome;
import frc.robot.commands.ManualControl;
import frc.robot.commands.PIDControl;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.LeadScrew;



/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final LeadScrew leadScrew = new LeadScrew();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final ManualControl rightRun = new ManualControl(() -> 1.0,leadScrew);
  private final ManualControl leftRun = new ManualControl(() -> -1.0,leadScrew); 
  private final Chassis chassis = new Chassis();

  private Joystick operator = new Joystick(1);
  private JoystickButton enableSliderControl = new JoystickButton(operator,1);
  


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    leadScrew.setDefaultCommand(new PIDControl(leadScrew));
    // Configure the button bindings
    configureButtonBindings();
    chassis.setDefaultCommand(new RunCommand(()->chassis.arcadeDrive(operator.getRawAxis(1), operator.getRawAxis(0)), chassis));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //Adds labers to Smartdash Board
    SmartDashboard.putData("leadscrew/runRight",rightRun);
    SmartDashboard.putData("leadscrew/runLeft",leftRun);
    //SmartDashboard.putData("Homed", );


    enableSliderControl.whileHeld(new RunCommand(
      ()->{
        leadScrew.setPosition( Lerp.lerp(operator.getRawAxis(3), -1, 1, leadScrew.kLeftInches, leadScrew.kRightInches) );
        leadScrew.runPID();
      }
      ,leadScrew
    ));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new ParallelCommandGroup(
      new LeadScrewGoHome(leadScrew,() -> 0.5).andThen(new PIDControl(leadScrew)),
      new RunCommand(()->chassis.arcadeDrive(.25,0), chassis).withTimeout(5)
      
    )
    
    ;
    // new ManualControl(() -> 1.0,leadScrew)
    // .andThen(new RunCommand(() -> new RunCommand(()->chassis.arcadeDrive(operator.getRawAxis(1), operator.getRawAxis(0)), chassis){},chassis))
    // .andThen(new InstantCommand());
    //;
    // An ExampleCommand will run in autonomous
    // return m_autoCommand;
    
  }
}
