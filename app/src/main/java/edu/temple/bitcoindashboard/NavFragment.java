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
    PriceFragment priceFrag;                //Declaring a PriceFragment
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


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.viewPriceButton:

                Toast.makeText(v.getContext(), "View Price", Toast.LENGTH_SHORT).show();

                fm = getFragmentManager();
                priceFrag = new PriceFragment();

                if(twoPaneMode) {

                    fm.beginTransaction()
                            .replace(R.id.detailsPane, priceFrag)
                            .addToBackStack("1")
                            .commit();
                    fm.executePendingTransactions();
                } else {

                    fm.beginTransaction()
                            .replace(R.id.navPane, priceFrag)
                            .addToBackStack("1")
                            .commit();
                    fm.executePendingTransactions();
                }

                break;

            case R.id.priceChartButton:

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

                Toast.makeText(v.getContext(), "View Price Chart", Toast.LENGTH_SHORT).show();
                break;

            case R.id.blockInfoButton:

                Toast.makeText(v.getContext(), "View Block info", Toast.LENGTH_SHORT).show();
                break;

            case R.id.checkBalance:

                Toast.makeText(v.getContext(), "Check Balance", Toast.LENGTH_SHORT).show();
                break;

            default:

                Toast.makeText(v.getContext(), "Default", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
