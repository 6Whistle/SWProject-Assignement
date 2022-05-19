package com.junhwei.board;


import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Info {
    private @Id @GeneratedValue Long id;
    private String title;
    private String text;
    private String date;
    private int count;

    Info(){}
    Info(String title, String text, String date, int count){
        this.title = title;
        this.text = text;
        this.date = date;
        this.count = count;
    }

    public Long getId(){ return id; };
    public String getTitle(){ return title; }
    public String getText(){ return text; }
    public String getDate() { return date; }
    public int getCount(){ return count; }
    public void setId(Long id){ this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setText(String text) { this.text = text; }
    public void setDate(String date) { this.date = date; }
    public void setCount(int count) { this.count = count; }

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(!(o instanceof Info))
            return false;
        Info info = (Info) o;
        return Objects.equals(this.id, info.id) && Objects.equals(this.title, info.title) &&
                Objects.equals(this.text, info.text) && Objects.equals(this.date, info.date) &&
                Objects.equals(this.count, info.count);
    }

    @Override
    public int hashCode(){ return Objects.hash(this.id, this.title, this.date, this.count); }

    @Override
    public String toString(){ return "Info{" + "id=" + this.id + ", title='" + this.title + '\'' + ", text='" +
            this.text + '\'' + ", date='" + this.date + '\'' + ", count=" + this.count + '}';
    }
}
