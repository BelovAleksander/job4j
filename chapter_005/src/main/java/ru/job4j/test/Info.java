package ru.job4j.test;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 06.07.18
 */


public class Info {
    private int added;
    private int changed;
    private int deleted;

    public void plusNew(int change) {
        this.added += change;
    }

    public void plusChanged(int change) {
        this.changed += change;
    }

    public void plusDeleted(int change) {
        this.deleted += change;
    }

    public int getNew() {
        return this.added;
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
