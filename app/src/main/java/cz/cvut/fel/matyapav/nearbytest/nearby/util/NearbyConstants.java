package cz.cvut.fel.matyapav.nearbytest.nearby.util;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class NearbyConstants {

    private NearbyConstants(){}

    public static final String EMPTY_MAC_ADDRESS = "00:00:00:00:00:00";
    static final String MAC_REGULAR_EXPRESSION = "^%s\\s+0x1\\s+0x2\\s+([:0-9a-fA-F]+)\\s+\\*\\s+\\w+$";
    static final String ARP_LOCATION = "/proc/net/arp";

    public static final String BLUETOOTH_MISSING_MSG = "This device doesn't support bluetooth.";

    public static final String ADDITIONAL_INFO_BT_MAJOR_CLASS = "bluetooth-major-class";
}