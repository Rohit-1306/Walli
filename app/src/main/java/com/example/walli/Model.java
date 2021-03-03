package com.example.walli;

public class Model {
    private int id;
    private String originalURL,mediumURL;

    public Model() {
    }

    public Model(int id, String originalURL, String mediumURL) {
        this.id = id;
        this.originalURL = originalURL;
        this.mediumURL = mediumURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public String getMediumURL() {
        return mediumURL;
    }

    public void setMediumURL(String mediumURL) {
        this.mediumURL = mediumURL;
    }
}
