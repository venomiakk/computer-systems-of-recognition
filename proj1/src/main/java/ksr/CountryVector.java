package ksr;

public class CountryVector {

    private String country;
    private Vector vector;

    public CountryVector(String country, Vector vector) {
        this.country = country;
        this.vector = vector;
    }

    public String getCountry() {
        return country;
    }

    public Vector getVector() {
        return vector;
    }

    @Override
    public String toString() {
        return "Country: " + country + ", Vector: " + vector;
    }

}
