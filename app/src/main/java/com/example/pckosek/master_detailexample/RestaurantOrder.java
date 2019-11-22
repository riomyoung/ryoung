package com.example.pckosek.master_detailexample;

public class RestaurantOrder {

    /* ------------------------*/
    /*    member variables     */

    public int orderNumber;
    public float orderCost;
    public String choice;

    /* -------------------------------------- */
    /*  CONSTRUCTORS
    /* -------------------------------------- */

    public RestaurantOrder() {
    }

    public RestaurantOrder(int orderNumber, float orderCost, String choice) {
        this.orderNumber = orderNumber;
        this.orderCost = orderCost;
        this.choice = choice;
    }


    /* -------------------------------------- */
    /*  GETTERS AND SETTERS
    /* -------------------------------------- */

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getOrderNumberString() {
        return String.valueOf(orderNumber);
    }


    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public float getOrderCost() {
        return orderCost;
    }

    public String getOrderCostString() {
        return String.valueOf(orderCost);
    }


    public void setOrderCost(float orderCost) {
        this.orderCost = orderCost;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String item) {
        this.choice = choice;
    }

}

