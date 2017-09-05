package com.streamanalyze.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sa.engine.client.SaClient;
import com.sa.engine.client.consumers.GenericDataConsumer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView label = (TextView)findViewById(R.id.label);
        SaClient saClient = new SaClient.Builder(getApplication()).build();
        saClient.postCall("hello", new Object[]{"Streaming world"}, new GenericDataConsumer() {
            @Override
            public void onData(final Object[] objects) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        label.setText(objects[0].toString());
                    }
                });
            }
        });
    }
}
