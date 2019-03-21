package edu.fpt.comp1640.config;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static edu.fpt.comp1640.utils.StringUtils.escape;
import static edu.fpt.comp1640.utils.StringUtils.unescape;

/**
 * @author duclm
 */
public class Ini extends LinkedHashMap<String, Map<String, String>> {

    private static final char[] hexDigit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final Pattern sectionPattern = Pattern.compile("\\s*\\[([^]]*)]\\s*");
    private static final Pattern keyValuePattern = Pattern.compile("\\s*([^=]*)=(.*)");
    private static final HashSet<Character> comments = new HashSet<Character>() {{
        add('#');
        add(';');
    }};

    public Ini() {
    }

    public Ini(String path) throws IOException {
        load(path);
    }

    public Ini(InputStream stream) throws IOException {
        load(stream);
    }

    public static void defineCommentChars(Character... chars) {
        comments.clear();
        comments.addAll(Arrays.asList(chars));
    }

    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }

    public void save(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        getSections().forEach(section -> {
            String key = section.getKey();
            Set<Entry<String, String>> entrySet = section.getValue().entrySet();

            sb.append('[').append(key).append(']').append('\n');
            entrySet.forEach(x -> sb.append(escape(x.getKey())).append('=')
                .append(escape(x.getValue())).append('\n'));
            sb.append('\n');
        });

        try (OutputStream stream = new FileOutputStream(path)) {
            stream.write(sb.toString().getBytes());
        }
    }

    public void load(String path) throws IOException {
        this.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            parse(br);
        }
    }

    public void load(InputStream stream) throws IOException {
        this.clear();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            parse(br);
        }
    }

    private void parse(BufferedReader br) throws IOException {
        String line;
        String section = null;
        while ((line = br.readLine()) != null) {
            if (line.length() < 1 || comments.contains(line.trim().charAt(0))) {
                continue;
            }
            Matcher checker = sectionPattern.matcher(line);
            if (checker.matches()) {
                section = checker.group(1).trim();
            } else {
                checker = keyValuePattern.matcher(line);
                if (!checker.matches()) {
                    continue;
                }

                String key = unescape(checker.group(1).trim());
                String value = unescape(checker.group(2).trim());
                put(section, key, value);
            }
        }
    }

    public Map<String, String> getSection(String s) {
        return get(s);
    }

    public Set<Entry<String, Map<String, String>>> getSections() {
        return entrySet();
    }

    public void put(String section, String key, String value) {
        computeIfAbsent(section, k -> new LinkedHashMap<>()).put(key, value);
    }

    public String get(String section, String key) {
        Map<String, String> map = get(section);
        return map == null ? null : map.get(key);
    }

    public String get(String section, String key, String defaultValue) {
        String s = get(section, key);
        return (s != null) ? s : defaultValue;
    }

    public int getInt(String section, String key, int defaultValue) {
        String s = get(section, key);
        try {
            return (s != null) ? Integer.parseInt(s) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public float getFloat(String section, String key, float defaultValue) {
        String s = get(section, key);
        try {
            return (s != null) ? Float.parseFloat(s) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public double getDouble(String section, String key, double defaultValue) {
        String s = get(section, key);
        try {
            return (s != null) ? Double.parseDouble(s) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }
}

