package edu.temple.bitcoindashboard;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Spinner;

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
        View v = inflater.inflate(R.layout.fragment_details, container, false);

        webView = (WebView) v.findViewById(R.id.webView);
        dateSpinner = (Spinner) v.findViewById(R.id.dateSelector);

        //Following two lines enables Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Sets the WebViewClient to a new WebViewClient
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("http://chart.yahoo.com/z?s=BTCUSD=X&t=1d");

        return v;
    }

}
