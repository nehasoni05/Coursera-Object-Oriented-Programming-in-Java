package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, P2D);

		if (offline)
		{
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers .it will collect all the properties of your location.
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    //TODO (Step 3): Add a loop here that calls createMarker (see below) 
	    // to create a new SimplePointMarker for each PointFeature in 
	    // earthquakes.  Then add each new SimplePointMarker to the 
	    // List markers (so that it will be added to the map in the line below)
	    // Add the markers to the map so that they are displayed
	    for (PointFeature eq: earthquakes)       
	    {
	    	markers.add(createMarker(eq));         //the markers here generate are of gray colour
	    }
	    
	   
	    map.addMarkers(markers);
	}
	
	private SimplePointMarker createMarker(PointFeature feature)
	{  
		// To print all of the features in a PointFeature (so you can see what they are)
		// uncomment the line below.  Note this will only print if you call createMarker 
		// from setup
	   //System.out.println(feature.getProperties());  (all prop will print down not on map)
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		// Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	   
		
		// TODO (Step 4): Add code below to style the marker's size and color 
	    // according to the magnitude of the earthquake.  
	    // Don't forget about the constants THRESHOLD_MODERATE and 
	    // THRESHOLD_LIGHT, which are declared above.
	    // Rather than comparing the magnitude to a number directly, compare 
	    // the magnitude to these variables (and change their value in the code 
	    // above if you want to change what you mean by "moderate" and "light")
	    
	   
		int red=color(255,0,0);
	    int blue=color(0,0,255);
	    int yellow=color(255,255,0);
	    
	    //styling the markers
	   
	    
	    	if(mag<4)
	    	{
	    		marker.setColor(blue);
	    		((SimplePointMarker) marker).setRadius(6);     //here we do type casting for mk (i.e mk is type of SimplePointMarker
	    	}
	    	else if(mag>4.0 && mag<4.9)
	    	{
	    		marker.setColor(yellow);
	    		((SimplePointMarker) marker).setRadius(8);
	    	}
	    	else
	    	{
	    		marker.setColor(red);
	    		((SimplePointMarker) marker).setRadius(12);
	    	}
	    	
	    	 // Finally return the marker
	    return marker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
	   fill(200);                                                  //background color of rectangle
	   rect(20,250,170,170);                                       //to draw rectangle 
	   textSize(20);                                               //font of text
	   fill(0, 0, 0, 200);                                          //fill(red,green,blue,opacity)
	   text("Earthquake Key",15, 270);                             //text(string,x ,y)
	   
	   fill(color(255,0,0));                                       //color of ellipse
	   ellipse(30, 290, 18, 18);                                   //ellipse(x,y,width og ellipse,height of ellipse)
	   fill(color(255,0,0));                                       //color of text  
	   textSize(11);
	   text("5.0 + magnitude", 50, 290);                           //write text ,x,y
	   
	   fill(color(255,255,0));                                       //color of ellipse
	   ellipse(30, 330, 12, 12);                                   //ellipse(x,y,width og ellipse,height of ellipse)
	   fill(color(255,255,0));                                       //color of text  
	   textSize(11);
	   text("4.0 + magnitude", 50, 330); 
	   
	   fill(color(0,0,255));                                       //color of ellipse
	   ellipse(30, 370, 8, 8);                                   //ellipse(x,y,width og ellipse,height of ellipse)
	   fill(color(0,0,255));                                       //color of text  
	   textSize(11);
	   text("Below 4.0 magnitude", 50, 370);
	}
}
