package com.ua.sdk.internal;

import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.util.Streams;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.acra.ACRAConstants;

public class DebugJsonParser<T> implements JsonParser<T> {
    private final JsonParser<T> parser;

    public DebugJsonParser(JsonParser<T> parser2) {
        Precondition.isNotNull(parser2);
        this.parser = parser2;
    }

    public T parse(InputStream inputStream) throws UaException {
        String json = null;
        try {
            json = Streams.readFully(inputStream);
            T parse = this.parser.parse(new ByteArrayInputStream(json.getBytes(ACRAConstants.UTF8)));
            UaLog.debug("response=%s", (Object) json);
            return parse;
        } catch (IOException e) {
            throw new UaException((Throwable) e);
        } catch (Throwable th) {
            UaLog.debug("response=%s", (Object) json);
            throw th;
        }
    }
}
