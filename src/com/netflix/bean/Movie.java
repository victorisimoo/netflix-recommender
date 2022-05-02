package com.netflix.bean;

public class Movie {

    private int index;
    private String title;
    private String director;
    private String points;

    public Movie(){

    }

    public Movie(int index, String title, String director, String genre) {
        this.index = index;
        this.title = title;
        this.director = director;
        this.points = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getPoints() {
        return points;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String toString() {
        return index + ") titulo=" + title + ", director=" + director + ", points=" + points;
    }

    public String getWithGenre() {
        return index + ") titulo=" + title + ", director=" + director + ", genero=" + points;
    }

}
