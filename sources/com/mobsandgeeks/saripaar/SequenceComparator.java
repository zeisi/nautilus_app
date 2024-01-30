package com.mobsandgeeks.saripaar;

import android.util.Pair;
import com.mobsandgeeks.saripaar.adapter.ViewDataAdapter;
import java.util.Comparator;

final class SequenceComparator implements Comparator<Pair<Rule, ViewDataAdapter>> {
    SequenceComparator() {
    }

    public int compare(Pair<Rule, ViewDataAdapter> lhsPair, Pair<Rule, ViewDataAdapter> rhsPair) {
        int lhsSequence = ((Rule) lhsPair.first).getSequence();
        int rhsSequence = ((Rule) rhsPair.first).getSequence();
        if (lhsSequence == rhsSequence) {
            return 0;
        }
        return lhsSequence > rhsSequence ? 1 : -1;
    }
}
