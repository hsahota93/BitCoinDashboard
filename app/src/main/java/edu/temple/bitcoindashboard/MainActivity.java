package edu.temple.bitcoindashboard;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm;                     //Declaring the FragmentManager
    NavFragment navFragment;                //Declaring a NavFragment
    ChartFragment chartFrag;                //Declaring a DetailsFragment
    boolean twoPaneMode;

    /*  http://btc.blockr.io/api/v1/address/info/PUBLIC_KEY_GOES_HERE (For finding current balance)  */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gets the FragmentManager
        fm = getFragmentManager();

        //If true the phone is in landscape
        twoPaneMode = (findViewById(R.id.detailsPane)) != null;

        //If you find the detailsPane then replace it with a DetailsFragment
        if (twoPaneMode) {

            chartFrag = new ChartFragment();

            fm.beginTransaction()
                    .replace(R.id.detailsPane, new Fragment())
                    .addToBackStack("1")
                    .commit();
            fm.executePendingTransactions();
        }

        //Creates a new NavFragment
        navFragment = new NavFragment();
        navFragment.checkOrientation(twoPaneMode);

        //Replaces the FrameLayout with a NavFragment
        fm.beginTransaction()
                .replace(R.id.navPane, navFragment)
                .commit();
        fm.executePendingTransactions();
    } //End onCreate
} //End Activity

