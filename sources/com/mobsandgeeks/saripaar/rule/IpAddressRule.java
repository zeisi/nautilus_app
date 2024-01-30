package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.IpAddress;
import commons.validator.routines.InetAddressValidator;

public class IpAddressRule extends AnnotationRule<IpAddress, String> {
    protected IpAddressRule(IpAddress ipAddress) {
        super(ipAddress);
    }

    public boolean isValid(String ipAddress) {
        return InetAddressValidator.getInstance().isValid(ipAddress);
    }
}
