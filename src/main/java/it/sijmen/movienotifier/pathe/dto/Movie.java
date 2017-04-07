package it.sijmen.movienotifier.pathe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Movie implements Serializable {

    private static final long serialVersionUID = -4593722361788767522L;

    @JsonProperty
    private int age;
    @JsonProperty("cast")
    private ArrayList<PathePerson> castList;
    @JsonProperty
    private String distributor;
    @JsonProperty("genreIds")
    private ArrayList<Long> genreIds;
    @JsonProperty("genres")
    private ArrayList<MovieGenre> genreList;
    @JsonProperty
    private long id;
    @JsonProperty
    private String intro;
    @JsonProperty
    private boolean isRatable;
    @JsonProperty
    private long likesAllTime;
    @JsonProperty
    private long likesRecent;
    @JsonProperty
    private String name;
    @JsonProperty
    private String originalName;
    @JsonProperty
    private int pDisc;
    @JsonProperty
    private int pDrugs;
    @JsonProperty
    private int pFear;
    @JsonProperty
    private int pLang;
    @JsonProperty
    private int pSex;
    @JsonProperty
    private int pViol;
    @JsonProperty("posters")
    private ArrayList<PathePoster> posterList;
    @JsonProperty
    private int producationYear;
    @JsonProperty
    private double rating;
    @JsonProperty
    private long ratingCount;
    @JsonProperty
    private Calendar releaseDate;
    @JsonProperty
    private int runTime;
    @JsonProperty
    private long salesAlltime;
    @JsonProperty
    private long salesRecent;
    @JsonProperty("schedule")
    private ArrayList<PatheSchedule> scheduleList;
    @JsonProperty
    private String status;
    @JsonProperty("stills")
    private ArrayList<PatheStill> stillList;
    @JsonProperty
    private String teaser;
    @JsonProperty
    private String text;
    @JsonProperty
    private String thumb;
    @JsonProperty("trailers")
    private ArrayList<Trailer> trailerList;
    @JsonProperty
    private PatheVersion version;
    @JsonProperty
    private List<PatheVersion> versions;
    @JsonProperty
    private long visitors;
    @JsonProperty
    private long visitorsAllTime;
    @JsonProperty
    private long visitorsRecent;
    @JsonProperty
    private String vodUrl;

    public Movie() {
        this.releaseDate = Calendar.getInstance();
        this.age = -1;
        this.genreList = new ArrayList<>();
        this.castList = new ArrayList<>();
        this.scheduleList = new ArrayList<>();
        this.posterList = new ArrayList<>();
        this.stillList = new ArrayList<>();
        this.trailerList = new ArrayList<>();
        this.genreIds = new ArrayList<>();
    }

    public long getVisitorsRecent() {
        return this.visitorsRecent;
    }

    public long getRatingCount() {
        return this.ratingCount;
    }

    public void setRatingCount(long ratingCount) {
        this.ratingCount = ratingCount;
    }

    public long getSalesAlltime() {
        return this.salesAlltime;
    }

    public void setSalesAlltime(long salesAlltime) {
        this.salesAlltime = salesAlltime;
    }

    public long getSalesRecent() {
        return this.salesRecent;
    }

    public void setSalesRecent(long salesRecent) {
        this.salesRecent = salesRecent;
    }

    public long getLikesAllTime() {
        return this.likesAllTime;
    }

    public void setLikesAllTime(long likesAllTime) {
        this.likesAllTime = likesAllTime;
    }

    public long getLikesRecent() {
        return this.likesRecent;
    }

    public void setLikesRecent(long likesRecent) {
        this.likesRecent = likesRecent;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public int getRunTime() {
        return this.runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public int getProducationYear() {
        return this.producationYear;
    }

    public void setProducationYear(int producationYear) {
        this.producationYear = producationYear;
    }

    public String getTeaser() {
        return this.teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getIntro() {
        return this.intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<MovieGenre> getGenreList() {
        return this.genreList;
    }

    public void setGenreList(ArrayList<MovieGenre> patheGenres) {
        this.genreList = patheGenres;
    }

    public double getRating() {
        return this.rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getVisitorsAllTime() {
        return this.visitorsAllTime;
    }

    public void setVisitorsAllTime(long visitors) {
        this.visitorsAllTime = visitors;
    }

    public void setVisitorsRecent(long visitors) {
        this.visitorsRecent = visitors;
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

    public PatheKijkwijzer getPatheKijkwijzer() {
        return generateKijkwijzer();
    }

    public Calendar getReleaseDate() {
        return this.releaseDate;
    }

    public int getAge() {
        return this.age;
    }

    @JsonSetter("pAge")
    public void setPPage(String age) {
        this.age = MovieAge.fromAgeString(age);
    }

    public String getThumb() {
        return this.thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDistributor() {
        return this.distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public void setCastList(ArrayList<PathePerson> cast) {
        this.castList = cast;
    }

    public ArrayList<PathePerson> getCastList() {
        return this.castList;
    }

    public PathePerson getDirector() {
        Iterator it = this.castList.iterator();
        while (it.hasNext()) {
            PathePerson person = (PathePerson) it.next();
            if (person.getType().equalsIgnoreCase(PathePerson.TYPE_DIRECTOR)) {
                return person;
            }
        }
        return null;
    }

    public ArrayList<PatheSchedule> getScheduleList() {
        return this.scheduleList;
    }

    public void setScheduleList(ArrayList<PatheSchedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public long getVisitors() {
        return this.visitors;
    }

    public void setVisitors(long visitors) {
        this.visitors = visitors;
    }

    public int getPatheKijkwijzerMask() {
        return generateKijkwijzer().getMask();
    }

    private PatheKijkwijzer generateKijkwijzer() {
        PatheKijkwijzer kijkWijzer = new PatheKijkwijzer();
        kijkWijzer.setDisc(this.pDisc);
        kijkWijzer.setDrugs(this.pDrugs);
        kijkWijzer.setFear(this.pFear);
        kijkWijzer.setLang(this.pLang);
        kijkWijzer.setSex(this.pSex);
        kijkWijzer.setViol(this.pViol);
        return kijkWijzer;
    }

    public int getpDrugs() {
        return this.pDrugs;
    }

    public void setpDrugs(int pDrugs) {
        this.pDrugs = pDrugs;
    }

    public int getpDisc() {
        return this.pDisc;
    }

    public void setpDisc(int pDisc) {
        this.pDisc = pDisc;
    }

    public int getpLang() {
        return this.pLang;
    }

    public void setpLang(int pLang) {
        this.pLang = pLang;
    }

    public int getpViol() {
        return this.pViol;
    }

    public void setpViol(int pViol) {
        this.pViol = pViol;
    }

    public int getpSex() {
        return this.pSex;
    }

    public void setpSex(int pSex) {
        this.pSex = pSex;
    }

    public int getpFear() {
        return this.pFear;
    }

    public void setpFear(int pFear) {
        this.pFear = pFear;
    }

    public ArrayList<PathePoster> getPosterList() {
        return this.posterList;
    }

    public void setPosterList(ArrayList<PathePoster> posterList) {
        this.posterList = posterList;
    }

    public ArrayList<PatheStill> getStillList() {
        return this.stillList;
    }

    public void setStillList(ArrayList<PatheStill> stillList) {
        this.stillList = stillList;
    }

    public ArrayList<Trailer> getTrailerList() {
        return this.trailerList;
    }

    public void setTrailerList(ArrayList<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    public int describeContents() {
        return 0;
    }

    public PatheVersion getVersion() {
        return this.version;
    }

    public void setVersion(PatheVersion version) {
        this.version = version;
    }

    public boolean isRatable() {
        return this.isRatable;
    }

    public void setRatable(boolean isRatable) {
        this.isRatable = isRatable;
    }

    public String getVodUrl() {
        return this.vodUrl;
    }

    public void setVodUrl(String vodUrl) {
        this.vodUrl = vodUrl;
    }

    public ArrayList<Long> getGenreIds() {
        return this.genreIds;
    }

    public void setGenreIds(ArrayList<Long> genreIds) {
        this.genreIds = genreIds;
    }

    public String getVodId() {
        if (this.vodUrl == null) {
            return null;
        }
        Matcher m = Pattern.compile("/film/([0-9]+)").matcher(this.vodUrl);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    public List<PatheVersion> getVersions() {
        return this.versions;
    }

    public void setVersions(List<PatheVersion> versions) {
        this.versions = versions;
    }

    public String getVersionString() {
        if (this.versions == null || this.versions.size() != 1) {
            return null;
        }
        return ((PatheVersion) this.versions.get(0)).getName();
    }
}
