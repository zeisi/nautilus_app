package com.ua.oss.org.apache.http.entity.mime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Header implements Iterable<MinimalField> {
    private final Map<String, List<MinimalField>> fieldMap = new HashMap();
    private final List<MinimalField> fields = new LinkedList();

    public void addField(MinimalField field) {
        if (field != null) {
            String key = field.getName().toLowerCase(Locale.ENGLISH);
            List<MinimalField> values = this.fieldMap.get(key);
            if (values == null) {
                values = new LinkedList<>();
                this.fieldMap.put(key, values);
            }
            values.add(field);
            this.fields.add(field);
        }
    }

    public List<MinimalField> getFields() {
        return new ArrayList(this.fields);
    }

    public MinimalField getField(String name) {
        List<MinimalField> list;
        if (name == null || (list = this.fieldMap.get(name.toLowerCase(Locale.ENGLISH))) == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<MinimalField> getFields(String name) {
        if (name == null) {
            return null;
        }
        List<MinimalField> list = this.fieldMap.get(name.toLowerCase(Locale.ENGLISH));
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList(list);
    }

    public void setField(MinimalField field) {
        if (field != null) {
            List<MinimalField> list = this.fieldMap.get(field.getName().toLowerCase(Locale.ENGLISH));
            if (list == null || list.isEmpty()) {
                addField(field);
                return;
            }
            list.clear();
            list.add(field);
            int firstOccurrence = -1;
            int index = 0;
            Iterator<MinimalField> it = this.fields.iterator();
            while (it.hasNext()) {
                if (it.next().getName().equalsIgnoreCase(field.getName())) {
                    it.remove();
                    if (firstOccurrence == -1) {
                        firstOccurrence = index;
                    }
                }
                index++;
            }
            this.fields.add(firstOccurrence, field);
        }
    }

    public Iterator<MinimalField> iterator() {
        return Collections.unmodifiableList(this.fields).iterator();
    }

    public String toString() {
        return this.fields.toString();
    }
}
