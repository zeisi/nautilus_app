package dagger.internal;

import dagger.releasablereferences.ReleasableReferenceManager;
import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@GwtIncompatible
public final class ReferenceReleasingProviderManager implements ReleasableReferenceManager {
    private final Queue<WeakReference<ReferenceReleasingProvider<?>>> providers = new ConcurrentLinkedQueue();
    private final Class<? extends Annotation> scope;

    private enum Operation {
        RELEASE {
            /* access modifiers changed from: package-private */
            public void execute(ReferenceReleasingProvider<?> provider) {
                provider.releaseStrongReference();
            }
        },
        RESTORE {
            /* access modifiers changed from: package-private */
            public void execute(ReferenceReleasingProvider<?> provider) {
                provider.restoreStrongReference();
            }
        };

        /* access modifiers changed from: package-private */
        public abstract void execute(ReferenceReleasingProvider<?> referenceReleasingProvider);
    }

    public ReferenceReleasingProviderManager(Class<? extends Annotation> scope2) {
        this.scope = (Class) Preconditions.checkNotNull(scope2);
    }

    public void addProvider(ReferenceReleasingProvider<?> provider) {
        this.providers.add(new WeakReference(provider));
    }

    public Class<? extends Annotation> scope() {
        return this.scope;
    }

    public void releaseStrongReferences() {
        execute(Operation.RELEASE);
    }

    public void restoreStrongReferences() {
        execute(Operation.RESTORE);
    }

    private void execute(Operation operation) {
        Iterator<WeakReference<ReferenceReleasingProvider<?>>> iterator = this.providers.iterator();
        while (iterator.hasNext()) {
            ReferenceReleasingProvider<?> provider = (ReferenceReleasingProvider) iterator.next().get();
            if (provider == null) {
                iterator.remove();
            } else {
                operation.execute(provider);
            }
        }
    }
}
