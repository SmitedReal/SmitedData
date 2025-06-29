import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.*;

public class SDAT {
    // SDAT can be an object, array, or primitive value
    private Object value;

    // Constructors
    public SDAT(Object value) {
        this.value = value;
    }

    public SDAT() {
        this.value = new LinkedHashMap<String, SDAT>();
    }

    // Factory methods
    public static SDAT object() {
        return new SDAT(new LinkedHashMap<String, SDAT>());
    }

    public static SDAT array() {
        return new SDAT(new ArrayList<SDAT>());
    }

    // Type checking
    public boolean isObject() {
        return value instanceof Map;
    }

    public boolean isArray() {
        return value instanceof List;
    }

    public boolean isString() {
        return value instanceof String;
    }

    public boolean isNumber() {
        return value instanceof Number;
    }

    public boolean isBoolean() {
        return value instanceof Boolean;
    }

    public boolean isNull() {
        return value == null;
    }

    // Getters with type safety
    public Map<String, SDAT> getObject() {
        if (!isObject()) {
            throw new ClassCastException("Not an SDAT object");
        }
        return (Map<String, SDAT>) value;
    }

    public List<SDAT> getArray() {
        if (!isArray()) {
            throw new ClassCastException("Not an SDAT array");
        }
        return (List<SDAT>) value;
    }

    public String getString() {
        if (!isString()) {
            throw new ClassCastException("Not an SDAT string");
        }
        return (String) value;
    }

    public Number getNumber() {
        if (!isNumber()) {
            throw new ClassCastException("Not an SDAT number");
        }
        return (Number) value;
    }

    public Boolean getBoolean() {
        if (!isBoolean()) {
            throw new ClassCastException("Not an SDAT boolean");
        }
        return (Boolean) value;
    }

    // Object operations
    public SDAT put(String key, Object value) {
        if (!isObject()) {
            throw new UnsupportedOperationException("Not an object");
        }
        getObject().put(key, wrap(value));
        return this;
    }

    public SDAT get(String key) {
        if (!isObject()) {
            throw new UnsupportedOperationException("Not an object");
        }
        return getObject().get(key);
    }

    // Array operations
    public SDAT add(Object value) {
        if (!isArray()) {
            throw new UnsupportedOperationException("Not an array");
        }
        getArray().add(wrap(value));
        return this;
    }

    public SDAT get(int index) {
        if (!isArray()) {
            throw new UnsupportedOperationException("Not an array");
        }
        return getArray().get(index);
    }

    // Helper to wrap Java objects in SDAT
    private static SDAT wrap(Object value) {
        if (value instanceof SDAT) {
            return (SDAT) value;
        } else if (value instanceof Map) {
            SDAT sdat = SDAT.object();
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet()) {
                sdat.put(entry.getKey().toString(), entry.getValue());
            }
            return sdat;
        } else if (value instanceof Iterable) {
            SDAT sdat = SDAT.array();
            for (Object item : (Iterable<?>) value) {
                sdat.add(item);
            }
            return sdat;
        } else if (value.getClass().isArray()) {
            SDAT sdat = SDAT.array();
            for (Object item : (Object[]) value) {
                sdat.add(item);
            }
            return sdat;
        }
        return new SDAT(value);
    }

    // Serialization to SDAT string format
    @Override
    public String toString() {
        return serialize(this);
    }

    public static String serialize(SDAT sdat) {
        if (sdat.isObject()) {
            StringBuilder sb = new StringBuilder("{");
            boolean first = true;
            for (Map.Entry<String, SDAT> entry : sdat.getObject().entrySet()) {
                if (!first) sb.append(",");
                sb.append("\"").append(escapeString(entry.getKey())).append("\":");
                sb.append(serialize(entry.getValue()));
                first = false;
            }
            sb.append("}");
            return sb.toString();
        } else if (sdat.isArray()) {
            StringBuilder sb = new StringBuilder("[");
            boolean first = true;
            for (SDAT item : sdat.getArray()) {
                if (!first) sb.append(",");
                sb.append(serialize(item));
                first = false;
            }
            sb.append("]");
            return sb.toString();
        } else if (sdat.isString()) {
            return "\"" + escapeString(sdat.getString()) + "\"";
        } else if (sdat.isNumber() || sdat.isBoolean()) {
            return sdat.value.toString();
        } else if (sdat.isNull()) {
            return "null";
        }
        throw new RuntimeException("Unknown SDAT type");
    }

    private static String escapeString(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            switch (c) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\b': sb.append("\\b"); break;
                case '\f': sb.append("\\f"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (c <= 0x1F) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        return sb.toString();
    }

    // Deserialization from SDAT string format
    public static SDAT parse(String sdatString) {
        return new SDATParser(sdatString).parse();
    }
    // New binary serialization methods
    public void saveToFile(String filename) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(filename))) {
            // Magic number and version
            out.writeInt(0x53444154); // "SDAT" in hex
            out.writeByte(1);         // Version

            // Compress the JSON string
            byte[] jsonBytes = this.toString().getBytes(StandardCharsets.UTF_8);
            byte[] compressed = compress(jsonBytes);

            // Write obfuscated data
            out.writeInt(compressed.length);
            byte[] obfuscated = obfuscate(compressed);
            out.write(obfuscated);
        }
    }

    public static SDAT loadFromFile(String filename) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(filename))) {
            // Verify magic number
            if (in.readInt() != 0x53444154) {
                throw new IOException("Not a valid SDAT file");
            }

            // Read version
            byte version = in.readByte();
            if (version != 1) {
                throw new IOException("Unsupported SDAT version");
            }

            // Read data
            int length = in.readInt();
            byte[] obfuscated = new byte[length];
            in.readFully(obfuscated);

            // Deobfuscate and decompress
            byte[] compressed = deobfuscate(obfuscated);
            byte[] jsonBytes = decompress(compressed);

            return SDAT.parse(new String(jsonBytes, StandardCharsets.UTF_8));
        }
    }

    private static byte[] obfuscate(byte[] data) {
        byte[] result = new byte[data.length];
        // Simple XOR obfuscation with a key
        byte[] key = "SDAT_SECRET".getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ key[i % key.length]);
        }
        return result;
    }

    private static byte[] deobfuscate(byte[] data) {
        // Obfuscation is symmetric (XOR again to deobfuscate)
        return obfuscate(data);
    }

    private static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try (GZIPOutputStream gzip = new GZIPOutputStream(bos)) {
            gzip.write(data);
        }
        return bos.toByteArray();
    }

    private static byte[] decompress(byte[] compressed) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (GZIPInputStream gzip = new GZIPInputStream(bis)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzip.read(buffer)) > 0) {
                bos.write(buffer, 0, len);
            }
        }
        return bos.toByteArray();
    }
}

    // Parser implementation
     class SDATParser {
        private final String str;
        private int pos = 0;

        SDATParser(String str) {
            this.str = str;
        }

        SDAT parse() {
            skipWhitespace();
            char c = peekChar();
            if (c == '{') {
                return parseObjectValue();
            } else if (c == '[') {
                return parseArrayValue();
            } else if (c == '"') {
                return new SDAT(parseStringValue());
            } else if (c == '-' || (c >= '0' && c <= '9')) {
                return new SDAT(parseNumberValue());
            } else if (c == 't' || c == 'f') {
                return new SDAT(parseBooleanValue());
            } else if (c == 'n') {
                parseNullValue();
                return new SDAT(null);
            }
            throw new RuntimeException("Unexpected character: " + c);
        }

        private SDAT parseObjectValue() {
            Map<String, SDAT> obj = new LinkedHashMap<>();
            expect('{');
            skipWhitespace();
            if (peekChar() == '}') {
                nextChar();
                return new SDAT(obj);
            }
            while (true) {
                skipWhitespace();
                String key = parseStringValue();
                skipWhitespace();
                expect(':');
                SDAT value = parse();
                obj.put(key, value);
                skipWhitespace();
                char c = nextChar();
                if (c == '}') break;
                if (c != ',') throw new RuntimeException("Expected ',' or '}'");
            }
            return new SDAT(obj);
        }

        private SDAT parseArrayValue() {
            List<SDAT> arr = new ArrayList<>();
            expect('[');
            skipWhitespace();
            if (peekChar() == ']') {
                nextChar();
                return new SDAT(arr);
            }
            while (true) {
                skipWhitespace();
                arr.add(parse());
                skipWhitespace();
                char c = nextChar();
                if (c == ']') break;
                if (c != ',') throw new RuntimeException("Expected ',' or ']'");
            }
            return new SDAT(arr);
        }

        private String parseStringValue() {
            expect('"');
            StringBuilder sb = new StringBuilder();
            while (true) {
                char c = nextChar();
                if (c == '"') break;
                if (c == '\\') {
                    c = nextChar();
                    switch (c) {
                        case '"': sb.append('"'); break;
                        case '\\': sb.append('\\'); break;
                        case '/': sb.append('/'); break;
                        case 'b': sb.append('\b'); break;
                        case 'f': sb.append('\f'); break;
                        case 'n': sb.append('\n'); break;
                        case 'r': sb.append('\r'); break;
                        case 't': sb.append('\t'); break;
                        case 'u':
                            String hex = str.substring(pos, pos + 4);
                            pos += 4;
                            sb.append((char) Integer.parseInt(hex, 16));
                            break;
                        default:
                            throw new RuntimeException("Invalid escape sequence: \\" + c);
                    }
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        private Number parseNumberValue() {
            int start = pos;
            if (peekChar() == '-') nextChar();
            while (peekChar() >= '0' && peekChar() <= '9') nextChar();
            if (peekChar() == '.') {
                nextChar();
                while (peekChar() >= '0' && peekChar() <= '9') nextChar();
            }
            if (peekChar() == 'e' || peekChar() == 'E') {
                nextChar();
                if (peekChar() == '+' || peekChar() == '-') nextChar();
                while (peekChar() >= '0' && peekChar() <= '9') nextChar();
            }
            String numStr = str.substring(start, pos);
            try {
                if (numStr.contains(".") || numStr.contains("e") || numStr.contains("E")) {
                    return Double.parseDouble(numStr);
                } else {
                    long l = Long.parseLong(numStr);
                    if (l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE) {
                        return (int) l;
                    }
                    return l;
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid number: " + numStr, e);
            }
        }

        private Boolean parseBooleanValue() {
            if (str.startsWith("true", pos)) {
                pos += 4;
                return true;
            } else if (str.startsWith("false", pos)) {
                pos += 5;
                return false;
            }
            throw new RuntimeException("Expected 'true' or 'false'");
        }

        private void parseNullValue() {
            if (str.startsWith("null", pos)) {
                pos += 4;
            } else {
                throw new RuntimeException("Expected 'null'");
            }
        }

        private void expect(char expected) {
            char c = nextChar();
            if (c != expected) {
                throw new RuntimeException("Expected '" + expected + "' but found '" + c + "'");
            }
        }

        private char peekChar() {
            if (pos >= str.length()) {
                throw new RuntimeException("Unexpected end of input");
            }
            return str.charAt(pos);
        }

        private char nextChar() {
            if (pos >= str.length()) {
                throw new RuntimeException("Unexpected end of input");
            }
            return str.charAt(pos++);
        }

        private void skipWhitespace() {
            while (pos < str.length() && Character.isWhitespace(str.charAt(pos))) {
                pos++;
            }
        }
    }