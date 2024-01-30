package commons.validator.routines;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValidator implements Serializable {
    private static final long serialVersionUID = -8832409930574867162L;
    private final Pattern[] patterns;

    public RegexValidator(String regex) {
        this(regex, true);
    }

    public RegexValidator(String regex, boolean caseSensitive) {
        this(new String[]{regex}, caseSensitive);
    }

    public RegexValidator(String[] regexs) {
        this(regexs, true);
    }

    public RegexValidator(String[] regexs, boolean caseSensitive) {
        if (regexs == null || regexs.length == 0) {
            throw new IllegalArgumentException("Regular expressions are missing");
        }
        this.patterns = new Pattern[regexs.length];
        int flags = caseSensitive ? 0 : 2;
        for (int i = 0; i < regexs.length; i++) {
            if (regexs[i] == null || regexs[i].length() == 0) {
                throw new IllegalArgumentException("Regular expression[" + i + "] is missing");
            }
            this.patterns[i] = Pattern.compile(regexs[i], flags);
        }
    }

    public boolean isValid(String value) {
        if (value == null) {
            return false;
        }
        for (Pattern matcher : this.patterns) {
            if (matcher.matcher(value).matches()) {
                return true;
            }
        }
        return false;
    }

    public String[] match(String value) {
        String[] groups = null;
        if (value != null) {
            int i = 0;
            while (true) {
                if (i >= this.patterns.length) {
                    break;
                }
                Matcher matcher = this.patterns[i].matcher(value);
                if (matcher.matches()) {
                    int count = matcher.groupCount();
                    groups = new String[count];
                    for (int j = 0; j < count; j++) {
                        groups[j] = matcher.group(j + 1);
                    }
                } else {
                    i++;
                }
            }
        }
        return groups;
    }

    public String validate(String value) {
        if (value == null) {
            return null;
        }
        for (Pattern matcher : this.patterns) {
            Matcher matcher2 = matcher.matcher(value);
            if (matcher2.matches()) {
                int count = matcher2.groupCount();
                if (count == 1) {
                    return matcher2.group(1);
                }
                StringBuffer buffer = new StringBuffer();
                for (int j = 0; j < count; j++) {
                    String component = matcher2.group(j + 1);
                    if (component != null) {
                        buffer.append(component);
                    }
                }
                return buffer.toString();
            }
        }
        return null;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("RegexValidator{");
        for (int i = 0; i < this.patterns.length; i++) {
            if (i > 0) {
                buffer.append(",");
            }
            buffer.append(this.patterns[i].pattern());
        }
        buffer.append("}");
        return buffer.toString();
    }
}
