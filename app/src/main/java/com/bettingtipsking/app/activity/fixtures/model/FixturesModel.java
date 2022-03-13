package com.bettingtipsking.app.activity.fixtures.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class FixturesModel  implements Parcelable {
    int leagueId;
    String leagueName;
    String leagueCountry;
    String leagueLogo;
    String leagueFlag;
    String leagueSeason;
    String leagueRound;
    List<MatchDetailsModel> matchesList;

    public FixturesModel(){ }

    public FixturesModel(int leagueId, String leagueName, String leagueCountry, String leagueLogo, String leagueFlag, String leagueSeason, String leagueRound, List<MatchDetailsModel> matchesList){
        this.leagueId = leagueId;
        this.leagueName = leagueName;
        this.leagueCountry = leagueCountry;
        this.leagueLogo = leagueLogo;
        this.leagueFlag = leagueFlag;
        this.leagueSeason = leagueSeason;
        this.leagueRound = leagueRound;
        this.matchesList = matchesList;
    }

    protected FixturesModel(Parcel in) {
        leagueId = in.readInt();
        leagueName = in.readString();
        leagueCountry = in.readString();
        leagueLogo = in.readString();
        leagueFlag = in.readString();
        leagueSeason = in.readString();
        leagueRound = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(leagueId);
        dest.writeString(leagueName);
        dest.writeString(leagueCountry);
        dest.writeString(leagueLogo);
        dest.writeString(leagueFlag);
        dest.writeString(leagueSeason);
        dest.writeString(leagueRound);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FixturesModel> CREATOR = new Creator<FixturesModel>() {
        @Override
        public FixturesModel createFromParcel(Parcel in) {
            return new FixturesModel(in);
        }

        @Override
        public FixturesModel[] newArray(int size) {
            return new FixturesModel[size];
        }
    };

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getLeagueCountry() {
        return leagueCountry;
    }

    public void setLeagueCountry(String leagueCountry) {
        this.leagueCountry = leagueCountry;
    }

    public String getLeagueLogo() {
        return leagueLogo;
    }

    public void setLeagueLogo(String leagueLogo) {
        this.leagueLogo = leagueLogo;
    }

    public String getLeagueFlag() {
        return leagueFlag;
    }

    public void setLeagueFlag(String leagueFlag) {
        this.leagueFlag = leagueFlag;
    }

    public String getLeagueSeason() {
        return leagueSeason;
    }

    public void setLeagueSeason(String leagueSeason) {
        this.leagueSeason = leagueSeason;
    }

    public String getLeagueRound() {
        return leagueRound;
    }

    public void setLeagueRound(String leagueRound) {
        this.leagueRound = leagueRound;
    }

    public List<MatchDetailsModel> getMatchesList() {
        return matchesList;
    }

    public void setMatchesList(List<MatchDetailsModel> matchesList) {
        this.matchesList = matchesList;
    }
}
