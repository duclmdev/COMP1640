package edu.fpt.comp1640.utils;

import edu.fpt.comp1640.database.Database;
import edu.fpt.comp1640.database.ResultSetHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DatabaseUtils {
    public static String getJSON(String sql, String[] fields, Object[] param) throws Exception {
        JSONArray result = new JSONArray();
        getResult(sql, param, rs -> {
            JSONObject object = new JSONObject();
            for (int i = 1, columnCount = fields.length; i <= columnCount; i++) {
                object.put(fields[i - 1], rs.getObject(i));
            }
            result.add(object);
        });
        return result.toJSONString();
    }

    public static void getResult(String sql, Object[] param, ResultSetHandler handler) throws Exception {
        try (Database db = new Database()) {
            db.query(sql, param, rs -> {
                while (rs.next()) {
                    handler.handle(rs);
                }
            });
        }
    }
}
