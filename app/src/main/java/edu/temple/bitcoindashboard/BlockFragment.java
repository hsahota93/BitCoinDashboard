package edu.temple.bitcoindashboard;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class BlockFragment extends Fragment {

    TextView blockNum;
    TextView hashNum;
    TextView merkleRoot;
    TextView blockConfirm;
    TextView blockSize;
    TextView blockDifficulty;
    TextView nextBlock;
    TextView prevBlock;

    EditText searchBlock;
    Button searchButton;

    String baseURL = "http://btc.blockr.io/api/v1/block/info/";

    public BlockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_block, container, false);

        blockNum = (TextView) v.findViewById(R.id.blockNum);
        hashNum = (TextView) v.findViewById(R.id.hashNum);
        merkleRoot = (TextView) v.findViewById(R.id.merkleRoot);
        blockConfirm = (TextView) v.findViewById(R.id.blockConfirm);
        blockSize = (TextView) v.findViewById(R.id.blockSize);
        blockDifficulty = (TextView) v.findViewById(R.id.blockDifficulty);
        nextBlock = (TextView) v.findViewById(R.id.nextBlock);
        prevBlock = (TextView) v.findViewById(R.id.prevBlock);
        searchBlock = (EditText) v.findViewById(R.id.searchBlock);
        searchButton = (Button) v.findViewById(R.id.searchButton);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String enteredBlockNumber = searchBlock.getText().toString();

                Thread blockThread = new Thread() {

                    @Override
                    public void run() {

                        try {

                            URL url = new URL(baseURL + enteredBlockNumber);
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
                        } catch (Exception e) {


                        }
                    }
                };

                blockThread.start();

            }
        });

        return v;
    }

    Handler responseHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            try {

                JSONObject blockObject = new JSONObject((String) msg.obj);
                JSONObject data = blockObject.getJSONObject("data");

                blockNum.setText(data.get("nb").toString());
                hashNum.setText(data.get("hash").toString());
                merkleRoot.setText(data.get("merkleroot").toString());
                blockConfirm.setText(data.get("confirmations").toString());
                blockSize.setText(data.get("size").toString());
                blockDifficulty.setText(data.get("difficulty").toString());
                nextBlock.setText(data.get("next_block_nb").toString());
                prevBlock.setText(data.get("prev_block_nb").toString());

            } catch (JSONException e) {}

            return true;
        }
    });


}
