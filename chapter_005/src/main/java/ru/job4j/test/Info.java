package ru.job4j.test;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 06.07.18
 */


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
