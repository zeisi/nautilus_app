package com.ua.sdk.gear;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.EntityRef;
import com.ua.sdk.internal.ApiTransferObject;
import com.ua.sdk.internal.Link;
import com.ua.sdk.internal.LinkEntityRef;

public class GearImpl extends ApiTransferObject implements Gear {
    public static final Parcelable.Creator<GearImpl> CREATOR = new Parcelable.Creator<GearImpl>() {
        public GearImpl createFromParcel(Parcel source) {
            return new GearImpl(source);
        }

        public GearImpl[] newArray(int size) {
            return new GearImpl[size];
        }
    };
    public static final String REF_SELF = "self";
    @SerializedName("age_group")
    String ageGroup;
    @SerializedName("available")
    Boolean available;
    @SerializedName("brand")
    String brand;
    @SerializedName("category")
    String category;
    @SerializedName("color")
    String color;
    @SerializedName("department")
    String department;
    @SerializedName("description")
    String description;
    @SerializedName("detail_photo_url")
    String detailPhotoUrl;
    @SerializedName("gender")
    String gender;
    @SerializedName("keywords")
    String keywords;
    @SerializedName("mid_level_product_type")
    String midLevelProductType;
    @SerializedName("model")
    String model;
    @SerializedName("msrp")
    String msrp;
    @SerializedName("photo_url")
    String photoUrl;
    @SerializedName("price")
    String price;
    @SerializedName("product_type")
    String productType;
    @SerializedName("product_url")
    String productUrl;
    @SerializedName("purchase_url")
    String purchaseUrl;
    @SerializedName("size")
    String size;
    @SerializedName("sku")
    String sku;
    @SerializedName("source")
    String source;
    @SerializedName("styleid")
    String styleId;
    @SerializedName("style_number")
    String styleNumber;
    @SerializedName("thumbnail_url")
    String thumbnailUrl;
    @SerializedName("upc")
    Long upc;

    public GearImpl() {
    }

    public String getStyleNumber() {
        return this.styleNumber;
    }

    public String getColor() {
        return this.color;
    }

    public String getProductUrl() {
        return this.productUrl;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public String getAgeGroup() {
        return this.ageGroup;
    }

    public String getSize() {
        return this.size;
    }

    public String getSku() {
        return this.sku;
    }

    public String getSource() {
        return this.source;
    }

    public String getDepartment() {
        return this.department;
    }

    public String getBrand() {
        return this.brand;
    }

    public Boolean getAvailable() {
        return this.available;
    }

    public String getCategory() {
        return this.category;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPrice() {
        return this.price;
    }

    public String getPurchaseUrl() {
        return this.purchaseUrl;
    }

    public String getMidLevelProductType() {
        return this.midLevelProductType;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public String getDetailPhotoUrl() {
        return this.detailPhotoUrl;
    }

    public String getProductType() {
        return this.productType;
    }

    public String getGender() {
        return this.gender;
    }

    public Long getUpc() {
        return this.upc;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public String getStyleId() {
        return this.styleId;
    }

    public String getModel() {
        return this.model;
    }

    public String getMsrp() {
        return this.msrp;
    }

    public EntityRef getRef() {
        Link link = getLink("self");
        if (link == null) {
            return null;
        }
        return new LinkEntityRef(link.getId(), link.getHref());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.styleNumber);
        dest.writeString(this.color);
        dest.writeString(this.productUrl);
        dest.writeString(this.keywords);
        dest.writeString(this.ageGroup);
        dest.writeString(this.size);
        dest.writeString(this.sku);
        dest.writeString(this.source);
        dest.writeString(this.department);
        dest.writeString(this.brand);
        dest.writeValue(this.available);
        dest.writeString(this.category);
        dest.writeString(this.description);
        dest.writeString(this.price);
        dest.writeString(this.purchaseUrl);
        dest.writeString(this.midLevelProductType);
        dest.writeString(this.photoUrl);
        dest.writeString(this.detailPhotoUrl);
        dest.writeString(this.productType);
        dest.writeString(this.gender);
        dest.writeValue(this.upc);
        dest.writeString(this.thumbnailUrl);
        dest.writeString(this.styleId);
        dest.writeString(this.model);
        dest.writeString(this.msrp);
    }

    private GearImpl(Parcel in) {
        super(in);
        this.styleNumber = in.readString();
        this.color = in.readString();
        this.productUrl = in.readString();
        this.keywords = in.readString();
        this.ageGroup = in.readString();
        this.size = in.readString();
        this.sku = in.readString();
        this.source = in.readString();
        this.department = in.readString();
        this.brand = in.readString();
        this.available = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.category = in.readString();
        this.description = in.readString();
        this.price = in.readString();
        this.purchaseUrl = in.readString();
        this.midLevelProductType = in.readString();
        this.photoUrl = in.readString();
        this.detailPhotoUrl = in.readString();
        this.productType = in.readString();
        this.gender = in.readString();
        this.upc = (Long) in.readValue(Long.class.getClassLoader());
        this.thumbnailUrl = in.readString();
        this.styleId = in.readString();
        this.model = in.readString();
        this.msrp = in.readString();
    }
}
