package edu.temple.bitcoindashboard;

import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class PriceFragment extends Fragment {

    TextView priceText;

    public PriceFragment() {
        // Required empty public constructor
    } //End constructor


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_price, container, false);
        priceText = (TextView) v.findViewById(R.id.priceDisplay);

        priceText.setText("Price goes Here");

        Thread priceThread = new Thread() {

            @Override
            public void run() {

                try {

                    URL url = new URL("http://btc.blockr.io/api/v1/address/balance/155eusBLPGkHzCEFkQChC2TZs3WiU3aMMT");
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(
                                    url.openStream()));

                    String tmpString = "";
                    String response = "";
                    while (tmpString != null) {
                        response.concat(tmpString);
                        response = response + tmpString;
                        tmpString = reader.readLine();
                    }

                    Message msg = Message.obtain();
                    msg.obj = response;
                    responseHandler.sendMessage(msg);
                } catch (Exception e) {}
            }

        };

        priceThread.run();

        return v;
    } //End onCreateView


    Handler responseHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            try {

                JSONObject blockObject = new JSONObject((String) msg.obj);
                JSONObject data = blockObject.getJSONObject("data");

                String balance = data.get("balance").toString();

                priceText.setText(balance);

                Toast.makeText(getContext(), balance, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {

            }

            return true;
        }
    });

} //End PriceFragment