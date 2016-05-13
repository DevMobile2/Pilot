package com.amzur.pilot.utilities;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtils {

	
public static boolean isInternetOn(Context con) {
    	
    	// get Connectivity Manager object to check connection
		ConnectivityManager connec =  
                       (ConnectivityManager)con.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		   // Check for network connections
			if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
			     connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
			     connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
			     connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
			   
				// if connected with internet
				
			    //Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
			    return true;
			    
			} else if ( 
              connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
              connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
			  
				//Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
			    return false;
			}
		  return false;
		}
}
