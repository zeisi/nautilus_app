package org.acra.collections;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public final class ImmutableSet<E> implements Set<E>, Serializable {
    private final Set<E> mSet;

    public ImmutableSet(E... elements) {
        this(Arrays.asList(elements));
    }

    public ImmutableSet(Collection<E> collection) {
        this.mSet = new LinkedHashSet(collection);
    }

    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(@NonNull Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object object) {
        return this.mSet.contains(object);
    }

    public boolean containsAll(@NonNull Collection<?> collection) {
        return this.mSet.containsAll(collection);
    }

    public boolean isEmpty() {
        return this.mSet.isEmpty();
    }

    @NonNull
    public Iterator<E> iterator() {
        return new UnmodifiableIteratorWrapper(this.mSet.iterator());
    }

    public boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(@NonNull Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(@NonNull Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return this.mSet.size();
    }

    @NonNull
    public Object[] toArray() {
        return this.mSet.toArray();
    }

    @NonNull
    public <T> T[] toArray(@NonNull T[] array) {
        return this.mSet.toArray(array);
    }

    public static final class Builder<E> {
        private final Set<E> mSet = new LinkedHashSet();

        public void add(E element) {
            this.mSet.add(element);
        }

        public ImmutableSet<E> build() {
            return new ImmutableSet<>(this.mSet);
        }
    }
}
