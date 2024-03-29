package dagger.internal;

import dagger.Lazy;
import javax.inject.Provider;

public final class DoubleCheck<T> implements Provider<T>, Lazy<T> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DoubleCheck.class.desiredAssertionStatus());
    private static final Object UNINITIALIZED = new Object();
    private volatile Object instance = UNINITIALIZED;
    private volatile Provider<T> provider;

    private DoubleCheck(Provider<T> provider2) {
        if ($assertionsDisabled || provider2 != null) {
            this.provider = provider2;
            return;
        }
        throw new AssertionError();
    }

    public T get() {
        Object result = this.instance;
        if (result == UNINITIALIZED) {
            synchronized (this) {
                result = this.instance;
                if (result == UNINITIALIZED) {
                    result = this.provider.get();
                    Object currentInstance = this.instance;
                    if (currentInstance == UNINITIALIZED || currentInstance == result) {
                        this.instance = result;
                        this.provider = null;
                    } else {
                        throw new IllegalStateException("Scoped provider was invoked recursively returning different results: " + currentInstance + " & " + result);
                    }
                }
            }
        }
        return result;
    }

    public static <T> Provider<T> provider(Provider<T> delegate) {
        Preconditions.checkNotNull(delegate);
        return delegate instanceof DoubleCheck ? delegate : new DoubleCheck<>(delegate);
    }

    public static <T> Lazy<T> lazy(Provider<T> provider2) {
        if (provider2 instanceof Lazy) {
            return (Lazy) provider2;
        }
        return new DoubleCheck((Provider) Preconditions.checkNotNull(provider2));
    }
}
