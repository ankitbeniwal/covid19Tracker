package dev.pagalprogrammer.covid19Tracker.models;

public class LocationStats {
    private String country;
    private int confirmedCases, recoveredCases, deaths;
    private int[][] pastRecord;

    public void setCountry(String country) {
        this.country = country;
    }

    public void setConfirmedCases(int confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public void setRecoveredCases(int recoveredCases) {
        this.recoveredCases = recoveredCases;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getCountry() {
        return country;
    }

    public int getConfirmedCases() {
        return confirmedCases;
    }

    public int getRecoveredCases() {
        return recoveredCases;
    }

    public int getDeaths() {
        return deaths;
    }

    public int[][] getPastRecord() {
        return pastRecord;
    }

    public void setPastRecord(int[][] pastRecord) {
        this.pastRecord = pastRecord;
    }
}
