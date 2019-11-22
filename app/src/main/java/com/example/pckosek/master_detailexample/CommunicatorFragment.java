package com.example.pckosek.master_detailexample;

import android.content.Context;
import android.support.v4.app.Fragment;

public class CommunicatorFragment extends Fragment {

    /* ------------------------*/
    /*    member variables     */

    public final static String TAG = "COMMUNICATOR FRAGMENT";
    private CommunicatorFragmentInterface mCallback;


    /* ------------------------*/
    /*    constructor          */

    public CommunicatorFragment() {
    }


    /* ------------------------*/
    /*    lifecycle methods    */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (CommunicatorFragment.CommunicatorFragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement CommunicatorFragmentInterface");
        }
    }

    CommunicatorFragmentInterface getInterface() {
        return mCallback;
    }


    /* ------------------------*/
    /*   interface definition  */

    public interface CommunicatorFragmentInterface {
        void notifySelected(int ItemNumber);
        void notifyUpdated(RestaurantOrder r);
    }
}