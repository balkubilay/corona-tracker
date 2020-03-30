package com.example.coronatracker;

public class CoronaDetailInfoFields{
    private String lastUp;
    private String confirmed;
    private String deaths;
    private String recovered;
    private String id;

    CoronaDetailInfoFields(CoronaDetailInfoFieldsBuilder coronaDetailInfoFieldsBuilder) {
        this.lastUp=coronaDetailInfoFieldsBuilder.lastUp;
        this.confirmed=coronaDetailInfoFieldsBuilder.confirmed;
        this.deaths=coronaDetailInfoFieldsBuilder.deaths;
        this.recovered=coronaDetailInfoFieldsBuilder.recovered;
        this.id=coronaDetailInfoFieldsBuilder.id;
    }

    static class CoronaDetailInfoFieldsBuilder{
        String lastUp;
        String confirmed;
        String deaths;
        String recovered;
        String id;

        public CoronaDetailInfoFieldsBuilder setId(String id){
            this.id=id;
            return this;
        }

        public CoronaDetailInfoFieldsBuilder setLastUp(String lastUp) {
            this.lastUp = lastUp;
            return this;
        }

        public CoronaDetailInfoFieldsBuilder setConfirmed(String confirmed) {
            this.confirmed = confirmed;
            return this;
        }

        public CoronaDetailInfoFieldsBuilder setDeaths(String deaths) {
            this.deaths = deaths;
            return this;
        }

        public CoronaDetailInfoFieldsBuilder setRecovered(String recovered) {
            this.recovered = recovered;
            return this;
        }

        public CoronaDetailInfoFields build(){
            return new CoronaDetailInfoFields(this);
        }
    }

    public String getLastUp() {
        return lastUp;
    }

    public void setLastUp(String lastUp) {
        this.lastUp = lastUp;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}