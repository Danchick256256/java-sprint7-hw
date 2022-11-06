package util;

public class generateId {
    private static int id = 0;

    public static int generateTaskId() {
        return ++id;
    }
}
