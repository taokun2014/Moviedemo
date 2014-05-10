package cn.tk.service;



import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;







import cn.tk.domain.MovieInfo;
import cn.tk.domain.Person;




public class JsonTools {

	/**
	 * 定义解析Json数据的方法
	 * 
	 * @param jsonString
	 *            从豆瓣上获取的Json字符串
	 * @return MovieInfo 集合
	 */
	public static List<MovieInfo> parseJson(String jsonString) {
		List<MovieInfo> movieInfos = new ArrayList<MovieInfo>();
		MovieInfo movieInfo = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObject.getJSONArray("subjects");
			for(int i=0; i<jsonArray.length() && i<10;i++){
				movieInfo = new MovieInfo();
				
				
				JSONObject jsonItem = jsonArray.getJSONObject(i);
				
				
				String id = jsonItem.getString("id");
				//System.out.println(id);
				movieInfo.setId(id);
				
				String title = jsonItem.getString("title");
				System.out.println(title);
				movieInfo.setName(title);
				
				
				String year = jsonItem.getString("year");
				movieInfo.setYear(year);
				JSONObject json_rating = jsonItem.getJSONObject("rating");
				
				Double rating = json_rating.getDouble("average");
				System.out.println("rating = "+rating);
				movieInfo.setRating(rating);
				
				
				JSONObject json_image = jsonItem.getJSONObject("images");
				String image_path = json_image.getString("small");
				System.out.println("image_path = "+image_path);
				movieInfo.setImage(image_path);
				//movieInfo.setImage(downloadBitmap(image_path));
				
				
				System.out.println("**************id=" + movieInfo.getId());
				String movieitemStr = Utils
						.downloadjsondata("https://api.douban.com/v2/movie/subject/"
								+ movieInfo.getId());
				System.out
						.println("**************movieitemStr=" + movieitemStr);

				JSONObject jObject = new JSONObject(movieitemStr);
				JSONArray jArray = jObject.getJSONArray("genres");
				StringBuffer genres = new StringBuffer();
				for (int j = 0; j < jArray.length(); j++) {
					String genresStr = (String) jArray.get(j);
					if (j == jArray.length() - 1) {
						genres.append(genresStr);
						movieInfo.setGenres(genres);
						System.out.println(movieInfo.getGenres().toString());
					} else {
						genres.append(genresStr + "/");
					}

				}
					
					//获取影片导演信息
					JSONArray directorsArray = jObject.getJSONArray("directors");
//					System.out.println("--------directorsArray.length()-------"+directorsArray.length());
					JSONObject directorsJObject = directorsArray.getJSONObject(0);
					String directorName = directorsJObject.getString("name");
					String directorId = directorsJObject.getString("id");
					System.out.println("--------------directorName----------"+directorName);
					movieInfo.setDirector_name(directorName);
					movieInfo.setDirector_id(directorId);
					System.out.println("--------------directorId----------"+directorId);
					
				if (directorsJObject.getString("avatars").equals("null")) {
					System.out.println("==============not found=============");
				} else {
					
					String directorImage = directorsJObject.getJSONObject("avatars").getString("small");
					movieInfo.setDirector_image(directorImage);
				}
					
					
					/*System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>"+directorsArray.getString(0));
					String directorImage = avatarsJObject0.getString("small");
					System.out.println("--------------directorImage----------"+directorImage);
					movieInfo.setDirector_image(directorImage);*/
					
					//获取影片主演信息
					JSONArray actorsArray = jObject.getJSONArray("casts");
					System.out.println("---------actorsArray---------"+actorsArray.length());
					//System.out.println("----------actorsArray(0)------------"+actorsArray.getJSONObject(0).getJSONObject("avatars").getString("small"));
					for(int k = 0; k<actorsArray.length();k++){
						JSONObject actorJObject = actorsArray.getJSONObject(k);
//						System.out.println("------------actorJObject----------"+actorJObject);
						String imagePath = null;
						if(actorJObject.getString("avatars").equals("null")){
							System.out.println("==============not found=============");
							
						}else{
							imagePath = actorJObject.getJSONObject("avatars").getString("small");
//							System.out.println("======================="+imagePath);
							if(k==0){
								movieInfo.setActors_image0(imagePath);
							}else if(k==1){
								movieInfo.setActors_image1(imagePath);
							}else if(k==2){
								movieInfo.setActors_image2(imagePath);
							}else if(k==3){
								movieInfo.setActors_image3(imagePath);
							}
						}
						
						
					}
					
					for(int n=0;n<actorsArray.length();n++){
						JSONObject actorsJObject0 = actorsArray.getJSONObject(n);
						
						String actorsName = actorsJObject0.getString("name");
						System.out.println("------------actorsName----------"+actorsName);
						if(n==0){
							movieInfo.setActors_name0(actorsName);
						}else if(n==1){
							movieInfo.setActors_name1(actorsName);
						}else if(n==2){
							movieInfo.setActors_name2(actorsName);
						}else if(n==3){
							movieInfo.setActors_name3(actorsName);
						}
					}
					
				
				
				movieInfos.add(movieInfo);
				
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return movieInfos;
	}
	public static Person parsePersonJson(String parseId){
		Person person = new Person();
		String directorStr = Utils.downloadjsondata("https://api.douban.com/v2/movie/celebrity/"+parseId);
		try {
			JSONObject personObject = new JSONObject(directorStr);
			person.setName(personObject.getString("name"));
			person.setName_en(personObject.getString("name_en"));
			
			person.setSex(personObject.getString("gender"));
			
			person.setPlace(personObject.getString("born_place"));
			
			
			JSONObject imageObject = personObject.getJSONObject("avatars");
			String directorimagepath = imageObject.getString("small");
			person.setImagepath(directorimagepath);
			
			
			JSONArray workArray = personObject.getJSONArray("works");
			for(int m = 0; m<workArray.length() && m<4; m++){
				JSONObject workJObject = workArray.getJSONObject(m);
				JSONObject subjectJObject = workJObject.getJSONObject("subject");
				String workname = subjectJObject.getString("title");
				
				JSONObject imageJObject = subjectJObject.getJSONObject("images");
				String workimagepath = imageJObject.getString("small");
				System.out.println("==============="+workimagepath);
				if(m==0){
					person.setWorkname1(workname);
					person.setWorkimage1(workimagepath);	
				}else if(m==1){
					person.setWorkname2(workname);
					person.setWorkimage2(workimagepath);
				}else if(m==2){
					person.setWorkname3(workname);
					person.setWorkimage3(workimagepath);
				}else if(m==3){
					person.setWorkname4(workname);
					person.setWorkimage4(workimagepath);
				}
				
				
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return person;
	}
	
	
	
}
