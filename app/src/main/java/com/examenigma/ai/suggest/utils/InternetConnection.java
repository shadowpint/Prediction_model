package com.examenigma.ai.suggest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

/**
 * @author Neeraj Giri
 */
public class InternetConnection {

	/** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
	public static boolean checkConnection(@NonNull Context context) {
		return  ((ConnectivityManager) context.getSystemService
				(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
	}
}
