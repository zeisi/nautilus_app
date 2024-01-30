package commons.validator.routines;

import com.nautilus.omni.util.Constants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InetAddressValidator implements Serializable {
    private static final String IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";
    private static final InetAddressValidator VALIDATOR = new InetAddressValidator();
    private static final long serialVersionUID = -919201640201914789L;
    private final RegexValidator ipv4Validator = new RegexValidator(IPV4_REGEX);

    public static InetAddressValidator getInstance() {
        return VALIDATOR;
    }

    public boolean isValid(String inetAddress) {
        return isValidInet4Address(inetAddress) || isValidInet6Address(inetAddress);
    }

    public boolean isValidInet4Address(String inet4Address) {
        String[] groups = this.ipv4Validator.match(inet4Address);
        if (groups == null) {
            return false;
        }
        int i = 0;
        while (i <= 3) {
            String ipSegment = groups[i];
            if (ipSegment == null || ipSegment.length() == 0) {
                return false;
            }
            try {
                if (Integer.parseInt(ipSegment) > 255) {
                    return false;
                }
                if (ipSegment.length() > 1 && ipSegment.startsWith("0")) {
                    return false;
                }
                i++;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidInet6Address(String inet6Address) {
        boolean containsCompressedZeroes = inet6Address.indexOf("::") > -1;
        if (containsCompressedZeroes && inet6Address.indexOf("::") != inet6Address.lastIndexOf("::")) {
            return false;
        }
        if ((inet6Address.startsWith(Constants.COLON_SEPARATOR) && !inet6Address.startsWith("::")) || (inet6Address.endsWith(Constants.COLON_SEPARATOR) && !inet6Address.endsWith("::"))) {
            return false;
        }
        Object[] octets = inet6Address.split(Constants.COLON_SEPARATOR);
        if (containsCompressedZeroes) {
            List octetList = new ArrayList(Arrays.asList(octets));
            if (inet6Address.endsWith("::")) {
                octetList.add("");
            } else if (inet6Address.startsWith("::") && !octetList.isEmpty()) {
                octetList.remove(0);
            }
            octets = octetList.toArray();
        }
        if (octets.length > 8) {
            return false;
        }
        int validOctets = 0;
        int emptyOctets = 0;
        for (int index = 0; index < octets.length; index++) {
            String octet = (String) octets[index];
            if (octet.length() == 0) {
                emptyOctets++;
                if (emptyOctets > 1) {
                    return false;
                }
                validOctets++;
            } else {
                emptyOctets = 0;
                if (octet.indexOf(".") > -1) {
                    if (!inet6Address.endsWith(octet) || index > octets.length - 1 || index > 6 || !isValidInet4Address(octet)) {
                        return false;
                    }
                    validOctets += 2;
                } else if (octet.length() > 4) {
                    return false;
                } else {
                    try {
                        int octetInt = Integer.valueOf(octet, 16).intValue();
                        if (octetInt < 0 || octetInt > 65535) {
                            return false;
                        }
                        validOctets++;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }
        }
        if (validOctets >= 8 || containsCompressedZeroes) {
            return true;
        }
        return false;
    }
}
