package com.android.volley.toolbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class NetworkImageView extends ImageView {
    /* access modifiers changed from: private */
    public int mDefaultImageId;
    /* access modifiers changed from: private */
    public int mErrorImageId;
    private ImageLoader.ImageContainer mImageContainer;
    private ImageLoader mImageLoader;
    private String mUrl;

    public NetworkImageView(Context context) {
        this(context, (AttributeSet) null);
    }

    public NetworkImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageUrl(String url, ImageLoader imageLoader) {
        this.mUrl = url;
        this.mImageLoader = imageLoader;
        loadImageIfNecessary(false);
    }

    public void setDefaultImageResId(int defaultImage) {
        this.mDefaultImageId = defaultImage;
    }

    public void setErrorImageResId(int errorImage) {
        this.mErrorImageId = errorImage;
    }

    /* access modifiers changed from: package-private */
    public void loadImageIfNecessary(final boolean isInLayoutPass) {
        boolean isFullyWrapContent;
        int maxWidth;
        int maxHeight;
        int width = getWidth();
        int height = getHeight();
        ImageView.ScaleType scaleType = getScaleType();
        boolean wrapWidth = false;
        boolean wrapHeight = false;
        if (getLayoutParams() != null) {
            if (getLayoutParams().width == -2) {
                wrapWidth = true;
            } else {
                wrapWidth = false;
            }
            if (getLayoutParams().height == -2) {
                wrapHeight = true;
            } else {
                wrapHeight = false;
            }
        }
        if (!wrapWidth || !wrapHeight) {
            isFullyWrapContent = false;
        } else {
            isFullyWrapContent = true;
        }
        if (width != 0 || height != 0 || isFullyWrapContent) {
            if (TextUtils.isEmpty(this.mUrl)) {
                if (this.mImageContainer != null) {
                    this.mImageContainer.cancelRequest();
                    this.mImageContainer = null;
                }
                setDefaultImageOrNull();
                return;
            }
            if (!(this.mImageContainer == null || this.mImageContainer.getRequestUrl() == null)) {
                if (!this.mImageContainer.getRequestUrl().equals(this.mUrl)) {
                    this.mImageContainer.cancelRequest();
                    setDefaultImageOrNull();
                } else {
                    return;
                }
            }
            if (wrapWidth) {
                maxWidth = 0;
            } else {
                maxWidth = width;
            }
            if (wrapHeight) {
                maxHeight = 0;
            } else {
                maxHeight = height;
            }
            this.mImageContainer = this.mImageLoader.get(this.mUrl, new ImageLoader.ImageListener() {
                public void onErrorResponse(VolleyError error) {
                    if (NetworkImageView.this.mErrorImageId != 0) {
                        NetworkImageView.this.setImageResource(NetworkImageView.this.mErrorImageId);
                    }
                }

                public void onResponse(final ImageLoader.ImageContainer response, boolean isImmediate) {
                    if (isImmediate && isInLayoutPass) {
                        NetworkImageView.this.post(new Runnable() {
                            public void run() {
                                AnonymousClass1.this.onResponse(response, false);
                            }
                        });
                    } else if (response.getBitmap() != null) {
                        NetworkImageView.this.setImageBitmap(response.getBitmap());
                    } else if (NetworkImageView.this.mDefaultImageId != 0) {
                        NetworkImageView.this.setImageResource(NetworkImageView.this.mDefaultImageId);
                    }
                }
            }, maxWidth, maxHeight, scaleType);
        }
    }

    private void setDefaultImageOrNull() {
        if (this.mDefaultImageId != 0) {
            setImageResource(this.mDefaultImageId);
        } else {
            setImageBitmap((Bitmap) null);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        loadImageIfNecessary(true);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (this.mImageContainer != null) {
            this.mImageContainer.cancelRequest();
            setImageBitmap((Bitmap) null);
            this.mImageContainer = null;
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }
}
