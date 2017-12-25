package cz.cvut.fel.matyapav.nearbytest.nearby.finder;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

import cz.cvut.fel.matyapav.nearbytest.nearby.model.Device;
import cz.cvut.fel.matyapav.nearbytest.nearby.model.enums.DeviceType;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyNetworksFinder extends AbstractNearbyDevicesFinder {

    private WifiManager wifiManager;
    private boolean active;

    public NearbyNetworksFinder(Activity activity) {
        super(activity);
        wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    public void startFindingDevices() {
        List<ScanResult> scanResults = wifiManager.getScanResults();
        Device device;
        active = true;
        for (int i = 0; i < scanResults.size(); i++) {
            if (!active) {
                break;
            }
            ScanResult scanresult = scanResults.get(i);
            device = new Device(scanresult.SSID, scanresult.BSSID, DeviceType.WIFI_NETWORK);
            deviceFound(device);
        }
    }

    @Override
    public List<Device> stopFindingAndCollectDevices() {
        this.active = false;
        return getFoundDevices();
    }


}
