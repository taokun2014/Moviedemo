package cn.tk.domain;



import java.io.Serializable;




public class MovieInfo implements Serializable{
	private String id = "";
	private String name = "";
	private Double rating;
	private String year = "";
	private String image = "";
	private StringBuffer genres;
	
	
	private StringBuffer country = null;
	private String actors_name0 = null;
	private String actors_image0 =null;
	private String actors_name1 = null;
	private String actors_image1 =null;
	private String actors_name2 = null;
	private String actors_image2 =null;
	private String actors_name3 = null;
	private String actors_image3 =null;
	private String director_name = null;
	private String director_image = null;
	private String director_id = null;
	
	private static final long serialVersionUID = 1L;

	/*public static final Parcelable.Creator<MovieInfo> CREATOR = new Creator<MovieInfo>() {

		@Override
		public MovieInfo createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			MovieInfo movieinfo = new MovieInfo();
			movieinfo.id = source.readString();
			movieinfo.name = source.readString();
			movieinfo.rating = source.readDouble();
			movieinfo.year = source.readString();
			movieinfo.image = source.readString();
			movieinfo.genres = source.readParcelable(StringBuffer.class.getClassLoader());
			movieinfo.actors_name0 = source.readString();
			movieinfo.actors_name1 = source.readString();
			movieinfo.actors_name2 = source.readString();
			movieinfo.actors_name3 = source.readString();
			return movieinfo;
		}

		@Override
		public MovieInfo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new MovieInfo[size];
		}

	};*/
	/*@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(year);
		dest.writeString(image);
		dest.writeString(genres.toString());
		dest.writeString(actors_name0);
		dest.writeString(actors_name1);
		dest.writeString(actors_name2);
		dest.writeString(actors_name3);
		dest.writeString(director_name);
		
		dest.writeDouble(rating);
	}*/
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public StringBuffer getGenres() {
		return genres;
	}
	public void setGenres(StringBuffer genres) {
		this.genres = genres;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public StringBuffer getCountry() {
		return country;
	}
	public void setCountry(StringBuffer country) {
		this.country = country;
	}
	
	
	public String getActors_name0() {
		return actors_name0;
	}
	public void setActors_name0(String actors_name0) {
		this.actors_name0 = actors_name0;
	}
	
	public String getActors_image0() {
		return actors_image0;
	}
	public void setActors_image0(String actors_image0) {
		this.actors_image0 = actors_image0;
	}
	public String getActors_name1() {
		return actors_name1;
	}
	public void setActors_name1(String actors_name1) {
		this.actors_name1 = actors_name1;
	}
	public String getActors_image1() {
		return actors_image1;
	}
	public void setActors_image1(String actors_image1) {
		this.actors_image1 = actors_image1;
	}
	public String getActors_name2() {
		return actors_name2;
	}
	public void setActors_name2(String actors_name2) {
		this.actors_name2 = actors_name2;
	}
	public String getActors_image2() {
		return actors_image2;
	}
	public void setActors_image2(String actors_image2) {
		this.actors_image2 = actors_image2;
	}
	public String getActors_name3() {
		return actors_name3;
	}
	public void setActors_name3(String actors_name3) {
		this.actors_name3 = actors_name3;
	}
	public String getActors_image3() {
		return actors_image3;
	}
	public void setActors_image3(String actors_image3) {
		this.actors_image3 = actors_image3;
	}
	public String getDirector_name() {
		return director_name;
	}
	public void setDirector_name(String director_name) {
		this.director_name = director_name;
	}
	public String getDirector_image() {
		return director_image;
	}
	public void setDirector_image(String director_image) {
		this.director_image = director_image;
	}
	
	public String getDirector_id() {
		return director_id;
	}
	public void setDirector_id(String director_id) {
		this.director_id = director_id;
	}
	@Override
	public String toString() {
		return "MovieInfo [id=" + id + ", name=" + name + ", rating=" + rating
				+ ", year=" + year + ", image=" + image + ", genres=" + genres
				+ ", country=" + country + ", actors_name0=" + actors_name0
				+ ", actors_image0=" + actors_image0 + ", actors_name1="
				+ actors_name1 + ", actors_image1=" + actors_image1
				+ ", actors_name2=" + actors_name2 + ", actors_image2="
				+ actors_image2 + ", actors_name3=" + actors_name3
				+ ", actors_image3=" + actors_image3 + ", director_name="
				+ director_name + ", director_image=" + director_image + "]";
	}
	
	
	
}
