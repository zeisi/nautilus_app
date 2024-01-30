package com.ua.sdk.role;

import com.ua.sdk.Resource;
import java.util.List;

public interface Role extends Resource {

    public enum Permission {
        EDIT,
        CREATE,
        MODERATE,
        ADMINISTER
    }

    String getDescription();

    Type getName();

    List<Permission> getPermissions();

    void setDescription(String str);

    void setName(Type type);

    void setPermissions(List<Permission> list);

    public enum Type {
        CREATOR("CREATOR"),
        MODERATOR("MODERATOR"),
        EDITOR("EDITOR"),
        ADMINISTRATOR("ADMINISTRATOR");
        
        String value;

        private Type(String value2) {
            this.value = value2;
        }

        public String getValue() {
            return this.value;
        }

        public static Type parse(String value2) {
            for (Type type : values()) {
                if (type.value.equals(value2)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Value passed is not known!");
        }
    }
}
