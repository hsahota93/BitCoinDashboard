package edu.temple.bitcoindashboard;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class NavFragment extends Fragment implements View.OnClickListener {

    Button viewPrice;                       //Declaring a Button
    Button priceChart;                      //Declaring a Button
    Button blockInfo;                       //Declaring a Button
    Button checkBalance;                    //Declaring a Button
    ChartFragment chartFrag;                //Declaring a ChartFragment
    BalanceFragment balanceFrag;            //Declaring a PriceFragment
    ViewPriceFragment priceFrag;            //Declaring a ViewPriceFragment
    boolean twoPaneMode;                    //Boolean to check if in landscape mode or not
    FragmentManager fm;                     //Declaring a FragmentManager

    public NavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_nav, container, false);

        //Assigning buttons
        viewPrice = (Button) v.findViewById(R.id.viewPriceButton);
        priceChart = (Button) v.findViewById(R.id.priceChartButton);
        blockInfo = (Button) v.findViewById(R.id.blockInfoButton);
        checkBalance  = (Button) v.findViewById(R.id.checkBalance);

        //Setting the onClickListeners for each button
        viewPrice.setOnClickListener(this);
        priceChart.setOnClickListener(this);
        blockInfo.setOnClickListener(this);
        checkBalance.setOnClickListener(this);

        return v;
    }

    //Called in MainActivity so Fragment knows if its in landscape mode or not
    public void checkOrientation(boolean mode) {

        twoPaneMode = mode;
    }

    //OnClick listener for all buttons. Used switch statements to handle different buttons
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.viewPriceButton:          //If user clicks the "View Price" button

                priceFrag = new ViewPriceFragment();
                fm = getFragmentManager();

                if(twoPaneMode) {

                    fm.beginTransaction()
                            .replace(R.id.detailsPane, priceFrag)
                            .addToBackStack("1")
                            .commit();
                    fm.executePendingTransactions();

                    priceFrag.getCurrentValue();
                } else {

                    fm.beginTransaction()
                            .replace(R.id.navPane, priceFrag)
                            .addToBackStack("1")
                            .commit();
                    fm.executePendingTransactions();

                    priceFrag.getCurrentValue();
                }
                break;

            case R.id.priceChartButton:         //If user clicks the "Price Chart" button

                chartFrag = new ChartFragment();
                fm = getFragmentManager();

                if(twoPaneMode) {

                    fm.beginTransaction()
                            .replace(R.id.detailsPane, chartFrag)
                            .addToBackStack("1")
                            .commit();
                    fm.executePendingTransactions();
                } else {

                    fm.beginTransaction()
                            .replace(R.id.navPane, chartFrag)
                            .addToBackStack("1")
                            .commit();
                    fm.executePendingTransactions();
                }

                break;

            case R.id.blockInfoButton:          //If the user clicks the "Block Info" button

                Toast.makeText(v.getContext(), "View Block info", Toast.LENGTH_SHORT).show();
                break;

            case R.id.checkBalance:             //If the user clicks the "Check Balance" button

                fm = getFragmentManager();
                balanceFrag = new BalanceFragment();

                //Checks orientation of the device, replaces detailsPane if in landscape mode
                //Replaces navPane if in portrait mode
                if(twoPaneMode) {

                    fm.beginTransaction()
                            .replace(R.id.detailsPane, balanceFrag)
                            .addToBackStack("1")
                            .commit();
                    fm.executePendingTransactions();

                    balanceFrag.getPriceData();           //Updates the TextView with wallet balance
                } else {

                    fm.beginTransaction()
                            .replace(R.id.navPane, balanceFrag)
                            .addToBackStack("1")
                            .commit();
                    fm.executePendingTransactions();

                    balanceFrag.getPriceData();           //Updates the TextView with wallet balance
                }

                break;

            default:

                Toast.makeText(v.getContext(), "Default", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
