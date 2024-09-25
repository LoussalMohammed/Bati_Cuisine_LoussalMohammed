package org.app.tools;

public class DataFile {
    public static void main(String[] args) {

    }
    public DataFile() {}

    protected static String url() {
        return "jdbc:postgresql://mohammed:5432/batiCuisine";
    }

    protected static String user() {
        return "postgres";
    }

    protected static String password() {
        return "superman";
    }
}
