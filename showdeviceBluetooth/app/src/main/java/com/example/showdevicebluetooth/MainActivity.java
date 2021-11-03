package com.example.showdevicebluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private DeviceListAdapter mAdapter;
    private ArrayList<BluetoothDevice> mDeviceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDeviceList		= getIntent().getExtras().getParcelableArrayList
                ("device.list");
        mListView		=  findViewById(R.id.lv_paired);
        mAdapter		= new DeviceListAdapter(this);
        mAdapter.setData(mDeviceList);
        mAdapter.setListener(new DeviceListAdapter.OnPairButtonClickListener() {
            @Override
            public void onPairButtonClick(int position) {
                BluetoothDevice device = mDeviceList.get(position);
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    unpairDevice(device);
                } else {
                    Toast.makeText(getApplicationContext(),

                            "pairing", Toast.LENGTH_SHORT).show();

                    pairDevice(device);
                }
            }
        });

        mListView.setAdapter(mAdapter);

        registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.
                ACTION_BOND_STATE_CHANGED));
    }

    @Override
    public void onDestroy() {

        unregisterReceiver(mPairReceiver);

        super.onDestroy();
    }


    private void pairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod
                    ("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final BroadcastReceiver mPairReceiver =
            new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();

                    if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                        final int state 		= intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE,
                                BluetoothDevice.ERROR);
                        final int prevState	= intent.getIntExtra(BluetoothDevice.
                                EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

                        if (state == BluetoothDevice.BOND_BONDED && prevState ==
                                BluetoothDevice.BOND_BONDING) {

                            Toast.makeText(getApplicationContext(),

                                    "paired", Toast.LENGTH_SHORT).show();
                        } else if (state == BluetoothDevice.BOND_NONE && prevState ==
                                BluetoothDevice.BOND_BONDED){
                            Toast.makeText(getApplicationContext(),

                                    "unpaired", Toast.LENGTH_SHORT).show();

                        }

                        mAdapter.notifyDataSetChanged();
                    }
                }
            };
}