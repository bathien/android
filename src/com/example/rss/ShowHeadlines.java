package com.example.rss;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ShowHeadlines  extends Activity {
	// a main category has already been selected by the user
	// such as: 'Top Stories', 'World News', 'Business', ...  
	// ["urlCaption", "urlAddress"] comes in a bundle sent   
	// by main thread, here we access RSS-feed and show the   
	// corresponding headlines.    
	ArrayList<SingleItem> newsList = new ArrayList<SingleItem>();
	ListView myListView;  
	String urlAddress = "";  
	String urlCaption = "";     SingleItem selectedNewsItem;   
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 
		myListView = (ListView)this.findViewById(R.id.myListView);
		// find out which intent is calling us       
		Intent callingIntent = getIntent();   
		// grab data bundle holding selected url & caption sent to us
		Bundle myBundle = callingIntent.getExtras();     
		urlAddress = myBundle.getString("urlAddress");     
		urlCaption = myBundle.getString("urlCaption"); 
		 // update app's top 'TitleBar' (eg. 'NPR - Business Wed April 09, 2014')
		this.setTitle("NPR - " + urlCaption + " \t" + MainActivity.niceDate());    
		// clicking on a row shows dialogBox with more info about selected item       
		myListView = (ListView)this.findViewById(R.id.myListView); 
		
		myListView.setOnItemClickListener(new OnItemClickListener()
		{ 
			public void onItemClick(AdapterView<?> av, View v, int index, long id) {
				selectedNewsItem = newsList.get(index);
				showNiceDialogBox(selectedNewsItem, getApplicationContext());
				}     
			});
		// get stories for the selected news option
		DownloadRssFeed downloader =  new DownloadRssFeed(ShowHeadlines.this);
		downloader.execute(urlAddress, urlCaption);
		}//onCreate  
		
        public void showNiceDialogBox(SingleItem selectedStoryItem, Context context){
        	// make a nice looking dialog box (story summary, btnClose, btnMore)
        	// CAUTION: (check)on occasions title and description are the same!   
        	String title = selectedStoryItem.getTitle(); 
        	String description = selectedStoryItem.getDescription(); 
        	if (title.toLowerCase().equals(description.toLowerCase())){
        		description = "";      
        		}
        	try {
        		//CAUTION: sometimes TITLE and DESCRIPTION include HTML markers  
        		final Uri storyLink =  Uri.parse(selectedStoryItem.getLink());    
        		AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);    
        		myBuilder
        		.setIcon(R.drawable.tuoitre)
        		.setTitle(Html.fromHtml(urlCaption) )
        		.setMessage( title + "\n\n" + Html.fromHtml(description) + "\n" ) 
        		.setPositiveButton("Cancel", null)
        		.setNegativeButton("More", new DialogInterface.OnClickListener() {   
        			public void onClick(DialogInterface dialog, int whichOne) {    
        				Intent browser = new Intent(Intent.ACTION_VIEW, storyLink);      
        				startActivity(browser);     
        				}        
        			})//setNegativeButton  
        		.show(); 
        		} catch (Exception e) {
        			Log.e("Error DialogBox", e.getMessage() );
        		}   
        }//showNiceDialogBox 
}//ShowHeadlines 
        