package ru.job4j.test;

public class Info {
    private int added;
    private int changed;
    private int deleted;

    public void plusNew() {
        this.added++;
    }

    public void plusChanged() {
        this.changed++;
    }

    public void plusDeleted() {
        this.deleted++;
    }

    public void minusDeleted() {
        deleted--;
    }

    public int getDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("added: ")
                .append(added)
                .append(" changed: ")
                .append(changed)
                .append(" deleted: ")
                .append(deleted)
                .toString();
    }
}
