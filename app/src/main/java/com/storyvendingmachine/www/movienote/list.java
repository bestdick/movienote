package com.storyvendingmachine.www.movienote;

/**
 * Created by Administrator on 2018-01-20.
 */

public class list {
    String movie_title;
    String movie_main;
    String author;
    String w_date;
    String w_time;
    String w_year;
    String thumb_nail;

    public String getImage_names() {
        return image_names;
    }

    public void setImage_names(String image_names) {
        this.image_names = image_names;
    }

    String image_names;






    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_main() {
        return movie_main;
    }

    public void setMovie_main(String movie_main) {
        this.movie_main = movie_main;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getW_date() {
        return w_date;
    }

    public void setW_date(String w_date) {
        this.w_date = w_date;
    }

    public String getW_time() {
        return w_time;
    }

    public void setW_time(String w_time) {
        this.w_time = w_time;
    }

    public String getW_year() {
        return w_year;
    }

    public void setW_year(String w_year) {
        this.w_year = w_year;
    }

    public String getThumb_nail() {
        return thumb_nail;
    }

    public void setThumb_nail(String thumb_nail) {
        this.thumb_nail = thumb_nail;
    }

    public list(String movie_title, String movie_main, String author, String w_date, String w_time, String w_year, String thumb_nail, String image_names) {
        this.movie_title = movie_title;
        this.movie_main = movie_main;
        this.author = author;
        this.w_date = w_date;
        this.w_time = w_time;
        this.w_year = w_year;
        this.thumb_nail = thumb_nail;
        this.image_names = image_names;
    }
}

