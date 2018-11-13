package ru.job4j.callboard.models;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 15.10.2018
 */
public class Photo {
    private int id;
    private String path;

    public Photo() {

    }

    public Photo(final int id) {
        this.id = id;
    }

    public Photo(final String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Photo{"
                + "id=" + id
                + ", path='" + path + '\''
                + '}';
    }
}
