package com.nautilus.omni.model.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Collection;

@DatabaseTable(tableName = "award_type")
public class AwardType {
    public static final String AWARD = "award";
    public static final String BIG_IMAGE = "big_image";
    public static final String CIRCLE = "circle";
    public static final String COLOR = "color";
    public static final String DESCRIPTION = "description";
    public static final String HOME_IMAGE = "home_image";
    public static final String ID_FIELD_NAME = "id";
    public static final String MEDIUM_IMAGE = "medium_image";
    public static final String NAME = "name";
    public static final String SMALL_IMAGE = "small_image";
    @ForeignCollectionField(columnName = "award", eager = false, maxEagerLevel = 3)
    public Collection<Award> mAwards;
    @DatabaseField(columnName = "big_image")
    private int mBigImage;
    @DatabaseField(columnName = "circle")
    private int mCircle;
    @DatabaseField(columnName = "color")
    private int mColor;
    @DatabaseField(columnName = "description")
    private String mDescription;
    @DatabaseField(columnName = "home_image")
    private int mHomeImage;
    @DatabaseField(columnName = "id", generatedId = true)
    private int mId;
    @DatabaseField(columnName = "medium_image")
    private int mMediumImage;
    @DatabaseField(columnName = "name")
    private String mName;
    @DatabaseField(columnName = "small_image")
    private int mSmallImage;

    public int getmId() {
        return this.mId;
    }

    public String getmName() {
        return this.mName;
    }

    public String getmDescription() {
        return this.mDescription;
    }

    public int getmCircle() {
        return this.mCircle;
    }

    public int getmColor() {
        return this.mColor;
    }

    public int getmSmallImage() {
        return this.mSmallImage;
    }

    public int getmMediumImage() {
        return this.mMediumImage;
    }

    public int getmBigImage() {
        return this.mBigImage;
    }

    public int getmHomeImage() {
        return this.mHomeImage;
    }

    public Collection<Award> getmAwards() {
        return this.mAwards;
    }

    public void setmId(int mId2) {
        this.mId = mId2;
    }

    public void setmName(String mName2) {
        this.mName = mName2;
    }

    public void setmDescription(String mDescription2) {
        this.mDescription = mDescription2;
    }

    public void setmCircle(int mCircle2) {
        this.mCircle = mCircle2;
    }

    public void setmColor(int mColor2) {
        this.mColor = mColor2;
    }

    public void setmSmallImage(int mSmallImage2) {
        this.mSmallImage = mSmallImage2;
    }

    public void setmMediumImage(int mMediumImage2) {
        this.mMediumImage = mMediumImage2;
    }

    public void setmBigImage(int mBigImage2) {
        this.mBigImage = mBigImage2;
    }

    public void setmHomeImage(int mHomeImage2) {
        this.mHomeImage = mHomeImage2;
    }
}
