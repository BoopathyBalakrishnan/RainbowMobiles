package com.rainbowmobiles.suggest;

import android.content.Context;
import android.net.ConnectivityManager;
/**
 * 
 * @author Boopathy B
 *
 */
public class Utils {
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkInternetConnection(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager.getActiveNetworkInfo() != null
				&& connectivityManager.getActiveNetworkInfo().isAvailable()
				&& connectivityManager.getActiveNetworkInfo().isConnected()) {
			return true;
		} else {
			return false;
		}
	}
}
