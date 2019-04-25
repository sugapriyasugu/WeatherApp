package sugaselvaraj.paypal.com.myweatherapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    public static MySingleton mInstance;

    public RequestQueue mRequestQueue;

    public MySingleton(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized MySingleton getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public  RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
