package edu.temple.bitcoindashboard;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ViewPriceFragment extends Fragment {

    TextView priceText;
    //http://btc.blockr.io/api/v1/coin/info

    public ViewPriceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_price, container, false);
        priceText = (TextView) v.findViewById(R.id.priceView);

        priceText.setText("Loading...");

        return v;
    }

    public void getCurrentValue() {

        Thread getValue = new Thread() {

            @Override
            public void run() {

                try {

                    URL url = new URL("http://btc.blockr.io/api/v1/coin/info");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

                    String tmpString;
                    String response = "";

                    tmpString = reader.readLine();

                    while (tmpString != null) {

                        response = response + tmpString;
                        tmpString = reader.readLine();
                    }

                    Message msg = Message.obtain();
                    msg.obj = response;
                    responseHandler.sendMessage(msg);
                } catch (Exception e) {}

            }
        };

        getValue.start();
    }

    Handler responseHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            try {

                JSONObject blockObject = new JSONObject((String) msg.obj);
                JSONObject data = blockObject.getJSONObject("data");
                JSONObject markets = data.getJSONObject("markets");
                JSONObject coinbase = markets.getJSONObject("coinbase");

                String balance = coinbase.get("value").toString();

                priceText.setText("$" + balance);
             } catch (JSONException e) {}

            return true;
        }
    });

}
