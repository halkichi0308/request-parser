package burp.com.burp.type;

public enum ParamType {
    URL(0, "URL"), BODY(1, "Body"), COOKIE(2, "Cookie"), XML(3, "XML"), XML_ATTR(4, "XML_ATTR"),
    MULTIPART(5, "MALTIPART"), JSON(6, "JSON"),;

    final int type;
    final String name;

    private ParamType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static ParamType typeOf(int type) {
        for (final ParamType paramType : values()) {
            if (paramType.type == type) {
                return paramType;
            }
        }
        // return ParamType.BODY;
        throw new IllegalArgumentException("Unknown type: " + type);
    }

    @Override
    public String toString() {
        return this.name;
    }
}