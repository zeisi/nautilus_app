package com.ua.sdk.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import com.nautilus.omni.util.Constants;
import com.ua.oss.org.apache.http.entity.ContentType;
import com.ua.oss.org.apache.http.entity.mime.HttpMultipartMode;
import com.ua.oss.org.apache.http.entity.mime.MIME;
import com.ua.oss.org.apache.http.entity.mime.MultipartEntityBuilder;
import com.ua.sdk.Resource;
import com.ua.sdk.UaException;
import com.ua.sdk.UaLog;
import com.ua.sdk.UploadCallback;
import com.ua.sdk.activitystory.AttachmentDest;
import com.ua.sdk.authentication.AuthenticationManager;
import com.ua.sdk.authentication.FilemobileCredential;
import com.ua.sdk.authentication.FilemobileCredentialManager;
import com.ua.sdk.internal.net.UrlBuilder;
import com.ua.sdk.util.Media;
import com.ua.sdk.util.ProgressHttpEntity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.Header;

public class MediaService<T extends Resource> {
    protected static final int MAX_HEIGHT = 1000;
    protected static final int MAX_WIDTH = 1000;
    protected final AuthenticationManager authManager;
    protected final ConnectionFactory connFactory;
    protected final Context context;
    protected FilemobileCredentialManager filemobileCredentialManager;
    protected final JsonParser<T> jsonParser;
    protected ProgressOutputStream outputStream;
    protected final UrlBuilder urlBuilder;

    public MediaService(Context context2, ConnectionFactory connFactory2, UrlBuilder urlBuilder2, JsonParser<T> jsonParser2, AuthenticationManager authManager2) {
        this.context = context2;
        this.connFactory = connFactory2;
        this.urlBuilder = urlBuilder2;
        this.jsonParser = jsonParser2;
        this.authManager = authManager2;
    }

    public MediaService(Context context2, ConnectionFactory connFactory2, UrlBuilder urlBuilder2, JsonParser<T> jsonParser2, AuthenticationManager authManager2, FilemobileCredentialManager filemobileCredentialManager2) {
        this(context2, connFactory2, urlBuilder2, jsonParser2, authManager2);
        this.filemobileCredentialManager = filemobileCredentialManager2;
    }

    public void close() throws IOException {
        if (this.outputStream != null) {
            this.outputStream.close();
        }
    }

    public T uploadUserProfileImage(Uri image, T entity) throws UaException {
        HttpsURLConnection conn;
        Precondition.isNotNull(image, "image");
        try {
            conn = this.connFactory.getSslConnection(this.urlBuilder.buildGetUserProfilePhotoUrl(entity.getRef()));
            File imageFile = getFile(image);
            Precondition.isNotNull(imageFile, "imageFile");
            this.authManager.signAsUser(conn);
            ByteArrayOutputStream bos = compressBitmap(resizeBitmap(imageFile, 1000, 1000));
            int length = bos.size();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty(MIME.CONTENT_TYPE, Constants.JPEG_FORMAT);
            conn.setRequestProperty("Content-Length", String.valueOf(length));
            conn.setFixedLengthStreamingMode(length);
            bos.writeTo(conn.getOutputStream());
            bos.close();
            Precondition.isResponseSuccess(conn);
            T t = (Resource) this.jsonParser.parse(conn.getInputStream());
            conn.disconnect();
            return t;
        } catch (InterruptedIOException e) {
            UaLog.debug("Upload image cancelled.");
            throw new UaException(UaException.Code.CANCELED, (Throwable) e);
        } catch (Throwable t2) {
            UaLog.error("Unable to upload image.", t2);
            throw new UaException("Unable to upload image.", t2);
        }
    }

    public T uploadImage(Uri image, AttachmentDest dest, UploadCallback callback) throws UaException {
        HttpsURLConnection conn;
        Precondition.isNotNull(dest, "dest");
        Precondition.isNotNull(image, "image");
        try {
            conn = this.connFactory.getSslConnection(this.urlBuilder.buildPostImageUrl());
            this.authManager.signAsUser(conn);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            ProgressHttpEntity body = createHttpBodyEntity(image, dest);
            long length = body.getContentLength();
            conn.setFixedLengthStreamingMode((int) length);
            conn.setRequestProperty("Content-Length", String.valueOf(length));
            Header contentType = body.getContentType();
            conn.setRequestProperty(contentType.getName(), contentType.getValue());
            Header contentEncoding = body.getContentEncoding();
            if (contentEncoding != null) {
                conn.setRequestProperty(contentEncoding.getName(), contentEncoding.getValue());
            }
            this.outputStream = new ProgressOutputStream(conn.getOutputStream(), length, callback);
            body.writeTo(this.outputStream);
            Precondition.isResponseSuccess(conn);
            conn.disconnect();
            return null;
        } catch (InterruptedIOException e) {
            UaLog.debug("Upload image cancelled.");
            throw new UaException(UaException.Code.CANCELED, (Throwable) e);
        } catch (Throwable t) {
            UaLog.error("Unable to upload image.", t);
            throw new UaException("Unable to upload image.", t);
        }
    }

    public T uploadVideo(Uri video, AttachmentDest dest, UploadCallback callback) throws UaException {
        HttpURLConnection conn;
        Precondition.isNotNull(dest, "dest");
        Precondition.isNotNull(video, "video");
        try {
            conn = this.connFactory.getConnection(this.urlBuilder.buildPostVideoUrl());
            FilemobileCredential credentials = this.filemobileCredentialManager.getFilemobileTokenCredentials();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            ProgressHttpEntity body = createHttpBodyEntity(credentials, video, dest);
            long length = body.getContentLength();
            conn.setFixedLengthStreamingMode((int) length);
            conn.setRequestProperty("Content-Length", String.valueOf(length));
            Header contentType = body.getContentType();
            conn.setRequestProperty(contentType.getName(), contentType.getValue());
            Header contentEncoding = body.getContentEncoding();
            if (contentEncoding != null) {
                conn.setRequestProperty(contentEncoding.getName(), contentEncoding.getValue());
            }
            this.outputStream = new ProgressOutputStream(conn.getOutputStream(), length, callback);
            body.writeTo(this.outputStream);
            Precondition.isResponseSuccess(conn);
            conn.disconnect();
            return null;
        } catch (InterruptedIOException e) {
            UaLog.debug("Upload video cancelled.");
            throw new UaException(UaException.Code.CANCELED, (Throwable) e);
        } catch (Throwable t) {
            UaLog.error("Unable to upload video.", t);
            throw new UaException("Unable to upload video.", t);
        }
    }

    /* access modifiers changed from: protected */
    public ContentType getContentType(Uri uri) {
        String extension;
        String type = this.context.getContentResolver().getType(uri);
        if (type == null && (extension = MimeTypeMap.getFileExtensionFromUrl(uri.getPath())) != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        if (type == null) {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(uri.getPath(), opt);
            type = opt.outMimeType;
        }
        if (type != null) {
            return ContentType.create(type);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public File getFile(Uri uri) throws UaException {
        String path = Media.getPath(this.context, uri);
        Precondition.isNotNull(path, "path");
        return new File(path);
    }

    /* access modifiers changed from: protected */
    public ByteArrayOutputStream compressBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
        return bos;
    }

    /* access modifiers changed from: protected */
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /* access modifiers changed from: protected */
    public Bitmap rotateBitmap(Bitmap photo, int rotationDeg) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) rotationDeg);
        return Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
    }

    /* access modifiers changed from: protected */
    public Bitmap resizeBitmap(File photo, int availableWidth, int availableHeight) throws IOException {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmapOptions.inDither = true;
        BitmapFactory.decodeFile(photo.getAbsolutePath(), bitmapOptions);
        bitmapOptions.inSampleSize = calculateInSampleSize(bitmapOptions, availableWidth, availableHeight);
        bitmapOptions.inJustDecodeBounds = false;
        Bitmap thumbnailBitmap = BitmapFactory.decodeFile(photo.getAbsolutePath(), bitmapOptions);
        switch (new ExifInterface(photo.getAbsolutePath()).getAttributeInt("Orientation", 1)) {
            case 3:
                return rotateBitmap(thumbnailBitmap, 180);
            case 6:
                return rotateBitmap(thumbnailBitmap, 90);
            case 8:
                return rotateBitmap(thumbnailBitmap, -90);
            default:
                return thumbnailBitmap;
        }
    }

    private ProgressHttpEntity createHttpBodyEntity(FilemobileCredential credentials, Uri video, AttachmentDest dest) throws UaException {
        ContentType videoType = getContentType(video);
        File videoFile = getFile(video);
        Precondition.isNotNull(videoFile, "videoFile");
        return new ProgressHttpEntity(MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addTextBody("sessiontoken", credentials.getToken()).addTextBody("vhost", credentials.getVhost()).addTextBody("uid", credentials.getUid()).addTextBody("meta[user][user_id]", dest.getUserId()).addTextBody("meta[user][href]", dest.getHref()).addTextBody("meta[user][index]", String.valueOf(dest.getIndex())).addTextBody("meta[user][rel]", dest.getRel()).addBinaryBody("file", videoFile, videoType, video.getLastPathSegment()).build());
    }

    private ProgressHttpEntity createHttpBodyEntity(Uri image, AttachmentDest dest) throws UaException {
        ContentType jsonType = ContentType.create("application/json");
        ContentType imageType = getContentType(image);
        File imageFile = getFile(image);
        Precondition.isNotNull(imageFile);
        UaLog.debug("request=" + dest.toString());
        return new ProgressHttpEntity(MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addBinaryBody("data", dest.toString().getBytes(), jsonType, "page_json.json").addBinaryBody("image", imageFile, imageType, image.getLastPathSegment()).build());
    }
}
