package org.curtis.database;

public class DBException extends Exception {
    public DBException(String msg) {
        super(msg);
    }

    public DBException(Exception e) {
        super(e);
    }
}
