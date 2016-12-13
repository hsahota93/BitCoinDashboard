package edu.temple.bitcoindashboard;

import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BalanceFragment extends Fragment {

    TextView priceText;
    EditText keyText;
    Button goButton;
    String enteredAddress;
    String baseURL = "http://btc.blockr.io/api/v1/address/balance/";
    String walletBalance;

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    String value;
    ArrayList<String> addresses;
    String fileName = "favoriteList";
    File file;
    /*  http://btc.blockr.io/api/v1/address/info/PUBLIC_KEY_GOES_HERE (For finding current balance)  */

    public BalanceFragment() {
        // Required empty public constructor
    } //End constructor


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_balance, container, false);

        //Finds the TextView, EditText, Button and ListView
        priceText = (TextView) v.findViewById(R.id.priceDisplay);
        keyText = (EditText) v.findViewById(R.id.enterKey);
        goButton = (Button) v.findViewById(R.id.goButton);
        listView = (ListView) v.findViewById(R.id.listView);

        File dir = getActivity().getFilesDir();
        file = new File(dir, fileName);

        if (file.exists()) {
            addresses = new ArrayList<String>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    addresses.add(line.toString());
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread networkThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            enteredAddress = keyText.getText().toString();
                            String urlString = baseURL + enteredAddress.toString();
                            URL url = new URL(urlString);

                            BufferedReader reader = new BufferedReader(  new InputStreamReader(  url.openStream()));

                            String jsonString = reader.readLine();

                            JSONObject root = new JSONObject(jsonString);
                            Message msg = Message.obtain();
                            msg.obj = root;
                            messageHandler.sendMessage(msg);

                            Message msg2 = Message.obtain();
                            msg2.obj = enteredAddress;
                            messageHandler2.sendMessage(msg2);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                networkThread.start();
            }
        });

        listView = (ListView) v.findViewById(R.id.listView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (addresses == null) {
                addresses = new ArrayList<String>();
            }

            arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, addresses );
        }

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {

                value = (String)adapter.getItemAtPosition(position);

                Thread networkThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            String urlString = baseURL + value;
                            URL url = new URL(urlString);

                            BufferedReader reader = new BufferedReader(  new InputStreamReader(  url.openStream()));

                            String jsonString = reader.readLine();

                            JSONObject root = new JSONObject(jsonString);
                            Message msg = Message.obtain();
                            msg.obj = root;
                            messageHandler.sendMessage(msg);

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                networkThread.start();

            }
        });

        return v;
    } //End onCreateView

    Handler messageHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            JSONObject root = (JSONObject) msg.obj;
            JSONObject data  = null;
            try {
                data = root.getJSONObject("data");
                walletBalance = data.getString("balance");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            priceText.setText(walletBalance);
        }
    };

    Handler messageHandler2 = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            String temp = (String) msg.obj;

            try {
                FileWriter writer = new FileWriter(file);

                for (int i=0; i < addresses.size(); i++){
                    writer.append( addresses.get(i)  );
                    writer.append('\n');
                }

                writer.append(temp);
                writer.close();

                addresses.add(temp);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

} //End PriceFragment