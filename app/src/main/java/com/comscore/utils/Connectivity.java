package com.comscore.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"NewApi"})
public class Connectivity {
    private static HttpClient a = null;

    private static HttpClient a() {
        try {
            SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
            socketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            basicHttpParams.setBooleanParameter("http.protocol.expect-continue", false);
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schemeRegistry.register(new Scheme("https", socketFactory, 443));
            return new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
        } catch (Exception e) {
            CSLog.e("comScore", e.getMessage());
            return new DefaultHttpClient();
        }
    }

    public static String getCurrentSSID(Context context) {
        WifiInfo connectionInfo;
        if (Permissions.check(context, "android.permission.ACCESS_WIFI_STATE").booleanValue() && ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(1).isConnected() && (connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo()) != null) {
            return connectionInfo.getSSID();
        }
        return null;
    }

    public static HttpClient getHttpClient() {
        if (a == null) {
            a = a();
        }
        return a;
    }

    public static boolean isConnectBluetooth(Context context) {
        if (!Permissions.check(context, "android.permission.ACCESS_NETWORK_STATE").booleanValue() || Build.VERSION.SDK_INT < 13) {
            return false;
        }
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(7);
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isConnectEthernet(Context context) {
        if (!Permissions.check(context, "android.permission.ACCESS_NETWORK_STATE").booleanValue() || Build.VERSION.SDK_INT < 13) {
            return false;
        }
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(9);
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isConnectedMobile(Context context) {
        if (!Permissions.check(context, "android.permission.ACCESS_NETWORK_STATE").booleanValue()) {
            return false;
        }
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(0);
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isConnectedWiFi(Context context) {
        if (!Permissions.check(context, "android.permission.ACCESS_NETWORK_STATE").booleanValue()) {
            return false;
        }
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(1);
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isDataConnected(Context context) {
        if (!Permissions.check(context, "android.permission.READ_PHONE_STATE").booleanValue()) {
            return false;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        return telephonyManager.getDataActivity() == 3 || telephonyManager.getDataActivity() == 1 || telephonyManager.getDataActivity() == 2;
    }

    public static boolean isEmulator() {
        return "sdk".equals(Build.PRODUCT);
    }
}
