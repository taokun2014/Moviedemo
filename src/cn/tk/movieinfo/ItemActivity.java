package cn.tk.movieinfo;




import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.tk.domain.MovieInfo;
import cn.tk.service.Utils;

public class ItemActivity extends Activity {
	private ImageView movieImage,directorImage,actorImage1,actorImage2,actorImage3,actorImage4;
	private TextView movieName,movieRating,movieYear,movieGenres,directorName,actorName1,actorName2,actorName3,actorName4;
	MovieInfo movieinfo = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_item);
		Intent intent = getIntent();
		movieinfo = (MovieInfo) intent.getSerializableExtra("MOVIEINFO");
		System.out.println("--------------ItemActivity-----------"+movieinfo.toString());
		
		movieImage = (ImageView) this.findViewById(R.id.movieImage);
		movieName = (TextView) this.findViewById(R.id.movieTitle);
		movieRating = (TextView) this.findViewById(R.id.movieRating);
		movieYear = (TextView) this.findViewById(R.id.movieYear);
		movieGenres = (TextView)this.findViewById(R.id.movieGenres);
		directorName = (TextView) this.findViewById(R.id.directorName);
		actorName1 = (TextView) this.findViewById(R.id.actorName1);
		actorName2 = (TextView) this.findViewById(R.id.actorName2);
		actorName3 = (TextView) this.findViewById(R.id.actorName3);
		actorName4 = (TextView) this.findViewById(R.id.actorName4);
		directorImage = (ImageView) this.findViewById(R.id.directorImage);
		actorImage1 = (ImageView) this.findViewById(R.id.actorImage1);
		actorImage2 = (ImageView) this.findViewById(R.id.actorImage2);
		actorImage3 = (ImageView) this.findViewById(R.id.actorImage3);
		actorImage4 = (ImageView) this.findViewById(R.id.actorImage4);
		
		movieImage.setImageBitmap(Utils.downloadBitmap(movieinfo.getImage()));
		movieName.setText(movieinfo.getName());
		movieRating.setText(String.valueOf(movieinfo.getRating()));
		movieYear.setText(movieinfo.getYear());
		movieGenres.setText(movieinfo.getGenres());
		directorName.setText(movieinfo.getDirector_name());
		actorName1.setText(movieinfo.getActors_name0());
		actorName2.setText(movieinfo.getActors_name1());
		actorName3.setText(movieinfo.getActors_name2());
		actorName4.setText(movieinfo.getActors_name3());
		
		if(movieinfo.getDirector_image()==null && movieinfo.getDirector_name()!=null){
			directorImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.notfound));
		}else{
			directorImage.setImageBitmap(Utils.downloadBitmap(movieinfo.getDirector_image()));
		}
		if(movieinfo.getActors_image0()==null && movieinfo.getActors_name0()!=null){
			actorImage1.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.notfound));
		}else{
			actorImage1.setImageBitmap(Utils.downloadBitmap(movieinfo.getActors_image0()));
		}
		if(movieinfo.getActors_image1()==null && movieinfo.getActors_name1()!=null){
			actorImage2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.notfound));
		}else{
			actorImage2.setImageBitmap(Utils.downloadBitmap(movieinfo.getActors_image1()));
		}
		if(movieinfo.getActors_image2()==null && movieinfo.getActors_name2()!=null){
			actorImage3.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.notfound));
		}else{
			actorImage3.setImageBitmap(Utils.downloadBitmap(movieinfo.getActors_image2()));
		}
		if(movieinfo.getActors_image3()==null && movieinfo.getActors_name3()!=null){
			actorImage4.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.notfound));
		}else{
			actorImage4.setImageBitmap(Utils.downloadBitmap(movieinfo.getActors_image3()));
		}
		
		directorImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ItemActivity.this,DirectorActivity.class);
				if(movieinfo.getDirector_id().equals("null")){
					Toast.makeText(ItemActivity.this, "无相关的影人条目信息……", Toast.LENGTH_SHORT).show();
				}else{
					intent.putExtra("celebrity", movieinfo.getDirector_id());
					System.out.println("---------movieinfo.getDirector_id()----------"+movieinfo.getDirector_id());
					startActivity(intent);
				}
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item, menu);
		return true;
	}
	
}
