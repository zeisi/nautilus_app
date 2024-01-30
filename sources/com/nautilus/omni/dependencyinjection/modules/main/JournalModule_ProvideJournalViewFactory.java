package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.journal.view.JournalFragment;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class JournalModule_ProvideJournalViewFactory implements Factory<JournalFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!JournalModule_ProvideJournalViewFactory.class.desiredAssertionStatus());
    private final JournalModule module;

    public JournalModule_ProvideJournalViewFactory(JournalModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public JournalFragment get() {
        return (JournalFragment) Preconditions.checkNotNull(this.module.provideJournalView(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<JournalFragment> create(JournalModule module2) {
        return new JournalModule_ProvideJournalViewFactory(module2);
    }

    public static JournalFragment proxyProvideJournalView(JournalModule instance) {
        return instance.provideJournalView();
    }
}
