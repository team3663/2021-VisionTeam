// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Util.PatternRecognition2;

public class C_PatternModelBuilder extends CommandBase {

  private NetworkTableEntry model;
  private NetworkTableEntry testing;
  private NetworkTableEntry save;

  private ArrayList<int[]> savedModels = new ArrayList<int[]>();

  public C_PatternModelBuilder() {

    ShuffleboardTab tab = Shuffleboard.getTab("Model Builder");

    testing = tab.add("Testing", false)
      .withWidget(BuiltInWidgets.kToggleSwitch)
      .withPosition(0, 0)
      .withSize(1, 1)
      .getEntry();

    model = tab.add("Model", "")
      .withWidget(BuiltInWidgets.kTextView)
      .withPosition(0, 1)
      .withSize(4, 1)
      .getEntry();

    save = tab.add("Save", false)
      .withWidget(BuiltInWidgets.kToggleButton)
      .withPosition(2, 0)
      .withSize(1, 1)
      .getEntry();
  }

  @Override
  public void execute() {

    if(testing.getBoolean(false)) {

      int[] detected = PatternRecognition2.generateModel();

      int result = 0;

      /*
      for(int i = 0; i < savedModels.size(); i++) {
        if(PatternRecognition2.getPatternError(savedModels.get(i), detected) < PatternRecognition2.getPatternError(savedModels.get(result), detected)) {
          result = i;
        }
      }
      */

      model.setString("" + result);
      
    } else {

      int[] foundModel = PatternRecognition2.generateModel();

      //check the save button
      if(save.getBoolean(false)) {
        save.setBoolean(false);
  
        savedModels.add(foundModel);
      }

      //show the model that the robot is seeings

      String text = "";

      try {
        text = "" + foundModel[0] + " " + foundModel[1];
      } catch(Exception e) {}

      try {
        text = "" + foundModel[0] + " " + foundModel[1] + " " + foundModel[2];
      } catch(Exception e) {}
      

      model.setString(text);
    }
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
