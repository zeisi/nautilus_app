package commons.validator.routines;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidator implements Serializable {
    public static final long ALLOW_2_SLASHES = 2;
    public static final long ALLOW_ALL_SCHEMES = 1;
    public static final long ALLOW_LOCAL_URLS = 8;
    private static final String AUTHORITY_CHARS_REGEX = "\\p{Alnum}\\-\\.";
    private static final Pattern AUTHORITY_PATTERN = Pattern.compile(AUTHORITY_REGEX);
    private static final String AUTHORITY_REGEX = "^([\\p{Alnum}\\-\\.]*)(:\\d*)?(.*)?";
    private static final String[] DEFAULT_SCHEMES = {"http", "https", "ftp"};
    private static final UrlValidator DEFAULT_URL_VALIDATOR = new UrlValidator();
    public static final long NO_FRAGMENTS = 4;
    private static final int PARSE_AUTHORITY_EXTRA = 3;
    private static final int PARSE_AUTHORITY_HOST_IP = 1;
    private static final int PARSE_AUTHORITY_PORT = 2;
    private static final int PARSE_URL_AUTHORITY = 4;
    private static final int PARSE_URL_FRAGMENT = 9;
    private static final int PARSE_URL_PATH = 5;
    private static final int PARSE_URL_QUERY = 7;
    private static final int PARSE_URL_SCHEME = 2;
    private static final Pattern PATH_PATTERN = Pattern.compile(PATH_REGEX);
    private static final String PATH_REGEX = "^(/[-\\w:@&?=+,.!/~*'%$_;\\(\\)]*)?$";
    private static final Pattern PORT_PATTERN = Pattern.compile(PORT_REGEX);
    private static final String PORT_REGEX = "^:(\\d{1,5})$";
    private static final Pattern QUERY_PATTERN = Pattern.compile(QUERY_REGEX);
    private static final String QUERY_REGEX = "^(.*)$";
    private static final Pattern SCHEME_PATTERN = Pattern.compile(SCHEME_REGEX);
    private static final String SCHEME_REGEX = "^\\p{Alpha}[\\p{Alnum}\\+\\-\\.]*";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
    private static final String URL_REGEX = "^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?";
    private static final long serialVersionUID = 7557161713937335013L;
    private final Set allowedSchemes;
    private final RegexValidator authorityValidator;
    private final long options;

    public static UrlValidator getInstance() {
        return DEFAULT_URL_VALIDATOR;
    }

    public UrlValidator() {
        this((String[]) null);
    }

    public UrlValidator(String[] schemes) {
        this(schemes, 0);
    }

    public UrlValidator(long options2) {
        this((String[]) null, (RegexValidator) null, options2);
    }

    public UrlValidator(String[] schemes, long options2) {
        this(schemes, (RegexValidator) null, options2);
    }

    public UrlValidator(RegexValidator authorityValidator2, long options2) {
        this((String[]) null, authorityValidator2, options2);
    }

    public UrlValidator(String[] schemes, RegexValidator authorityValidator2, long options2) {
        this.options = options2;
        if (isOn(1)) {
            this.allowedSchemes = Collections.EMPTY_SET;
        } else {
            schemes = schemes == null ? DEFAULT_SCHEMES : schemes;
            this.allowedSchemes = new HashSet(schemes.length);
            for (String lowerCase : schemes) {
                this.allowedSchemes.add(lowerCase.toLowerCase(Locale.ENGLISH));
            }
        }
        this.authorityValidator = authorityValidator2;
    }

    public boolean isValid(String value) {
        if (value == null) {
            return false;
        }
        Matcher urlMatcher = URL_PATTERN.matcher(value);
        if (!urlMatcher.matches()) {
            return false;
        }
        String scheme = urlMatcher.group(2);
        if (!isValidScheme(scheme)) {
            return false;
        }
        String authority = urlMatcher.group(4);
        if (((!"file".equals(scheme) || !"".equals(authority)) && !isValidAuthority(authority)) || !isValidPath(urlMatcher.group(5)) || !isValidQuery(urlMatcher.group(7)) || !isValidFragment(urlMatcher.group(9))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isValidScheme(String scheme) {
        if (scheme == null || !SCHEME_PATTERN.matcher(scheme).matches()) {
            return false;
        }
        if (!isOff(1) || this.allowedSchemes.contains(scheme.toLowerCase(Locale.ENGLISH))) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isValidAuthority(String authority) {
        if (authority == null) {
            return false;
        }
        if (this.authorityValidator != null && this.authorityValidator.isValid(authority)) {
            return true;
        }
        Matcher authorityMatcher = AUTHORITY_PATTERN.matcher(DomainValidator.unicodeToASCII(authority));
        if (!authorityMatcher.matches()) {
            return false;
        }
        String hostLocation = authorityMatcher.group(1);
        if (!DomainValidator.getInstance(isOn(8)).isValid(hostLocation) && !InetAddressValidator.getInstance().isValid(hostLocation)) {
            return false;
        }
        String port = authorityMatcher.group(2);
        if (port != null && !PORT_PATTERN.matcher(port).matches()) {
            return false;
        }
        String extra = authorityMatcher.group(3);
        if (extra == null || extra.trim().length() <= 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isValidPath(String path) {
        if (path == null || !PATH_PATTERN.matcher(path).matches()) {
            return false;
        }
        int slash2Count = countToken("//", path);
        if (isOff(2) && slash2Count > 0) {
            return false;
        }
        int slashCount = countToken("/", path);
        int dot2Count = countToken("..", path);
        if (dot2Count <= 0 || (slashCount - slash2Count) - 1 > dot2Count) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isValidQuery(String query) {
        if (query == null) {
            return true;
        }
        return QUERY_PATTERN.matcher(query).matches();
    }

    /* access modifiers changed from: protected */
    public boolean isValidFragment(String fragment) {
        if (fragment == null) {
            return true;
        }
        return isOff(4);
    }

    /* access modifiers changed from: protected */
    public int countToken(String token, String target) {
        int tokenIndex = 0;
        int count = 0;
        while (tokenIndex != -1) {
            tokenIndex = target.indexOf(token, tokenIndex);
            if (tokenIndex > -1) {
                tokenIndex++;
                count++;
            }
        }
        return count;
    }

    private boolean isOn(long flag) {
        return (this.options & flag) > 0;
    }

    private boolean isOff(long flag) {
        return (this.options & flag) == 0;
    }
}
