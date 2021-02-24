// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Util;

import java.util.ArrayList;
import java.util.Arrays;

import frc.robot.drivers.Pixy;
import frc.robot.drivers.PowerCell;

public class PatternRecognition {

    public enum Pattern {
        RED_A(0),
        RED_B(1),
        BLUE_A(2),
        BLUE_B(3);
        

        private PowerCell[][] PatternArray = {
            {PowerCell.getMarkerCell(94, -54, 2640), PowerCell.getMarkerCell(-31, -48, 2538)},
            {PowerCell.getMarkerCell(-45, -74, 3696), PowerCell.getMarkerCell(85, -14, 1840)},
            {PowerCell.getMarkerCell(123, -79, 3290), PowerCell.getMarkerCell(-40, -26, 1924), PowerCell.getMarkerCell(53, 47, 132)},
            {PowerCell.getMarkerCell(8, -85, 2660), PowerCell.getMarkerCell(-3, 5, 840), PowerCell.getMarkerCell(-11, 48, 242)}
        };

        Pattern(int newNumber) {
            number = newNumber;
        }

        private int number;

        public PowerCell[] getPattern() {
            return PatternArray[number];
        }

    }

    public static Pattern getPattern() {
        Pattern results = Pattern.BLUE_A;
        double finalConfidence = 0;
        ArrayList<PowerCell> actualReading = Pixy.getInstance().getPowerCells();

        if(actualReading == null) {
            return results;
        }

        for(Pattern curPattern: Pattern.values() ){
            double currentConfidence = checkPattern(curPattern, actualReading);

            if(currentConfidence > finalConfidence) {
                finalConfidence = currentConfidence;
                results = curPattern;
            }
        }

        return results;
       
    }

    private static double checkPattern(Pattern pattern, ArrayList<PowerCell> actual) {
        ArrayList<PowerCell> patternArr = new ArrayList<PowerCell>(Arrays.asList(pattern.getPattern()));
        ArrayList<PowerCell> actualArr = (ArrayList) actual.clone();

        if(patternArr.size() != actualArr.size()) {
            return 0.0;
        }

        double finalConfidence = 0;
        int avarageDivide = patternArr.size();

        if(avarageDivide == 0) {
            return 0;
        }

        for(int i = 0; i < patternArr.size(); i++) {

            double closestConfidence = 0;
            int closestIndex = 0;
            for(int j = 0; j < actualArr.size(); j++) {

                double currentConfidence = actualArr.get(0).compare(patternArr.get(j), true, true, false);

                if(currentConfidence > closestConfidence) {
                    closestConfidence = currentConfidence;
                    closestIndex = j;
                }
            }

            finalConfidence += closestConfidence;
            patternArr.remove(closestIndex);
            actualArr.remove(0);
        }

        return finalConfidence / avarageDivide;
    }
}