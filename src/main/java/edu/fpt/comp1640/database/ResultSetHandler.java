package edu.fpt.comp1640.database;

import java.sql.ResultSet;

@FunctionalInterface
public interface ResultSetHandler {
    void handle(ResultSet rs) throws Exception;
}
