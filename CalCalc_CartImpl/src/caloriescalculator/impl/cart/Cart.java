/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caloriescalculator.impl.cart;

/**
 *
 * @author thechee
 */

import caloriescalculator.ifce.cart.ICart;
import java.util.*;
import caloriescalculator.entity.Food;


public class Cart implements ICart {
    List<Food> foods = new ArrayList<Food>();

    @Override
    public List<Food> listFoods() {
        return foods;
    }

    @Override
    public void addFood(Food food) {
        int index = -1;
        for(int i=0; i<foods.size(); i++) {
            if(foods.get(i).getName().equals(food.getName())) {
                index = i;
                break;
            }
        }
        if(index<0) {
            food.setQuantity(1);
            foods.add(food);
        } else {
            Food existingFood = foods.get(index);
            existingFood.increaseQuantityByOne();
            foods.set(index, existingFood);
        }
    }

    @Override
    public void removeFood(Food food) {
        for(int i=0; i<foods.size(); i++) {
            if(foods.get(i).getName().equals(food.getName())) {
                Food existingFood = foods.get(i);
                if(existingFood.getQuantity()>1) {
                    existingFood.decreaseQuantityByOne();
                    foods.set(i, existingFood);
                } else {
                    foods.remove(i);
                }
                break;
            }
        }
    }

    @Override
    public void clearCart() {
        foods.clear();
    }

    @Override
    public int getTotalCal() {
        int total = 0;
        for(Food food : foods) {
            total += food.getCalories()*food.getQuantity();
        }
        return total;
    }
}
