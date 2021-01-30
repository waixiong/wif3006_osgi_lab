/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caloriescalculator.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author thechee
 */
@Entity
@Table(name="foods", catalog="CaloriesCalculatorDB")
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="name")
    private String name;
    @Column(name="calories")
    private Integer calories;
    @Column(name="quantity")
    private Integer quantity;
    
    public Food(String name, Integer calories) {
        this.name = name;
        this.calories = calories;
        quantity = 1;
    }
    
    public Food(String name, Integer calories, Integer quantity) {
        this.name = name;
        this.calories = calories;
        this.quantity = quantity;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public void increaseQuantityByOne() {
        quantity++;
    }
    
    public void decreaseQuantityByOne() {
        quantity--;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Food)) {
            return false;
        }
        Food other = (Food) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Food Name:"+name+", Calories: "+calories+", Quantity: "+quantity;
    }
    
}
