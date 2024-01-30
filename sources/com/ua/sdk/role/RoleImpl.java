package com.ua.sdk.role;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.Reference;
import com.ua.sdk.role.Role;
import java.util.List;

public class RoleImpl implements Role {
    public static Parcelable.Creator<RoleImpl> CREATOR = new Parcelable.Creator<RoleImpl>() {
        public RoleImpl createFromParcel(Parcel source) {
            return new RoleImpl(source);
        }

        public RoleImpl[] newArray(int size) {
            return new RoleImpl[size];
        }
    };
    @SerializedName("description")
    private String description;
    @SerializedName("name")
    private Role.Type name;
    @SerializedName("permissions")
    private List<Role.Permission> permissions;

    public RoleImpl() {
    }

    private RoleImpl(Parcel in) {
        this.description = in.readString();
        int nameTemp = in.readInt();
        this.name = nameTemp == -1 ? null : Role.Type.values()[nameTemp];
        this.permissions = in.readArrayList(Role.Permission.class.getClassLoader());
    }

    protected RoleImpl(Role.Type name2, List<Role.Permission> permissions2, String description2) {
        this.name = name2;
        this.permissions = permissions2;
        this.description = description2;
    }

    public String getDescription() {
        return this.description;
    }

    public Role.Type getName() {
        return this.name;
    }

    public List<Role.Permission> getPermissions() {
        return this.permissions;
    }

    public void setDescription(String description2) {
        this.description = description2;
    }

    public void setName(Role.Type name2) {
        this.name = name2;
    }

    public void setPermissions(List<Role.Permission> permissions2) {
        this.permissions = permissions2;
    }

    public Reference getRef() {
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeInt(this.name == null ? -1 : this.name.ordinal());
        dest.writeList(this.permissions);
    }
}
