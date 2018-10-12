package ru.job4j.items.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 30.09.18
 */
public class Item {
    private int id;
    private String desc;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MM yyyy")
    private Timestamp created;
    private boolean done = false;

    public Item() {
        this.created = new Timestamp(System.currentTimeMillis());
    }

    public Item(final String desc) {
        this.desc = desc;
        this.created = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public Timestamp getCreated() {
        return created;
    }

    public boolean isDone() {
        return done;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDesc(final String desc) {
        this.desc = desc;
    }

    public void setCreated(final Timestamp created) {
        this.created = created;
    }

    public void setDone(final boolean done) {
        this.done = done;
    }
}
