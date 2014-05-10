package cn.tk.movieinfo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;
import cn.tk.db.DBHelper;
import cn.tk.domain.MovieInfo;
import cn.tk.service.JsonTools;
import cn.tk.service.Utils;

public class MainActivity extends Activity {
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	private EditText search_et;
	private Button search_bt,history_bt;
	private ListView movie_item;
	ProgressDialog dialog =null;
	List<MovieInfo> infos0 = null;
	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	private int mark = 0;
	private Handler myhandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			infos0 = (List<MovieInfo>) msg.obj;
			int flag0 = msg.what;
			dialog.dismiss();
			if(flag0 == 0 ){
				Toast.makeText(MainActivity.this, "未找到相关影片", Toast.LENGTH_SHORT).show();
			}else{
				if(mark ==1 && !list.isEmpty()){
					list.clear();
				}
				Toast.makeText(MainActivity.this, "搜索成功……", Toast.LENGTH_SHORT).show();
				for(MovieInfo info:infos0){
					Map<String,Object> map = new HashMap<String, Object>();
					String title = info.getName();
					map.put("name", title);
					System.out.println("--------------"+map.get("name"));
					Double rating = info.getRating();
					map.put("rating", String.valueOf(rating));
					System.out.println("--------------"+map.get("rating"));
					String year = info.getYear();
					map.put("year", year);
					System.out.println("--------------"+map.get("year"));
					String image = info.getImage();
					System.out.println("--------------******************hello-----------"+image);
					Bitmap bitmap = Utils.downloadBitmap(image);
					
					map.put("image", bitmap);
					
					list.add(map);
				}
				
				SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list, R.layout.movieitem, new String[]{"name","rating","year","image"}, new int[]{R.id.item_title,R.id.item_rating,R.id.item_year,R.id.item_image});
				
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
				movie_item.setAdapter(adapter);
				mark = 1;
			}
			
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		dbHelper = new DBHelper(this);
		db = dbHelper.getWritableDatabase();
		
		search_et = (EditText)this.findViewById(R.id.search_et);
		search_bt = (Button) this.findViewById(R.id.search_bt);
		history_bt = (Button) this.findViewById(R.id.historyBut);
		movie_item = (ListView) this.findViewById(R.id.movieList);
		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("正在下载中……请稍后……");
		dialog.setCancelable(true);
		history_bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
				startActivity(intent);
			}
		});
		search_bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo netInfo = cm.getActiveNetworkInfo();
				if(netInfo == null || !netInfo.isAvailable()){
					Toast.makeText(MainActivity.this, "网络不可用，请先连接网络……", Toast.LENGTH_SHORT).show();
				}else{
					Myrun myrun = new Myrun();
					Thread thread = new Thread(myrun);
					thread.start();
					dialog.show();
				}
				
			}
		});
		movie_item.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,ItemActivity.class);
				
				intent.putExtra("MOVIEINFO", infos0.get(arg2));
				String name_db = infos0.get(arg2).getName();
				String rating_db = String.valueOf(infos0.get(arg2).getRating());
				String year_db = infos0.get(arg2).getYear();
				String image_db = infos0.get(arg2).getImage();
				ContentValues values = new ContentValues();
				values.put("name", name_db);
				values.put("rating", rating_db);
				values.put("year", year_db);
				values.put("image", image_db);
				
				db.insert("mytable", null, values);
				startActivity(intent);
			}
		});
		
	}
	private class Myrun implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				String moviename = search_et.getText().toString().trim();
				String requestName = new String(moviename.getBytes(), "iso-8859-1");
				if(moviename.equals("")){
					Toast.makeText(MainActivity.this, "请输入电影名称……", Toast.LENGTH_SHORT).show();
					return;
				}
				String path = "https://api.douban.com/v2/movie/search?q="+requestName;
				String result = Utils.downloadjsondata(path);
				System.out.println(result);
				List<MovieInfo>  infos = JsonTools.parseJson(result);
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>"+infos.size());
				int flag;
				if(infos == null){
					flag = 0;
				}else{
					flag = 1;
				}
				
				Message message = new Message();
				message.obj = infos;
				message.what = flag;
				myhandler.sendMessage(message);
				for(MovieInfo info:infos){
					System.out.println(info.toString());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
