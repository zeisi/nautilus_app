package org.acra.collections;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.acra.collections.ImmutableSet;

public final class ImmutableMap<K, V> implements Map<K, V>, Serializable {
    private final Map<K, V> mMap;

    public ImmutableMap(Map<K, V> map) {
        this.mMap = new HashMap(map);
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean containsKey(Object key) {
        return this.mMap.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.mMap.containsValue(value);
    }

    @NonNull
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> original = this.mMap.entrySet();
        ImmutableSet.Builder<Map.Entry<K, V>> builder = new ImmutableSet.Builder<>();
        for (Map.Entry<K, V> entry : original) {
            builder.add(new ImmutableEntryWrapper(entry));
        }
        return builder.build();
    }

    public V get(Object key) {
        return this.mMap.get(key);
    }

    public boolean isEmpty() {
        return this.mMap.isEmpty();
    }

    @NonNull
    public Set<K> keySet() {
        return new ImmutableSet(this.mMap.keySet());
    }

    public V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    public void putAll(@NonNull Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    public V remove(Object object) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return this.mMap.size();
    }

    @NonNull
    public Collection<V> values() {
        return new ImmutableList(this.mMap.values());
    }

    public static class ImmutableEntryWrapper<K, V> implements Map.Entry<K, V> {
        private final Map.Entry<K, V> mEntry;

        ImmutableEntryWrapper(Map.Entry<K, V> mEntry2) {
            this.mEntry = mEntry2;
        }

        public K getKey() {
            return this.mEntry.getKey();
        }

        public V getValue() {
            return this.mEntry.getValue();
        }

        public V setValue(Object object) {
            throw new UnsupportedOperationException();
        }
    }
}
