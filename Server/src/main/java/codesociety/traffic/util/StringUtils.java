package codesociety.traffic.util;

public class StringUtils {
    private static String[] roomLabels = new String[] {"in2", "oldCaf", "irc", "tvPit", "ssa", "loft", "msa", "studion"};

    public static String generateRoomJson(String[] values) {
        String json = "{\n";
        for (int i = 0; i < roomLabels.length; i++) {
            json += "\t\"" + roomLabels[i] + "\": " + values[i] + ",\n";
        }
        json = json.replaceFirst(".$", "");
        json += "}";
        return json;
    }

    public static String[] floatArrayToStringArray(float[] floats) {
        String[] strings = new String[floats.length];
        for (int i = 0; i < floats.length; i++) {
            strings[i] = Float.toString(floats[i]);
        }
        return strings;
    }

    public static String stringifyFloatArray(float[] floats) {
        String s = "[";
        for (float f : floats) {
            s += Float.toString(f) + ",";
        }
        s = s.replaceFirst(".$", "");
        s += "]";
        return s;
    }
}
