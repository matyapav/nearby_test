package cz.cvut.fel.matyapav.nearbytest.nearby.finder;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearby.model.enums.DeviceType;
import cz.cvut.fel.matyapav.nearbytest.nearby.util.BluetoothUtil;
import cz.cvut.fel.matyapav.nearbytest.nearby.util.NearbyConstants;

import static cz.cvut.fel.matyapav.nearbytest.nearby.util.AdditionalInfoNames.*;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class BTDevicesFinder extends AbstractNearbyDevicesFinder {

    private BluetoothAdapter btAdapter;

    public BTDevicesFinder(Activity activity) {
        super(activity);
        BluetoothManager btManager = (BluetoothManager) getActivity().getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
    }

    @Override
    public void startFindingDevices() {
        if (btAdapter == null) {
            Toast.makeText(getActivity(), NearbyConstants.BLUETOOTH_MISSING_MSG, Toast.LENGTH_SHORT).show();
        } else {
            if (btAdapter.isEnabled()) {
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                getActivity().registerReceiver(mReceiver, filter);
                if (btAdapter.isDiscovering()) {
                    btAdapter.cancelDiscovery();
                }
                btAdapter.startDiscovery();
            }
        }
    }

    @Override
    public List<Device> stopFindingAndCollectDevices() {
        if (btAdapter.isDiscovering()) {
            btAdapter.cancelDiscovery();
        }
        return getFoundDevices();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Device device = new Device(bluetoothDevice.getName(), bluetoothDevice.getAddress(), DeviceType.BLUETOOTH_DISCOVERED);
                device.addAdditionalInformation(ADDITIONAL_INFO_BT_MAJOR_CLASS, BluetoothUtil.getBluetoothMajorDeviceClass(bluetoothDevice));
                device.addAdditionalInformation(ADDITIONAL_INFO_BT_CLASS, BluetoothUtil.getBluetoothDeviceClass(bluetoothDevice));
                deviceFound(device);
            }
        }
    };
}


