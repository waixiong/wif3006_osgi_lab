/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caloriescalculator.ifce.cart;

import java.util.List;
import caloriescalculator.entity.Food;

/**
 *
 * @author thechee
 */
public interface ICart {
    public List<Food> listFoods();
    public void addFood(Food food);
    public void removeFood(Food food);
    public void clearCart();
    public int getTotalCal();
}
