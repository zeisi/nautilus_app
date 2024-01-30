package org.acra.security;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.util.IOUtils;

public abstract class BaseKeyStoreFactory implements KeyStoreFactory {
    private final String certificateType;

    public enum Type {
        CERTIFICATE,
        KEYSTORE
    }

    /* access modifiers changed from: protected */
    public abstract InputStream getInputStream(@NonNull Context context);

    public BaseKeyStoreFactory() {
        this(ACRAConstants.DEFAULT_CERTIFICATE_TYPE);
    }

    public BaseKeyStoreFactory(String certificateType2) {
        this.certificateType = certificateType2;
    }

    /* access modifiers changed from: protected */
    public String getKeyStoreType() {
        return KeyStore.getDefaultType();
    }

    /* access modifiers changed from: protected */
    public Type getStreamType() {
        return Type.CERTIFICATE;
    }

    /* access modifiers changed from: protected */
    public char[] getPassword() {
        return null;
    }

    @Nullable
    public final KeyStore create(@NonNull Context context) {
        InputStream inputStream = getInputStream(context);
        if (inputStream != null) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            try {
                KeyStore keyStore = KeyStore.getInstance(getKeyStoreType());
                switch (getStreamType()) {
                    case CERTIFICATE:
                        Certificate certificate = CertificateFactory.getInstance(this.certificateType).generateCertificate(bufferedInputStream);
                        keyStore.load((InputStream) null, (char[]) null);
                        keyStore.setCertificateEntry("ca", certificate);
                        break;
                    case KEYSTORE:
                        keyStore.load(bufferedInputStream, getPassword());
                        break;
                }
                return keyStore;
            } catch (CertificateException e) {
                ACRA.log.e(ACRA.LOG_TAG, "Could not load certificate", e);
            } catch (KeyStoreException e2) {
                ACRA.log.e(ACRA.LOG_TAG, "Could not load keystore", e2);
            } catch (NoSuchAlgorithmException e3) {
                ACRA.log.e(ACRA.LOG_TAG, "Could not load keystore", e3);
            } catch (IOException e4) {
                ACRA.log.e(ACRA.LOG_TAG, "Could not load keystore", e4);
            } finally {
                IOUtils.safeClose(bufferedInputStream);
            }
        }
        return null;
    }
}
