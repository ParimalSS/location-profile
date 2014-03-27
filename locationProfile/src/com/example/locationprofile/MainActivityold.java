package com.example.locationprofile;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Selection;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivityold extends Activity implements LocationListener , OnMapClickListener ,OnClickListener {

	
	GoogleMap map;
	
	private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
	SQLiteDatabase datab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityold);
    
 //  map=((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
 //  map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        
       LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  
       lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.clear();
        
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
       mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
         // mDrawerList.setOnClickListener(this);
       
        // Set the adapter for the list view
      mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
       mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
      map.setOnMapClickListener(this);
        
    }
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
	//	map.clear();

		   MarkerOptions mp = new MarkerOptions();

		   mp.position(new LatLng(location.getLatitude(), location.getLongitude()));

		   mp.title("my position");

		   map.addMarker(mp);

		   map.animateCamera(CameraUpdateFactory.newLatLngZoom(
		    new LatLng(location.getLatitude(), location.getLongitude()), 16));

	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMapClick(LatLng point) {
		// TODO Auto-generated method stub
	//	mTapTextView.setText("tapped, point=" + point);
		 Toast.makeText(this, " "+point,Toast.LENGTH_LONG).show();
	//	 ArrayAdapter<string> =
	//	 list.add("B");
	//	 list.add("C");
	
		MarkerOptions marker = new MarkerOptions().position(
                new LatLng(point.latitude, point.longitude)).title("New Marker");
		marker.draggable(true);
		Mysqlitehelper mysq=new Mysqlitehelper(this);
		SQLiteDatabase datab=mysq.getWritableDatabase();
		 ContentValues values = new ContentValues();
		 values.put("xaxis", point.latitude);
		 values.put("yaxis", point.longitude);
		 values.put("name", "name");
		 datab.insert("location", null, values);
		 datab.close();
        map.addMarker(marker);
       
      
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activityold, menu);
        return true;
    }*/
	
	
	
	
	 private class DrawerItemClickListener implements ListView.OnItemClickListener {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	           selectItem(position);
	        	
	           if(position==1)
	            {
	            	Intent i=new Intent(view.getContext(),GPSSettingActivity.class);
		        	startActivity(i);
	            }
	           
	           
	           
	           if(position==2)
	            {
	            	Intent i=new Intent(view.getContext(),MainActivity.class);
		        	startActivity(i);
	            }

	    		//gs.openAndQueryDatabase();
	        }

	        private void selectItem(int position) {
	            // update the main content by replacing fragments
	            Fragment fragment = new Fragment();
	            Bundle args = new Bundle();
	          //  args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
	            fragment.setArguments(args);

	            FragmentManager fragmentManager = getFragmentManager();
	            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

	            // update selected item and title, then close the drawer
	            mDrawerList.setItemChecked(position, true);
	            	
	            
	            setTitle(mPlanetTitles[position]);
	            mDrawerLayout.closeDrawer(mDrawerList);
	        }
	    }
	
	
	
	
}



