package com.example.pckosek.master_detailexample;

public class RestaurantOrders {

    public RestaurantOrder[] restaurantOrders;

    public RestaurantOrders() {
    }

    public RestaurantOrders(int numOrders) {
        restaurantOrders = new RestaurantOrder[numOrders];
        for (int i = 0; i < numOrders; i++) {
            restaurantOrders[i] = new RestaurantOrder(i, (float)(i*2.2), "food");
        }
    }

    public void setRestaurantOrder(RestaurantOrder restaurantOrder, int postition) {
        this.restaurantOrders[postition] = restaurantOrder;
    }

    public RestaurantOrder[] getRestaurantOrders() {
        return restaurantOrders;
    }

    public RestaurantOrder getItem(int position) {
        return restaurantOrders[position];
    }
}
