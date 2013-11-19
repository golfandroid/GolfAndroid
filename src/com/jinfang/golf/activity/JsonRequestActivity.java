package com.jinfang.golf.activity;

import org.json.JSONObject;

import com.jinfang.golf.GolfApplication;
import com.jinfang.golf.R;
import com.jinfang.golf.network.Request.Method;
import com.jinfang.golf.network.RequestQueue;
import com.jinfang.golf.network.Response.ErrorListener;
import com.jinfang.golf.network.Response.Listener;
import com.jinfang.golf.network.VolleyError;
import com.jinfang.golf.network.toolbox.JsonObjectRequest;
import com.jinfang.golf.network.toolbox.Volley;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;



public class JsonRequestActivity extends Activity implements Listener<JSONObject>,ErrorListener{
	private TextView text;
	private static final String URL = "http://pipes.yahooapis.com/pipes/pipe.run?_id=giWz8Vc33BG6rQEQo_NLYQ&_render=json";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_json);
		text =(TextView) findViewById(R.id.text);

		RequestQueue queue = GolfApplication.getInstance().getRequestQueue();
		JsonObjectRequest jsonObjectRequest = 
				new JsonObjectRequest(Method.GET,URL,null,this,this);
		queue.add(jsonObjectRequest);
	}
	
	@Override
	public void onResponse(JSONObject arg0) {
		text.setText(arg0.toString());
	}

	@Override
	public void onErrorResponse(VolleyError arg0) {
		//BasicNetwork.performRequest: 
		//Unexpected response code 400 for 
		//https://www.googleapis.com/customsearch/v1?key=AIzaSyBmSXUzVZBKQv9FJkTpZXn0dObKgEQOIFU&;cx=014099860786446192319:t5mr0xnusiy&q=AndroidDev&alt=json&searchType=image
		
		Log.e("===================", "=========="+arg0.getMessage());
		text.setText(arg0.getMessage());
	}
	
}
