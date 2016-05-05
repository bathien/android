package com.example.rss;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {
   // Main GUI - A NEWS application based on National Public Radio RSS material
	ArrayAdapter<String> adapterMainSubjects;
	ListView myMainListView; 
	Context  context; 
	SingleItem selectedNewsItem;
	// hard-coding main NEWS categories (TODO: use a resource file)  
	String [][] myUrlCaptionMenu = {
			{"http://tuoitre.vn/rss/tt-tin-moi-nhat.rss",   "Trang chủ"}, 
			{"http://tuoitre.vn/rss/tt-the-gioi.rss",   "Thế giới"},    
			{"http://tuoitre.vn/rss/tt-kinh-te.rss",   "Kinh tế"},  
			{"http://tuoitre.vn/rss/tt-giao-duc.rss",   "Giáo dục"},    
			{"http://tuoitre.vn/rss/tt-van-hoa-giai-tri.rss",   "Văn hóa giải trí"}, 
			{"http://tuoitre.vn/rss/tt-nhip-song-so.rss",   "Nhịp sống số"},     
			{"http://tuoitre.vn/rss/tt-du-lich.rss",   "Du lịch"},    
			{"http://tuoitre.vn/rss/tt-chinh-tri-xa-hoi.rss",   "Chính trị xã hội"},   
			{"http://tuoitre.vn/rss/tt-phap-luat.rss",   "Pháp luật"}  ,  
			{"http://tuoitre.vn/rss/tt-song-khoe.rss",   "Sống khỏe"},     
			{"http://tuoitre.vn/rss/tt-the-thao.rss",   "Thể thao"},    
			{"http://tuoitre.vn/rss/tt-nhip-song-tre.rss",   "Nhịp sống trẻ"},   
			{"http://tuoitre.vn/rss/tt-ban-doc.rss",   "Bạn đọc"}   
	};   
		 //define convenient URL and CAPTIONs arrays  
	String [] myUrlCaption = new String[myUrlCaptionMenu.length];
	String [] myUrlAddress = new String[myUrlCaptionMenu.length]; 
	 @Override  protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);  
		 setContentView(R.layout.activity_main);
		 for (int i=0; i < myUrlAddress.length; i++) {    
			 myUrlAddress[i] = myUrlCaptionMenu[i][0];      
			 myUrlCaption[i] = myUrlCaptionMenu[i][1];   
			 }
		 context = getApplicationContext();     
		 this.setTitle("Tuoi Tre News\n" + niceDate() );
		 // user will tap on a ListView’s row to request category’s headlines 
		 myMainListView = (ListView)this.findViewById(R.id.myListView);
		 
		 myMainListView.setOnItemClickListener(new OnItemClickListener(){   
			 public void onItemClick(AdapterView<?> _av, View _v, int _index, long _id) {       
				 String urlAddress = myUrlAddress[_index];         
				 String urlCaption = myUrlCaption[_index];  
				 //create an Intent to talk to activity: ShowHeadlines  
				 Intent callShowHeadlines = new Intent( MainActivity.this, ShowHeadlines.class); 
				//prepare a Bundle and add the input arguments: url & caption  
				 Bundle myData = new Bundle();
				 myData.putString("urlAddress", urlAddress); 
				 myData.putString("urlCaption", urlCaption);
				 callShowHeadlines.putExtras(myData);  
				 startActivity(callShowHeadlines); 
			 }
		 });
		 // fill up the Main-GUI’s ListView with main news categories 
		 adapterMainSubjects = new ArrayAdapter<String>(this,
				 android.R.layout.simple_list_item_1, //android's default
		 myUrlCaption);
		 myMainListView.setAdapter(adapterMainSubjects); 
		 }//onCreate        
	 // method returns a value such as "Monday Apr 7, 2014"
	 public static String niceDate() {
		 SimpleDateFormat sdf = new SimpleDateFormat("EE MMM d, yyyy ", Locale.ROOT); 
		 return sdf.format(new Date() ); 
	 } 
}//MainActivity 