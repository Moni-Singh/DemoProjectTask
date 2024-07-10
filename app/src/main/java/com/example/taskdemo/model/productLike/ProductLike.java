package com.example.taskdemo.model.productLike;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class ProductLike implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int ID=0;

    @ColumnInfo(name = "product")
    String title="";
    @ColumnInfo(name = "isLiked")
    Boolean isLiked = false;
    @ColumnInfo(name = "isBookmarked")
    Boolean isBookMarked = false;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    public Boolean getBookMarked() {
        return isBookMarked;
    }

    public void setBookMarked(Boolean bookMarked) {
        isBookMarked = bookMarked;
    }
}
