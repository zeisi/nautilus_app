package commons.validator.routines;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements Serializable {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String EMAIL_REGEX = "^\\s*?(.+)@(.+?)\\s*$";
    private static final EmailValidator EMAIL_VALIDATOR = new EmailValidator(false);
    private static final EmailValidator EMAIL_VALIDATOR_WITH_LOCAL = new EmailValidator(true);
    private static final Pattern IP_DOMAIN_PATTERN = Pattern.compile(IP_DOMAIN_REGEX);
    private static final String IP_DOMAIN_REGEX = "^\\[(.*)\\]$";
    private static final String QUOTED_USER = "(\"[^\"]*\")";
    private static final String SPECIAL_CHARS = "\\p{Cntrl}\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]";
    private static final Pattern USER_PATTERN = Pattern.compile(USER_REGEX);
    private static final String USER_REGEX = "^\\s*(([^\\s\\p{Cntrl}\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]]|')+|(\"[^\"]*\"))(\\.(([^\\s\\p{Cntrl}\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]]|')+|(\"[^\"]*\")))*$";
    private static final String VALID_CHARS = "[^\\s\\p{Cntrl}\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]]";
    private static final String WORD = "(([^\\s\\p{Cntrl}\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]]|')+|(\"[^\"]*\"))";
    private static final long serialVersionUID = 1705927040799295880L;
    private final boolean allowLocal;

    public static EmailValidator getInstance() {
        return EMAIL_VALIDATOR;
    }

    public static EmailValidator getInstance(boolean allowLocal2) {
        if (allowLocal2) {
            return EMAIL_VALIDATOR_WITH_LOCAL;
        }
        return EMAIL_VALIDATOR;
    }

    protected EmailValidator(boolean allowLocal2) {
        this.allowLocal = allowLocal2;
    }

    public boolean isValid(String email) {
        if (email == null || email.endsWith(".")) {
            return false;
        }
        Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
        if (!emailMatcher.matches() || !isValidUser(emailMatcher.group(1)) || !isValidDomain(emailMatcher.group(2))) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isValidDomain(String domain) {
        Matcher ipDomainMatcher = IP_DOMAIN_PATTERN.matcher(domain);
        if (ipDomainMatcher.matches()) {
            return InetAddressValidator.getInstance().isValid(ipDomainMatcher.group(1));
        }
        DomainValidator domainValidator = DomainValidator.getInstance(this.allowLocal);
        if (domainValidator.isValid(domain) || domainValidator.isValidTld(domain)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isValidUser(String user) {
        return USER_PATTERN.matcher(user).matches();
    }
}
