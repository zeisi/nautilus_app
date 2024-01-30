package org.acra.collections;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public final class ImmutableList<E> implements List<E>, Serializable {
    private final List<E> mList;

    public ImmutableList(E... elements) {
        this(Arrays.asList(elements));
    }

    public ImmutableList(Collection<E> collection) {
        this.mList = new ArrayList(collection);
    }

    public void add(int location, E e) {
        throw new UnsupportedOperationException();
    }

    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int location, @NonNull Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(@NonNull Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object object) {
        return this.mList.contains(object);
    }

    public boolean containsAll(@NonNull Collection<?> collection) {
        return this.mList.containsAll(collection);
    }

    public E get(int location) {
        return this.mList.get(location);
    }

    public int indexOf(Object object) {
        return this.mList.indexOf(object);
    }

    public boolean isEmpty() {
        return this.mList.isEmpty();
    }

    @NonNull
    public Iterator<E> iterator() {
        return new UnmodifiableIteratorWrapper(this.mList.iterator());
    }

    public int lastIndexOf(Object object) {
        return this.mList.lastIndexOf(object);
    }

    public ListIterator<E> listIterator() {
        return new UnmodifiableListIteratorWrapper(this.mList.listIterator());
    }

    @NonNull
    public ListIterator<E> listIterator(int location) {
        return new UnmodifiableListIteratorWrapper(this.mList.listIterator(location));
    }

    public E remove(int location) {
        throw new UnsupportedOperationException();
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

    public E set(int location, E e) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return this.mList.size();
    }

    @NonNull
    public List<E> subList(int start, int end) {
        throw new UnsupportedOperationException();
    }

    @NonNull
    public Object[] toArray() {
        return this.mList.toArray();
    }

    @NonNull
    public <T> T[] toArray(@NonNull T[] array) {
        return this.mList.toArray(array);
    }
}
