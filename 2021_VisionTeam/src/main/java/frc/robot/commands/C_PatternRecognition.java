// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Util.PatternRecognition;

public class C_PatternRecognition extends CommandBase {
  /** Creates a new C_PatternRecognition. */
  NetworkTableEntry patterns;
  public C_PatternRecognition() {
    // Use addRequirements() here to declare subsystem dependencies.
    ShuffleboardTab tab  = Shuffleboard.getTab("Pattern Testing");
     patterns = tab.add("Pattern Result", "none")
      .withWidget(BuiltInWidgets.kTextView)
        .withPosition(0, 0)
          .withSize(4, 1)
          .getEntry();

  }

 
  @Override
  public void execute() {

    switch(PatternRecognition.getPattern()){
      case RED_A: 
        patterns.setString("Red A");
        break;

      case RED_B: 
        patterns.setString("Red B");
        break;

      case BLUE_A: 
        patterns.setString("Blue A");
        break;

      case BLUE_B: 
        patterns.setString("Blue B");
        break;

        default:
        break;
    }
    

    
  }

 
  }

