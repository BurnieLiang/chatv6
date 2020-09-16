package com.github.BurnieLiang.chatv6.core;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author Boning Liang
 * @date 2020-09-16 14:30:46
 */
public class IPv6 {

    public static String get() {
        InetAddress inetAddress = null;
        InetAddress localIP = null;
        try {
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            outer:
            while (networkInterfaceEnumeration.hasMoreElements()) {
                Enumeration<InetAddress> inetAddressEnumeration = networkInterfaceEnumeration.nextElement().getInetAddresses();
                while (inetAddressEnumeration.hasMoreElements()) {
                    inetAddress = inetAddressEnumeration.nextElement();
                    if (inetAddress instanceof Inet6Address) {
                        if (!inetAddress.isSiteLocalAddress()
                                && !inetAddress.isLoopbackAddress()
                                && inetAddress.getHostAddress().indexOf(":") == -1) {
                            break outer;
                        } else if (inetAddress.isSiteLocalAddress()
                                && !inetAddress.isLoopbackAddress()
                                && inetAddress.getHostAddress().indexOf(":") == -1) {
                            localIP = inetAddress;
                        }
                        break outer;
                    }
                    inetAddress = null;
                }
            }
            if (inetAddress != null) {
                String ipAddr = inetAddress.getHostAddress();
                int index = ipAddr.indexOf("%");
                if (index > 0) {
                    ipAddr = ipAddr.substring(0, index);
                }
                return ipAddr;
            } else if (localIP != null) {
                String ipAddr = localIP.getHostAddress();
                int index = ipAddr.indexOf("%");
                if (index > 0) {
                    ipAddr = ipAddr.substring(0, index);
                }
                return ipAddr;
            } else {
                return "";
            }

        } catch (SocketException e) {
            return "";
        }

    }

    private static boolean isReservedAddress(InetAddress inetAddress) {
        if (inetAddress.isAnyLocalAddress() || inetAddress.isLinkLocalAddress() || inetAddress.isLoopbackAddress()) {
            return true;
        }
        return false;
    }

}
