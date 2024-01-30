package com.mobsandgeeks.saripaar;

import com.mobsandgeeks.saripaar.annotation.Order;
import java.lang.reflect.Field;
import java.util.Comparator;

final class SaripaarFieldsComparator implements Comparator<Field> {
    private boolean mOrderedFields = true;

    SaripaarFieldsComparator() {
    }

    public int compare(Field lhsField, Field rhsField) {
        Order lhsOrderAnnotation = (Order) lhsField.getAnnotation(Order.class);
        Order rhsOrderAnnotation = (Order) rhsField.getAnnotation(Order.class);
        if (lhsOrderAnnotation == null || rhsOrderAnnotation == null) {
            this.mOrderedFields = false;
            return 0;
        }
        int lhsOrder = lhsOrderAnnotation.value();
        int rhsOrder = rhsOrderAnnotation.value();
        if (lhsOrder == rhsOrder) {
            return 0;
        }
        return lhsOrder > rhsOrder ? 1 : -1;
    }

    public boolean areOrderedFields() {
        return this.mOrderedFields;
    }
}
