package cn.tk.movieinfo;

import cn.tk.db.DBHelper;
import cn.tk.domain.Person;
import cn.tk.service.JsonTools;
import cn.tk.service.Utils;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class DirectorActivity extends Activity {
	private ImageView director_image,workimage1,workimage2,workimage3,workimage4;
	private TextView director_name,director_nameEn,director_sex,director_birthday,director_bornplace,workname1,workname2,workname3,workname4,worksTV;
	String director_Id = null;
	private ProgressDialog dialog = null;
	private DBHelper dbhelper = null;
	private SQLiteDatabase db = null;
	private Handler myhandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Person p = (Person) msg.obj;
			director_image.setImageBitmap(Utils.downloadBitmap(p.getImagepath()));
			director_name.setText(p.getName());
			director_nameEn.setText(p.getName_en());
			director_sex.setText(p.getSex());
			director_bornplace.setText(p.getPlace());
			ContentValues cv = new ContentValues();
			cv.put("name", p.getName());
			cv.put("name_en", p.getName_en());
			cv.put("sex", p.getSex());
			cv.put("image", p.getImagepath());
			
			db.insert("person", null, cv);
			workimage1.setImageBitmap(Utils.downloadBitmap(p.getWorkimage1()));
			
			workimage2.setImageBitmap(Utils.downloadBitmap(p.getWorkimage2()));
			
			workimage3.setImageBitmap(Utils.downloadBitmap(p.getWorkimage3()));
			
			workimage4.setImageBitmap(Utils.downloadBitmap(p.getWorkimage4()));
			
			workname1.setText(p.getWorkname1());
			workname2.setText(p.getWorkname2());
			workname3.setText(p.getWorkname3());
			workname4.setText(p.getWorkname4());
			worksTV.setText("作品");
			dialog.dismiss();
			
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.director);
		dbhelper = new DBHelper(this);
		db = dbhelper.getWritableDatabase();
		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("正在获取……请稍后……");
		dialog.setCancelable(true);
		dialog.show();
		Intent intent = getIntent();
		director_Id = intent.getStringExtra("celebrity");
		System.out.println("---------director_Id---------------"+director_Id);
		director_image = (ImageView) this.findViewById(R.id.directorImageId);
		workimage1 = (ImageView) this.findViewById(R.id.workImage1);
		workimage2 = (ImageView) this.findViewById(R.id.workImage2);
		workimage3 = (ImageView) this.findViewById(R.id.workImage3);
		workimage4 = (ImageView) this.findViewById(R.id.workImage4);
		director_name = (TextView) this.findViewById(R.id.nameId);
		director_nameEn = (TextView) this.findViewById(R.id.name_EnId);
		director_sex = (TextView) this.findViewById(R.id.sexId);
		director_birthday = (TextView) this.findViewById(R.id.birthdayId);
		director_bornplace = (TextView) this.findViewById(R.id.placeId);
		workname1 = (TextView) this.findViewById(R.id.workName1);
		workname2 = (TextView) this.findViewById(R.id.workName2);
		workname3 = (TextView) this.findViewById(R.id.workName3);
		workname4 = (TextView) this.findViewById(R.id.workName4);
		worksTV = (TextView) this.findViewById(R.id.worksTV);
		OtherRun otherrun = new OtherRun();
		new Thread(otherrun).start();
		
	}
	private class OtherRun implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Person person = JsonTools.parsePersonJson(director_Id);
			Message message = new Message();
			message.obj = person;
			myhandler.sendMessage(message);
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.director, menu);
		return true;
	}

}
