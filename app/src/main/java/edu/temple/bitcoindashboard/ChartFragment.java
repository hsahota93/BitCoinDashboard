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
import android.widget.SpinnerAdapter;

public class ChartFragment extends Fragment {

    WebView webView;
    Spinner dateSpinner;

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

        String[] timeInterval = {"1 Day", "5 Day"};
        ArrayAdapter dateAdapter = new ArrayAdapter(getContext(),
                R.layout.support_simple_spinner_dropdown_item, timeInterval);

        dateSpinner.setAdapter(dateAdapter);

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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

        //Loads the following URL by default
        webView.loadUrl("http://chart.yahoo.com/z?s=BTCUSD=X&t=1d");

        return v;
    }

}
