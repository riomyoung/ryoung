package com.example.pckosek.master_detailexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MasterFragment extends CommunicatorFragment implements View.OnClickListener{

    /* ------------------------*/
    /*    member variables     */

    private View mRootView;
    private TextView mTVItem1, mTVItem2, mTVItem3, mTVItem4;
    private CommunicatorFragmentInterface mCallback;
    private RestaurantOrders mRestaurantOrders;


    /* ------------------------*/
    /*    constructor, etc.    */

    public MasterFragment() {
    }


    public static MasterFragment newInstance(String serializedObject) {
        MasterFragment fragment = new MasterFragment();

        Bundle args = new Bundle();
        args.putString("SERIALIZED_OBJECT", serializedObject);
        fragment.setArguments(args);
        return fragment;
    }

    /* ------------------------*/
    /*    lifecycle methods    */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.master_fragment_main, container, false);

        mCallback = getInterface();

        mTVItem1 = (TextView) mRootView.findViewById(R.id.tv_item_1);
        mTVItem2 = (TextView) mRootView.findViewById(R.id.tv_item_2);
        mTVItem3 = (TextView) mRootView.findViewById(R.id.tv_item_3);
        mTVItem4 = (TextView) mRootView.findViewById(R.id.tv_item_4);

        mTVItem1.setOnClickListener(this);
        mTVItem2.setOnClickListener(this);
        mTVItem3.setOnClickListener(this);
        mTVItem4.setOnClickListener(this);


        String serializedObject = getArguments().getString("SERIALIZED_OBJECT");

        // create a local copy of the orders
        Gson gson = new GsonBuilder().create();
        RestaurantOrders r = gson.fromJson(serializedObject, RestaurantOrders.class);

        // update data set and display
        updateDataSet(r);
        updateDisplay();

        return mRootView;
    }

    /* ------------------------*/
    /*   helper methods        */

    public void updateDataSet(RestaurantOrders r) {
        mRestaurantOrders = r;
    }

    public void updateDisplay() {
        mTVItem1.setText( mRestaurantOrders.getRestaurantOrders()[0].getChoice() );
        mTVItem2.setText( mRestaurantOrders.getRestaurantOrders()[1].getChoice() );
        mTVItem3.setText( mRestaurantOrders.getRestaurantOrders()[2].getChoice() );
        mTVItem4.setText( mRestaurantOrders.getRestaurantOrders()[3].getChoice() );
    }


    /* ------------------------*/
    /*   interface methods     */

    @Override
    public void onClick(View v) {

        int itemNo = -1;

        switch (v.getId() ) {
            case R.id.tv_item_1 : itemNo = 0;
                break;
            case R.id.tv_item_2 : itemNo = 1;
                break;
            case R.id.tv_item_3 : itemNo = 2;
                break;
            case R.id.tv_item_4 : itemNo = 3;
                break;
             default:
                 break;
        }
        mCallback.notifySelected(itemNo);

    }

}