package com.ua.sdk.internal;

import com.j256.ormlite.stmt.query.SimpleComparison;
import com.nautilus.omni.bleservices.BLEScanManager;
import com.ua.sdk.UaLog;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.acra.ACRAConstants;

public class BaseReferenceBuilder {
    private static final String SELF_KEY = "self";
    private boolean didParseTemplateParams = false;
    private boolean dirty = false;
    private int expectedLength = 0;
    private String href;
    private String hrefTemplate;
    private boolean multiGet = false;
    ArrayList<Param> params = null;
    ArrayList<String> selfParams = null;

    protected BaseReferenceBuilder(String hrefTemplate2) {
        hrefTemplate2 = hrefTemplate2 == null ? "" : hrefTemplate2;
        this.hrefTemplate = hrefTemplate2;
        this.expectedLength += hrefTemplate2.length();
    }

    /* access modifiers changed from: protected */
    public void addSelfParam(int value) {
        addSelfParam(String.valueOf(value));
    }

    /* access modifiers changed from: protected */
    public void addSelfParam(String value) {
        if (value != null) {
            if (this.params == null) {
                this.params = new ArrayList<>(8);
            }
            if (this.selfParams == null) {
                this.selfParams = new ArrayList<>(8);
            }
            this.dirty = true;
            this.multiGet = true;
            this.selfParams.add(value);
            this.expectedLength += value.length();
        }
    }

    /* access modifiers changed from: protected */
    public void setParam(String key, int value) {
        setParam(key, String.valueOf(value));
    }

    /* access modifiers changed from: protected */
    public void setParam(String key, String value) {
        if (key != null) {
            if (value != null) {
                if (this.params == null) {
                    this.params = new ArrayList<>(8);
                }
                this.dirty = true;
                Param param = getParam(key);
                if (param == null) {
                    this.params.add(new Param(key, value));
                    this.expectedLength += key.length() + value.length() + 2;
                    return;
                }
                this.expectedLength += value.length() - param.value.length();
                param.value = value;
            } else if (removeParam(key) != null) {
                this.dirty = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void setParams(String key, String... values) {
        if (key != null) {
            if (values != null && values.length != 0) {
                if (this.params == null) {
                    this.params = new ArrayList<>(8);
                }
                this.dirty = true;
                for (int i = 0; i < values.length; i++) {
                    this.params.add(new Param(key, values[i]));
                    this.expectedLength += key.length() + values[i].length() + 2;
                }
            } else if (removeParams(key) != null) {
                this.dirty = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void parseTemplateParams() {
        if (!this.didParseTemplateParams) {
            this.didParseTemplateParams = true;
            int paramsStart = this.hrefTemplate.indexOf(63);
            if (paramsStart >= 0) {
                String params2 = this.hrefTemplate.substring(paramsStart);
                this.hrefTemplate = this.hrefTemplate.substring(0, paramsStart);
                this.dirty = true;
                int paramLength = params2.length();
                int startIndex = 1;
                while (startIndex < paramLength) {
                    int equalsIndex = params2.indexOf(61, startIndex);
                    if (equalsIndex < 0) {
                        throw new IllegalArgumentException(this.hrefTemplate + " is incorrectly formatted.");
                    }
                    String key = params2.substring(startIndex, equalsIndex);
                    int ampIndex = params2.indexOf(38, startIndex);
                    if (ampIndex < 0) {
                        ampIndex = paramLength;
                    }
                    setParam(key, params2.substring(equalsIndex + 1, ampIndex));
                    startIndex = ampIndex + 1;
                }
            }
        }
    }

    public String getHref() {
        if (!this.dirty) {
            if (this.href == null) {
                this.href = this.hrefTemplate;
            }
            return this.href;
        }
        if (this.multiGet) {
            this.expectedLength += "self".length();
        }
        StringBuilder out = new StringBuilder(this.expectedLength);
        writeHref(out);
        writeParams(out);
        writeSelfParams(out);
        return out.toString();
    }

    /* access modifiers changed from: protected */
    public Param removeParam(String key) {
        parseTemplateParams();
        if (this.params == null) {
            return null;
        }
        int size = this.params.size();
        for (int i = 0; i < size; i++) {
            Param param = this.params.get(i);
            if (param.key.equals(key)) {
                this.params.remove(i);
                this.expectedLength -= (param.key.length() + param.value.length()) + 2;
                return param;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public List<Param> removeParams(String key) {
        parseTemplateParams();
        if (this.params == null) {
            return null;
        }
        int size = this.params.size();
        List<Param> removedParams = null;
        for (int i = 0; i < size; i++) {
            Param param = this.params.get(i);
            if (param.key.equals(key)) {
                if (removedParams == null) {
                    removedParams = new ArrayList<>();
                }
                this.params.remove(i);
                this.expectedLength -= (param.key.length() + param.value.length()) + 2;
                removedParams.add(param);
            }
        }
        return removedParams;
    }

    /* access modifiers changed from: protected */
    public Param getParam(String key) {
        parseTemplateParams();
        if (this.params == null) {
            return null;
        }
        int size = this.params.size();
        for (int i = 0; i < size; i++) {
            Param param = this.params.get(i);
            if (param.key.equals(key)) {
                return param;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public List<Param> getParams(String key) {
        parseTemplateParams();
        if (this.params == null) {
            return null;
        }
        int size = this.params.size();
        List<Param> paramsList = null;
        for (int i = 0; i < size; i++) {
            Param param = this.params.get(i);
            if (param.key.equals(key)) {
                if (paramsList == null) {
                    paramsList = new ArrayList<>();
                }
                paramsList.add(param);
            }
        }
        return paramsList;
    }

    private void writeHref(StringBuilder out) {
        String template = this.hrefTemplate;
        boolean escaped = false;
        int openBrackets = 0;
        int closeBrackets = 0;
        int open = -1;
        int length = template.length();
        for (int i = 0; i < length; i++) {
            char c = template.charAt(i);
            if (!escaped) {
                switch (c) {
                    case '\\':
                        escaped = true;
                        out.append(c);
                        break;
                    case '{':
                        openBrackets++;
                        if (openBrackets != 1) {
                            break;
                        } else {
                            open = i;
                            break;
                        }
                    case BLEScanManager.CONVERT_TO_SIGNAL_STRENGTH:
                        if (openBrackets <= 0) {
                            out.append(c);
                            break;
                        } else {
                            closeBrackets++;
                            if (openBrackets != closeBrackets) {
                                break;
                            } else {
                                open = -1;
                                openBrackets = 0;
                                closeBrackets = 0;
                                Param param = getParam(template.substring(open + openBrackets, (i + 1) - closeBrackets));
                                param.isTemplateParam = true;
                                if (param != null) {
                                    out.append(urlEncode(param.value));
                                    break;
                                } else {
                                    out.append("null");
                                    break;
                                }
                            }
                        }
                    default:
                        if (open >= 0) {
                            break;
                        } else {
                            out.append(c);
                            break;
                        }
                }
            } else {
                escaped = false;
                out.append(c);
            }
        }
    }

    private void writeParams(StringBuilder out) {
        char prefix = '?';
        int size = this.params.size();
        for (int i = 0; i < size; i++) {
            Param param = this.params.get(i);
            if (!param.isTemplateParam) {
                out.append(prefix);
                prefix = '&';
                out.append(param.key);
                out.append('=');
                String templateKey = param.getValueTemplateKey();
                if (templateKey != null) {
                    Param valParam = getParam(templateKey);
                    if (valParam != null) {
                        valParam.isTemplateParam = true;
                        out.append(urlEncode(valParam.value));
                    } else {
                        out.append(urlEncode(param.value));
                    }
                } else {
                    out.append(urlEncode(param.value));
                }
            }
        }
    }

    private void writeSelfParams(StringBuilder out) {
        if (this.multiGet) {
            StringBuilder sb = new StringBuilder();
            Iterator i$ = this.selfParams.iterator();
            while (i$.hasNext()) {
                String self = i$.next();
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(self);
            }
            if (sb.length() > 0) {
                out.append(this.params.isEmpty() ? '?' : '&').append("self").append(SimpleComparison.EQUAL_TO_OPERATION).append(sb.toString());
            }
        }
    }

    private static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, ACRAConstants.UTF8);
        } catch (UnsupportedEncodingException e) {
            UaLog.error("UrlEncode error", (Throwable) e);
            return value;
        }
    }

    protected static class Param {
        boolean isTemplateParam;
        String key;
        String value;

        private Param(String key2, String value2) {
            this.key = key2;
            this.value = value2;
        }

        /* access modifiers changed from: private */
        public String getValueTemplateKey() {
            int length = this.value.length();
            if (length <= 1 || this.value.charAt(0) != '{' || this.value.charAt(length - 1) != '}') {
                return null;
            }
            int start = 0;
            while (this.value.charAt(start) == '{') {
                start++;
            }
            int end = length;
            while (this.value.charAt(end - 1) == '}') {
                end--;
            }
            return this.value.substring(start, end);
        }

        public String getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }
    }
}
