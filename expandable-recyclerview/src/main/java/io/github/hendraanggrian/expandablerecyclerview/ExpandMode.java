package io.github.hendraanggrian.expandablerecyclerview;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public enum ExpandMode {
    SINGLE(0), ANY(1);

    private int id;

    ExpandMode(int id) {
        this.id = id;
    }

    public static ExpandMode from(int id) {
        for (ExpandMode mode : values())
            if (mode.id == id)
                return mode;
        throw new RuntimeException("");
    }
}