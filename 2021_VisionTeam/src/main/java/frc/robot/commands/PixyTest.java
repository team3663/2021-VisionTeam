// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.links.SPILink;

public class PixyTest extends CommandBase {

  private final int MAX_POWER_CELLS = 10;

  private Pixy2 pixy;

  public PixyTest() {
    pixy = Pixy2.createInstance(new SPILink());
    pixy.init();
  }

  @Override 
  public void execute() { 

    int numBlocks = pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, MAX_POWER_CELLS);

    if(numBlocks > 0) {
      ArrayList<Block> blocks = pixy.getCCC().getBlockCache();

      SmartDashboard.putNumber("Pixy X", blocks.get(0).getX());
      SmartDashboard.putNumber("Pixy Y", blocks.get(0).getY());
    }

    SmartDashboard.putNumber("Pixy num blocks", numBlocks);
  }

  @Override 
  public boolean isFinished() { 
    return false; 
  }
}
