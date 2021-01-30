/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caloriescalculator.ifce.calcalc;

/**
 *
 * @author thechee
 */
public interface ICalCalc {
    public int calculateCalories(boolean isMale, int age, int weight, int height, int exerciseLevel, float weightGain);
}
