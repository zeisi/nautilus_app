package uk.co.chrisjenx.calligraphy;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;

class CalligraphyLayoutInflater extends LayoutInflater implements CalligraphyActivityFactory {
    private static final String[] sClassPrefixList = {"android.widget.", "android.webkit."};
    private final int mAttributeId;
    private final CalligraphyFactory mCalligraphyFactory;
    private Field mConstructorArgs = null;
    private boolean mSetPrivateFactory = false;

    protected CalligraphyLayoutInflater(Context context, int attributeId) {
        super(context);
        this.mAttributeId = attributeId;
        this.mCalligraphyFactory = new CalligraphyFactory(attributeId);
        setUpLayoutFactories(false);
    }

    protected CalligraphyLayoutInflater(LayoutInflater original, Context newContext, int attributeId, boolean cloned) {
        super(original, newContext);
        this.mAttributeId = attributeId;
        this.mCalligraphyFactory = new CalligraphyFactory(attributeId);
        setUpLayoutFactories(cloned);
    }

    public LayoutInflater cloneInContext(Context newContext) {
        return new CalligraphyLayoutInflater(this, newContext, this.mAttributeId, true);
    }

    public View inflate(XmlPullParser parser, ViewGroup root, boolean attachToRoot) {
        setPrivateFactoryInternal();
        return super.inflate(parser, root, attachToRoot);
    }

    private void setUpLayoutFactories(boolean cloned) {
        if (!cloned) {
            if (Build.VERSION.SDK_INT >= 11 && getFactory2() != null && !(getFactory2() instanceof WrapperFactory2)) {
                setFactory2(getFactory2());
            }
            if (getFactory() != null && !(getFactory() instanceof WrapperFactory)) {
                setFactory(getFactory());
            }
        }
    }

    public void setFactory(LayoutInflater.Factory factory) {
        if (!(factory instanceof WrapperFactory)) {
            super.setFactory(new WrapperFactory(factory, this, this.mCalligraphyFactory));
        } else {
            super.setFactory(factory);
        }
    }

    @TargetApi(11)
    public void setFactory2(LayoutInflater.Factory2 factory2) {
        if (!(factory2 instanceof WrapperFactory2)) {
            super.setFactory2(new WrapperFactory2(factory2, this.mCalligraphyFactory));
        } else {
            super.setFactory2(factory2);
        }
    }

    private void setPrivateFactoryInternal() {
        if (this.mSetPrivateFactory || !CalligraphyConfig.get().isReflection()) {
            return;
        }
        if (!(getContext() instanceof LayoutInflater.Factory2)) {
            this.mSetPrivateFactory = true;
            return;
        }
        Method setPrivateFactoryMethod = ReflectionUtils.getMethod(LayoutInflater.class, "setPrivateFactory");
        if (setPrivateFactoryMethod != null) {
            ReflectionUtils.invokeMethod(this, setPrivateFactoryMethod, new PrivateWrapperFactory2((LayoutInflater.Factory2) getContext(), this, this.mCalligraphyFactory));
        }
        this.mSetPrivateFactory = true;
    }

    @TargetApi(11)
    public View onActivityCreateView(View parent, View view, String name, Context context, AttributeSet attrs) {
        return this.mCalligraphyFactory.onViewCreated(createCustomViewInternal(parent, view, name, context, attrs), context, attrs);
    }

    /* access modifiers changed from: protected */
    @TargetApi(11)
    public View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        return this.mCalligraphyFactory.onViewCreated(super.onCreateView(parent, name, attrs), getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        View view = null;
        for (String prefix : sClassPrefixList) {
            try {
                view = createView(name, prefix, attrs);
            } catch (ClassNotFoundException e) {
            }
        }
        if (view == null) {
            view = super.onCreateView(name, attrs);
        }
        return this.mCalligraphyFactory.onViewCreated(view, view.getContext(), attrs);
    }

    /* access modifiers changed from: private */
    public View createCustomViewInternal(View parent, View view, String name, Context viewContext, AttributeSet attrs) {
        if (!CalligraphyConfig.get().isCustomViewCreation()) {
            return view;
        }
        if (view == null && name.indexOf(46) > -1) {
            if (this.mConstructorArgs == null) {
                this.mConstructorArgs = ReflectionUtils.getField(LayoutInflater.class, "mConstructorArgs");
            }
            Object[] mConstructorArgsArr = (Object[]) ReflectionUtils.getValue(this.mConstructorArgs, this);
            Object lastContext = mConstructorArgsArr[0];
            mConstructorArgsArr[0] = viewContext;
            ReflectionUtils.setValue(this.mConstructorArgs, this, mConstructorArgsArr);
            try {
                view = createView(name, (String) null, attrs);
            } catch (ClassNotFoundException e) {
            } finally {
                mConstructorArgsArr[0] = lastContext;
                ReflectionUtils.setValue(this.mConstructorArgs, this, mConstructorArgsArr);
            }
        }
        return view;
    }

    private static class WrapperFactory implements LayoutInflater.Factory {
        private final CalligraphyFactory mCalligraphyFactory;
        private final LayoutInflater.Factory mFactory;
        private final CalligraphyLayoutInflater mInflater;

        public WrapperFactory(LayoutInflater.Factory factory, CalligraphyLayoutInflater inflater, CalligraphyFactory calligraphyFactory) {
            this.mFactory = factory;
            this.mInflater = inflater;
            this.mCalligraphyFactory = calligraphyFactory;
        }

        public View onCreateView(String name, Context context, AttributeSet attrs) {
            if (Build.VERSION.SDK_INT < 11) {
                return this.mCalligraphyFactory.onViewCreated(this.mInflater.createCustomViewInternal((View) null, this.mFactory.onCreateView(name, context, attrs), name, context, attrs), context, attrs);
            }
            return this.mCalligraphyFactory.onViewCreated(this.mFactory.onCreateView(name, context, attrs), context, attrs);
        }
    }

    @TargetApi(11)
    private static class WrapperFactory2 implements LayoutInflater.Factory2 {
        protected final CalligraphyFactory mCalligraphyFactory;
        protected final LayoutInflater.Factory2 mFactory2;

        public WrapperFactory2(LayoutInflater.Factory2 factory2, CalligraphyFactory calligraphyFactory) {
            this.mFactory2 = factory2;
            this.mCalligraphyFactory = calligraphyFactory;
        }

        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return this.mCalligraphyFactory.onViewCreated(this.mFactory2.onCreateView(name, context, attrs), context, attrs);
        }

        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            return this.mCalligraphyFactory.onViewCreated(this.mFactory2.onCreateView(parent, name, context, attrs), context, attrs);
        }
    }

    @TargetApi(11)
    private static class PrivateWrapperFactory2 extends WrapperFactory2 {
        private final CalligraphyLayoutInflater mInflater;

        public PrivateWrapperFactory2(LayoutInflater.Factory2 factory2, CalligraphyLayoutInflater inflater, CalligraphyFactory calligraphyFactory) {
            super(factory2, calligraphyFactory);
            this.mInflater = inflater;
        }

        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            return this.mCalligraphyFactory.onViewCreated(this.mInflater.createCustomViewInternal(parent, this.mFactory2.onCreateView(parent, name, context, attrs), name, context, attrs), context, attrs);
        }
    }
}
