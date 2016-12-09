package edu.temple.bitcoindashboard;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class ChartFragment extends Fragment {

    WebView webView;
    Spinner dateSpinner;
    String yahooWebsite = "http://chart.yahoo.com/z?s=BTCUSD=X&t=";

    public ChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chart, container, false);

        //Assign the WebView and the Spinner
        webView = (WebView) v.findViewById(R.id.webView);
        dateSpinner = (Spinner) v.findViewById(R.id.dateSelector);

        //The options in the spinner
        String[] timeInterval = {"1 Day", "5 Day"};

        //Creating the adapter for the spinner
        ArrayAdapter dateAdapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item, timeInterval);

        //Sets 'dateAdapter' as the adapter for 'dateSpinner'
        dateSpinner.setAdapter(dateAdapter);

        //What happens when an item is selected
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Get what was selected and pass it as a string to 'getTimeInterval'
                getTimeInterval(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Following two lines enables Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Sets the WebViewClient to a new WebViewClient
        webView.setWebViewClient(new WebViewClient());

        //To make viewing the chart easier
        webView.setInitialScale(200);

        return v;
    }

    private void getTimeInterval(String string) {

        //Depending on what the user selected in the spinner '1d' or '5d' is added
        //to the website and then loaded
        switch (string) {

            case "1 Day":
                webView.loadUrl(yahooWebsite + "1d");
                break;
            case "5 Day":
                webView.loadUrl(yahooWebsite + "5d");
                break;

        } //End switch
    } //End getTimeInterval
} //End ChartFragment
