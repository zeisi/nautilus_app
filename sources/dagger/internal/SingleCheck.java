package dagger.internal;

import dagger.Lazy;
import javax.inject.Provider;

public final class SingleCheck<T> implements Provider<T>, Lazy<T> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SingleCheck.class.desiredAssertionStatus());
    private static final Object UNINITIALIZED = new Object();
    private volatile Object instance = UNINITIALIZED;
    private volatile Provider<T> provider;

    private SingleCheck(Provider<T> provider2) {
        if ($assertionsDisabled || provider2 != null) {
            this.provider = provider2;
            return;
        }
        throw new AssertionError();
    }

    public T get() {
        Provider<T> providerReference = this.provider;
        if (this.instance == UNINITIALIZED) {
            this.instance = providerReference.get();
            this.provider = null;
        }
        return this.instance;
    }

    public static <T> Provider<T> provider(Provider<T> provider2) {
        return ((provider2 instanceof SingleCheck) || (provider2 instanceof DoubleCheck)) ? provider2 : new SingleCheck<>((Provider) Preconditions.checkNotNull(provider2));
    }
}
