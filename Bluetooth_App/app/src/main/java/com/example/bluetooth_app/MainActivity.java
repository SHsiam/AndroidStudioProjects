package com.example.bluetooth_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    TextView tv;
BluetoothAdapter ba;
Set<BluetoothDevice>paired_device;
ArrayAdapter<String>adapter;
ListView listView;
Button on,off,paired,find;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ba=BluetoothAdapter.getDefaultAdapter();
        tv=findViewById(R.id.tv);
        on=findViewById(R.id.on1);
        off=findViewById(R.id.off2);
        paired=findViewById(R.id.paired3);
        find=findViewById(R.id.search4);
        listView=findViewById(R.id.list);

        if(ba==null)
        {
            on.setEnabled(false);
            off.setEnabled(false);
            find.setEnabled(false);
            paired.setEnabled(false);
            tv.setText("Status: Not Supported");
            Toast.makeText(getApplicationContext(), "ur device does not support bluetooth", Toast.LENGTH_SHORT).show();
        }
        else
        {
            on.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    on(view);
                }
            });
        }
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                off(view);
            }
        });
        paired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPairedDevices(view);
            }
        });
        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                find(view);
            }
        });
    }
    public void on(View v)
    {
        if(!ba.isEnabled())
        {
            Intent turnonintent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnonintent,1);
            Toast.makeText(getApplicationContext(),"bluetooth is on ",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"bluetooth is Already On ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (ba.isEnabled()) {
                tv.setText("Status :Enabled");
            } else {
                tv.setText("Status :Disabled");

            }
        }

    }
    public void off(View view){
        ba.disable();
        tv.setText("disconnected");
        Toast.makeText(getApplicationContext(),"bluetooth turned off ",Toast.LENGTH_LONG).show();
    }
    public void showPairedDevices(View view) {
        paired_device = ba.getBondedDevices();
        for (BluetoothDevice bd : paired_device) {
            adapter.add(bd.getName() + "\n" + bd.getAddress());
            Toast.makeText(getApplicationContext(), "show paired devices ", Toast.LENGTH_LONG).show();
        }
    }
        BroadcastReceiver breceiver =(new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action=intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action))
                {
                    BluetoothDevice device=intent.getParcelableExtra
                            (
                                    BluetoothDevice.EXTRA_DEVICE);
                    adapter.add(device.getName()+"\n"+device.getAddress());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        public void find(View v)
        {
            if(ba.isDiscovering())
            {
                ba.cancelDiscovery();
            }
            else
            {
                adapter.clear();
                ba.startDiscovery();
                registerReceiver(breceiver,new IntentFilter(BluetoothDevice.ACTION_FOUND));
            }
        }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(breceiver);
    }
}