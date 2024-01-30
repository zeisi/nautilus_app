package org.acra.collections;

import java.util.Iterator;

class UnmodifiableIteratorWrapper<E> implements Iterator<E> {
    private final Iterator<E> mIterator;

    UnmodifiableIteratorWrapper(Iterator<E> mIterator2) {
        this.mIterator = mIterator2;
    }

    public boolean hasNext() {
        return this.mIterator.hasNext();
    }

    public E next() {
        return this.mIterator.next();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
