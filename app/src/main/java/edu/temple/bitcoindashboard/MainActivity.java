package edu.temple.bitcoindashboard;


import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    FragmentManager fm;                     //Declaring the FragmentManager
    NavFragment navFragment;                //Declaring a NavFragment
    ViewPriceFragment viewPriceFrag;        //Declaring a ViewPriceFragment
    boolean twoPaneMode;                    //Used to check if phone is in landscape or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gets the FragmentManager
        fm = getFragmentManager();

        //If true the phone is in landscape
        twoPaneMode = (findViewById(R.id.detailsPane)) != null;

        //If you find the detailsPane FrameLayout then replace it with a ChartFragment
        if (twoPaneMode) {

            viewPriceFrag = new ViewPriceFragment();        //Creates a new ChartFragment

            //Replaces the detailsPane with a ChartFragment
            fm.beginTransaction()
                    .replace(R.id.detailsPane, viewPriceFrag)
                    .addToBackStack("1")
                    .commit();
            fm.executePendingTransactions();

            viewPriceFrag.getCurrentValue();
        }

        //Creates a new NavFragment then tells the NavFragment if
        //the phone is in landscape mode or not
        navFragment = new NavFragment();
        navFragment.checkOrientation(twoPaneMode);

        //Replaces the navPane FrameLayout with a NavFragment
        fm.beginTransaction()
                .replace(R.id.navPane, navFragment)
                .commit();
        fm.executePendingTransactions();
    } //End onCreate
} //End Activity

