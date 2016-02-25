package com.yogesh.eventhub.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yogesh on 8/2/16.
 */
public class Events implements Serializable {
    private String Actors;
    private String BannerURL;
    private String CanUserRate;
    private String Censor;
    private String Director;
    private String EventCensor;
    private String EventCode;
    private String EventIsGlobal;
    private String EventMusic;
    private String EventReleaseDate;
    private String EventSynopsis;
    private String EventTitle;
    private String EventType;
    private String EventVoice;
    private String EventWriter;
    private String EventAvgRatings;
    private String EventCriticsRatingsCount;
    private String EventMessage;
    private String EventMessageTitle;
    private String EventUserRatingsCount;
    private String EventUserReviewCount;
    private String EventBitIsTentativeReleaseDate;
    private String EventifierID;
    private String FShareURL;
    private String Genre;
    private List<String> GenreArray = new ArrayList<String>();
    private String ImageCode;
    private String IsComingSoon;
    private String Language;
    private String Length;
    private String ProducerCode;
    private String Ratings;
    private String ReleaseDateCode;
    private String Seq;
    private String TrailerURL;
    private List<ArrDate> arrDates = new ArrayList<ArrDate>();
    private String strMessage;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private boolean is_favorite = false;

    public boolean is_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(boolean is_favorite) {
        this.is_favorite = is_favorite;
    }

    /**
     *
     * @return
     * The Actors
     */
    public String getActors() {
        return Actors;
    }

    /**
     *
     * @param Actors
     * The Actors
     */
    public void setActors(String Actors) {
        this.Actors = Actors;
    }

    /**
     *
     * @return
     * The BannerURL
     */
    public String getBannerURL() {
        return BannerURL;
    }

    /**
     *
     * @param BannerURL
     * The BannerURL
     */
    public void setBannerURL(String BannerURL) {
        this.BannerURL = BannerURL;
    }

    /**
     *
     * @return
     * The CanUserRate
     */
    public String getCanUserRate() {
        return CanUserRate;
    }

    /**
     *
     * @param CanUserRate
     * The CanUserRate
     */
    public void setCanUserRate(String CanUserRate) {
        this.CanUserRate = CanUserRate;
    }

    /**
     *
     * @return
     * The Censor
     */
    public String getCensor() {
        return Censor;
    }

    /**
     *
     * @param Censor
     * The Censor
     */
    public void setCensor(String Censor) {
        this.Censor = Censor;
    }

    /**
     *
     * @return
     * The Director
     */
    public String getDirector() {
        return Director;
    }

    /**
     *
     * @param Director
     * The Director
     */
    public void setDirector(String Director) {
        this.Director = Director;
    }

    /**
     *
     * @return
     * The EventCensor
     */
    public String getEventCensor() {
        return EventCensor;
    }

    /**
     *
     * @param EventCensor
     * The EventCensor
     */
    public void setEventCensor(String EventCensor) {
        this.EventCensor = EventCensor;
    }

    /**
     *
     * @return
     * The EventCode
     */
    public String getEventCode() {
        return EventCode;
    }

    /**
     *
     * @param EventCode
     * The EventCode
     */
    public void setEventCode(String EventCode) {
        this.EventCode = EventCode;
    }

    /**
     *
     * @return
     * The EventIsGlobal
     */
    public String getEventIsGlobal() {
        return EventIsGlobal;
    }

    /**
     *
     * @param EventIsGlobal
     * The EventIsGlobal
     */
    public void setEventIsGlobal(String EventIsGlobal) {
        this.EventIsGlobal = EventIsGlobal;
    }

    /**
     *
     * @return
     * The EventMusic
     */
    public String getEventMusic() {
        return EventMusic;
    }

    /**
     *
     * @param EventMusic
     * The EventMusic
     */
    public void setEventMusic(String EventMusic) {
        this.EventMusic = EventMusic;
    }

    /**
     *
     * @return
     * The EventReleaseDate
     */
    public String getEventReleaseDate() {
        return EventReleaseDate;
    }

    /**
     *
     * @param EventReleaseDate
     * The EventReleaseDate
     */
    public void setEventReleaseDate(String EventReleaseDate) {
        this.EventReleaseDate = EventReleaseDate;
    }

    /**
     *
     * @return
     * The EventSynopsis
     */
    public String getEventSynopsis() {
        return EventSynopsis;
    }

    /**
     *
     * @param EventSynopsis
     * The EventSynopsis
     */
    public void setEventSynopsis(String EventSynopsis) {
        this.EventSynopsis = EventSynopsis;
    }

    /**
     *
     * @return
     * The EventTitle
     */
    public String getEventTitle() {
        return EventTitle;
    }

    /**
     *
     * @param EventTitle
     * The EventTitle
     */
    public void setEventTitle(String EventTitle) {
        this.EventTitle = EventTitle;
    }

    /**
     *
     * @return
     * The EventType
     */
    public String getEventType() {
        return EventType;
    }

    /**
     *
     * @param EventType
     * The EventType
     */
    public void setEventType(String EventType) {
        this.EventType = EventType;
    }

    /**
     *
     * @return
     * The EventVoice
     */
    public String getEventVoice() {
        return EventVoice;
    }

    /**
     *
     * @param EventVoice
     * The EventVoice
     */
    public void setEventVoice(String EventVoice) {
        this.EventVoice = EventVoice;
    }

    /**
     *
     * @return
     * The EventWriter
     */
    public String getEventWriter() {
        return EventWriter;
    }

    /**
     *
     * @param EventWriter
     * The EventWriter
     */
    public void setEventWriter(String EventWriter) {
        this.EventWriter = EventWriter;
    }

    /**
     *
     * @return
     * The EventAvgRatings
     */
    public String getEventAvgRatings() {
        return EventAvgRatings;
    }

    /**
     *
     * @param EventAvgRatings
     * The Event_AvgRatings
     */
    public void setEventAvgRatings(String EventAvgRatings) {
        this.EventAvgRatings = EventAvgRatings;
    }

    /**
     *
     * @return
     * The EventCriticsRatingsCount
     */
    public String getEventCriticsRatingsCount() {
        return EventCriticsRatingsCount;
    }

    /**
     *
     * @param EventCriticsRatingsCount
     * The Event_CriticsRatingsCount
     */
    public void setEventCriticsRatingsCount(String EventCriticsRatingsCount) {
        this.EventCriticsRatingsCount = EventCriticsRatingsCount;
    }

    /**
     *
     * @return
     * The EventMessage
     */
    public String getEventMessage() {
        return EventMessage;
    }

    /**
     *
     * @param EventMessage
     * The Event_Message
     */
    public void setEventMessage(String EventMessage) {
        this.EventMessage = EventMessage;
    }

    /**
     *
     * @return
     * The EventMessageTitle
     */
    public String getEventMessageTitle() {
        return EventMessageTitle;
    }

    /**
     *
     * @param EventMessageTitle
     * The Event_MessageTitle
     */
    public void setEventMessageTitle(String EventMessageTitle) {
        this.EventMessageTitle = EventMessageTitle;
    }

    /**
     *
     * @return
     * The EventUserRatingsCount
     */
    public String getEventUserRatingsCount() {
        return EventUserRatingsCount;
    }

    /**
     *
     * @param EventUserRatingsCount
     * The Event_UserRatingsCount
     */
    public void setEventUserRatingsCount(String EventUserRatingsCount) {
        this.EventUserRatingsCount = EventUserRatingsCount;
    }

    /**
     *
     * @return
     * The EventUserReviewCount
     */
    public String getEventUserReviewCount() {
        return EventUserReviewCount;
    }

    /**
     *
     * @param EventUserReviewCount
     * The Event_UserReviewCount
     */
    public void setEventUserReviewCount(String EventUserReviewCount) {
        this.EventUserReviewCount = EventUserReviewCount;
    }

    /**
     *
     * @return
     * The EventBitIsTentativeReleaseDate
     */
    public String getEventBitIsTentativeReleaseDate() {
        return EventBitIsTentativeReleaseDate;
    }

    /**
     *
     * @param EventBitIsTentativeReleaseDate
     * The Event_bitIsTentativeReleaseDate
     */
    public void setEventBitIsTentativeReleaseDate(String EventBitIsTentativeReleaseDate) {
        this.EventBitIsTentativeReleaseDate = EventBitIsTentativeReleaseDate;
    }

    /**
     *
     * @return
     * The EventifierID
     */
    public String getEventifierID() {
        return EventifierID;
    }

    /**
     *
     * @param EventifierID
     * The EventifierID
     */
    public void setEventifierID(String EventifierID) {
        this.EventifierID = EventifierID;
    }

    /**
     *
     * @return
     * The FShareURL
     */
    public String getFShareURL() {
        return FShareURL;
    }

    /**
     *
     * @param FShareURL
     * The FShareURL
     */
    public void setFShareURL(String FShareURL) {
        this.FShareURL = FShareURL;
    }

    /**
     *
     * @return
     * The Genre
     */
    public String getGenre() {
        return Genre;
    }

    /**
     *
     * @param Genre
     * The Genre
     */
    public void setGenre(String Genre) {
        this.Genre = Genre;
    }

    /**
     *
     * @return
     * The GenreArray
     */
    public List<String> getGenreArray() {
        return GenreArray;
    }

    /**
     *
     * @param GenreArray
     * The GenreArray
     */
    public void setGenreArray(List<String> GenreArray) {
        this.GenreArray = GenreArray;
    }

    /**
     *
     * @return
     * The ImageCode
     */
    public String getImageCode() {
        return ImageCode;
    }

    /**
     *
     * @param ImageCode
     * The ImageCode
     */
    public void setImageCode(String ImageCode) {
        this.ImageCode = ImageCode;
    }

    /**
     *
     * @return
     * The IsComingSoon
     */
    public String getIsComingSoon() {
        return IsComingSoon;
    }

    /**
     *
     * @param IsComingSoon
     * The IsComingSoon
     */
    public void setIsComingSoon(String IsComingSoon) {
        this.IsComingSoon = IsComingSoon;
    }

    /**
     *
     * @return
     * The Language
     */
    public String getLanguage() {
        return Language;
    }

    /**
     *
     * @param Language
     * The Language
     */
    public void setLanguage(String Language) {
        this.Language = Language;
    }

    /**
     *
     * @return
     * The Length
     */
    public String getLength() {
        return Length;
    }

    /**
     *
     * @param Length
     * The Length
     */
    public void setLength(String Length) {
        this.Length = Length;
    }

    /**
     *
     * @return
     * The ProducerCode
     */
    public String getProducerCode() {
        return ProducerCode;
    }

    /**
     *
     * @param ProducerCode
     * The ProducerCode
     */
    public void setProducerCode(String ProducerCode) {
        this.ProducerCode = ProducerCode;
    }

    /**
     *
     * @return
     * The Ratings
     */
    public String getRatings() {
        return Ratings;
    }

    /**
     *
     * @param Ratings
     * The Ratings
     */
    public void setRatings(String Ratings) {
        this.Ratings = Ratings;
    }

    /**
     *
     * @return
     * The ReleaseDateCode
     */
    public String getReleaseDateCode() {
        return ReleaseDateCode;
    }

    /**
     *
     * @param ReleaseDateCode
     * The ReleaseDateCode
     */
    public void setReleaseDateCode(String ReleaseDateCode) {
        this.ReleaseDateCode = ReleaseDateCode;
    }

    /**
     *
     * @return
     * The Seq
     */
    public String getSeq() {
        return Seq;
    }

    /**
     *
     * @param Seq
     * The Seq
     */
    public void setSeq(String Seq) {
        this.Seq = Seq;
    }

    /**
     *
     * @return
     * The TrailerURL
     */
    public String getTrailerURL() {
        return TrailerURL;
    }

    /**
     *
     * @param TrailerURL
     * The TrailerURL
     */
    public void setTrailerURL(String TrailerURL) {
        this.TrailerURL = TrailerURL;
    }

    /**
     *
     * @return
     * The arrDates
     */
    public List<ArrDate> getArrDates() {
        return arrDates;
    }

    /**
     *
     * @param arrDates
     * The arrDates
     */
    public void setArrDates(List<ArrDate> arrDates) {
        this.arrDates = arrDates;
    }

    /**
     *
     * @return
     * The strMessage
     */
    public String getStrMessage() {
        return strMessage;
    }

    /**
     *
     * @param strMessage
     * The strMessage
     */
    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}


