package cn.tk.movieinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.SimpleAdapter.ViewBinder;
import cn.tk.db.DBHelper;
import cn.tk.service.Utils;

public class HistoryActivity extends Activity {
	private TabHost tabhost;
	private ListView listview_Movie,listview_MoviePerson;
	private DBHelper dbhelper;
	private SQLiteDatabase db;
	private List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	private List<Map<String, Object>> list_person = new ArrayList<Map<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.history);
		dbhelper = new DBHelper(this);
		db = dbhelper.getReadableDatabase();
		listview_Movie = (ListView) this.findViewById(R.id.listview_history0);
		listview_MoviePerson = (ListView) this.findViewById(R.id.listview_history1);
		tabhost = (TabHost) this.findViewById(R.id.tabhost01);
		tabhost.setup();
		tabhost.addTab(tabhost.newTabSpec("moviesInfo").setIndicator("浏览过的影片").setContent(R.id.scrollview_history0));		
		tabhost.addTab(tabhost.newTabSpec("moviesPeople").setIndicator("浏览过的影人").setContent(R.id.scrollview_history1));
		
		
		Cursor cursor = db.query("mytable", new String[]{"name","rating","year","image"}, null, null, null, null, null);
		
		Map<String, Object> map = null;
		while(cursor.moveToNext()){
			map = new HashMap<String, Object>();
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String rating = cursor.getString(cursor.getColumnIndex("rating"));
			String year = cursor.getString(cursor.getColumnIndex("year"));
			String image = cursor.getString(cursor.getColumnIndex("image"));
			Bitmap bitmap = Utils.downloadBitmap(image);
			
			map.put("name", name);
			map.put("rating", rating);
			map.put("year", year);
			map.put("image", bitmap);
			
			list.add(map);
			
			
		}
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.movieitem, new String[]{"name","rating","year","image"}, new int[]{R.id.item_title,R.id.item_rating,R.id.item_year,R.id.item_image});
		adapter.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// TODO Auto-generated method stub
				if(view instanceof ImageView && data instanceof Bitmap){
					ImageView iv = (ImageView) view;
					iv.setImageBitmap((Bitmap)data);
					return true;
				}else{
					return false;
				}
				
			}
		});
		listview_Movie.setAdapter(adapter);
		
		Cursor cursor_person = db.query("person", new String[]{"name","name_en","sex","image"}, null,null, null, null, null);
		Map<String, Object> map_person = null;
		while(cursor_person.moveToNext()){
			map_person = new HashMap<String, Object>();
			String name_person = cursor_person.getString(cursor_person.getColumnIndex("name"));
			String name_en = cursor_person.getString(cursor_person.getColumnIndex("name_en"));
			String sex = cursor_person.getString(cursor_person.getColumnIndex("sex"));
			String imagepath = cursor_person.getString(cursor_person.getColumnIndex("image"));
			Bitmap bitmap_person = Utils.downloadBitmap(imagepath);
			
			map_person.put("name_person", name_person);
			map_person.put("name_en", name_en);
			map_person.put("sex", sex);
			map_person.put("image", bitmap_person);
			
			list_person.add(map_person);
			
		}
		SimpleAdapter adapter_person = new SimpleAdapter(this, list_person, R.layout.movieitem, new String[]{"name_person","name_en","sex","image"}, new int[]{R.id.item_title,R.id.item_rating,R.id.item_year,R.id.item_image});
        adapter_person.setViewBinder(new ViewBinder() {
			
			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// TODO Auto-generated method stub
				if(view instanceof ImageView && data instanceof Bitmap){
					ImageView iv = (ImageView) view;
					iv.setImageBitmap((Bitmap)data);
					return true;
				}else{
					return false;
				}
				
			}
		});
        listview_MoviePerson.setAdapter(adapter_person);
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}

}
