package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PathePerson implements Serializable {

    public static final String TYPE_DIRECTOR = "Regisseur";

    private static final long serialVersionUID = 2175035743077791234L;

    @JsonProperty
    private String biography;
    @JsonProperty
    private Calendar birthDate;
    @JsonProperty
    private String birthPlace;
    @JsonProperty
    private String gender;
    @JsonProperty
    private long id;
    @JsonProperty("images")
    private List<PatheImage> imageList;
    @JsonProperty
    private int movieCount;
    @JsonProperty("movies")
    private List<Movie> movieList;
    @JsonProperty
    private String name;
    @JsonProperty
    private String nationality;
    @JsonProperty
    private String role;
    @JsonProperty
    private int seqNo;
    @JsonProperty
    private String thumb;
    @JsonProperty
    private String type;

    public PathePerson() {
        this.birthDate = Calendar.getInstance();
        this.movieList = new ArrayList<>();
        this.imageList = new ArrayList<>();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeqNo() {
        return this.seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getThumb() {
        return this.thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getMovieCount() {
        return this.movieCount;
    }

    public void setMovieCount(int movieCount) {
        this.movieCount = movieCount;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String typeString) {
        this.type = typeString;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBiography() {
        return this.biography;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Calendar getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return this.birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    public List<Movie> getMovieList() {
        return this.movieList;
    }

    public void setImageList(ArrayList<PatheImage> imageList) {
        this.imageList = imageList;
    }

    public List<PatheImage> getImageList() {
        return this.imageList;
    }

    public int describeContents() {
        return 0;
    }

}
