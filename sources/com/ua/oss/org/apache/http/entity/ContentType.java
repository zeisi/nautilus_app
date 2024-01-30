package com.ua.oss.org.apache.http.entity;

import com.ua.oss.org.apache.http.Consts;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.HeaderValueParser;

public final class ContentType {
    public static final ContentType DEFAULT_TEXT = TEXT_PLAIN;
    public static final ContentType TEXT_PLAIN = create("text/plain", Consts.ISO_8859_1);
    private final Charset charset;
    private final String mimeType;

    ContentType(String mimeType2, Charset charset2) {
        this.mimeType = mimeType2;
        this.charset = charset2;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.mimeType);
        if (this.charset != null) {
            buf.append("; charset=");
            buf.append(this.charset.name());
        }
        return buf.toString();
    }

    private static boolean valid(String s) {
        for (int i = 0; i < s.length(); i++) {
            char ch2 = s.charAt(i);
            if (ch2 == '\"' || ch2 == ',' || ch2 == ';') {
                return false;
            }
        }
        return true;
    }

    public static ContentType create(String mimeType2, Charset charset2) {
        if (mimeType2 == null) {
            throw new IllegalArgumentException("MIME type may not be null");
        }
        String type = mimeType2.trim().toLowerCase(Locale.US);
        if (type.length() == 0) {
            throw new IllegalArgumentException("MIME type may not be empty");
        } else if (valid(type)) {
            return new ContentType(type, charset2);
        } else {
            throw new IllegalArgumentException("MIME type may not contain reserved characters");
        }
    }

    public static ContentType create(String mimeType2) {
        return new ContentType(mimeType2, (Charset) null);
    }

    public static ContentType create(String mimeType2, String charset2) throws UnsupportedCharsetException {
        return create(mimeType2, charset2 != null ? Charset.forName(charset2) : null);
    }

    private static ContentType create(HeaderElement helem) {
        String mimeType2 = helem.getName();
        String charset2 = null;
        NameValuePair param = helem.getParameterByName("charset");
        if (param != null) {
            charset2 = param.getValue();
        }
        return create(mimeType2, charset2);
    }

    public static ContentType parse(String s) throws ParseException, UnsupportedCharsetException {
        if (s == null) {
            throw new IllegalArgumentException("Content type may not be null");
        }
        HeaderElement[] elements = BasicHeaderValueParser.parseElements(s, (HeaderValueParser) null);
        if (elements.length > 0) {
            return create(elements[0]);
        }
        throw new ParseException("Invalid content type: " + s);
    }

    public static ContentType get(HttpEntity entity) throws ParseException, UnsupportedCharsetException {
        Header header;
        if (entity == null || (header = entity.getContentType()) == null) {
            return null;
        }
        HeaderElement[] elements = header.getElements();
        if (elements.length > 0) {
            return create(elements[0]);
        }
        return null;
    }
}
