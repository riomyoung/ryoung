package com.example.pckosek.master_detailexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetailFragment extends CommunicatorFragment implements View.OnClickListener{

    /* ------------------------*/
    /*    member variables     */

    private View mRootView;
    private EditText mEtOrderNum, mEtOrderCost, mEtOrderChoice;
    private Button mGoBack;
    private CommunicatorFragmentInterface mCallback;


    /* ------------------------*/
    /*    constructor, etc.    */

    public DetailFragment() {
    }


    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    /* ------------------------*/
    /*    lifecycle methods    */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.detail_fragment_main, container, false);

        mCallback = getInterface();

        mEtOrderNum = (EditText) mRootView.findViewById(R.id.et_order_number);
        mEtOrderCost= (EditText) mRootView.findViewById(R.id.et_order_cost);
        mEtOrderChoice = (EditText) mRootView.findViewById(R.id.et_order_choice);

        mGoBack = (Button) mRootView.findViewById(R.id.b_go_back);
        mGoBack.setOnClickListener(this);

        return mRootView;
    }

    /* ------------------------*/
    /*   helper methods        */

    public void updateDisplay(RestaurantOrder r){
        mEtOrderNum.setText( r.getOrderNumberString() );
        mEtOrderCost.setText(r.getOrderCostString() );
        mEtOrderChoice.setText( r.getChoice() );
    }

    /* ------------------------*/
    /*   interface methods     */

    @Override
    public void onClick(View v) {

        RestaurantOrder r = new RestaurantOrder(
                Integer.parseInt( mEtOrderNum.getText().toString() ),
                Float.parseFloat( mEtOrderCost.getText().toString() ),
                mEtOrderChoice.getText().toString()
        );

        mCallback.notifyUpdated(r);

    }
}