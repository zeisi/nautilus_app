package org.acra.collector;

import android.annotation.TargetApi;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.builder.ReportBuilder;
import org.acra.model.ComplexElement;
import org.acra.model.Element;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class MediaCodecListCollector extends Collector {
    private static final String[] AAC_TYPES = {"aac", "AAC"};
    private static final String[] AVC_TYPES = {"avc", "h264", "AVC", "H264"};
    private static final String COLOR_FORMAT_PREFIX = "COLOR_";
    private static final String[] H263_TYPES = {"h263", "H263"};
    private static final String[] MPEG4_TYPES = {"mp4", "mpeg4", "MP4", "MPEG4"};
    private final SparseArray<String> mAACProfileValues = new SparseArray<>();
    private final SparseArray<String> mAVCLevelValues = new SparseArray<>();
    private final SparseArray<String> mAVCProfileValues = new SparseArray<>();
    private final SparseArray<String> mColorFormatValues = new SparseArray<>();
    private final SparseArray<String> mH263LevelValues = new SparseArray<>();
    private final SparseArray<String> mH263ProfileValues = new SparseArray<>();
    private final SparseArray<String> mMPEG4LevelValues = new SparseArray<>();
    private final SparseArray<String> mMPEG4ProfileValues = new SparseArray<>();

    private enum CodecType {
        AVC,
        H263,
        MPEG4,
        AAC
    }

    MediaCodecListCollector() {
        super(ReportField.MEDIA_CODEC_LIST);
    }

    /* access modifiers changed from: package-private */
    @TargetApi(16)
    @NonNull
    public Element collect(ReportField reportField, ReportBuilder reportBuilder) {
        try {
            return collectMediaCodecList();
        } catch (JSONException e) {
            ACRA.log.w("Could not collect media codecs", (Throwable) e);
            return ACRAConstants.NOT_AVAILABLE;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean shouldCollect(Set<ReportField> crashReportFields, ReportField collect, ReportBuilder reportBuilder) {
        return super.shouldCollect(crashReportFields, collect, reportBuilder) && Build.VERSION.SDK_INT >= 16;
    }

    private void prepare() {
        try {
            for (Field f : Class.forName("android.media.MediaCodecInfo$CodecCapabilities").getFields()) {
                if (Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()) && f.getName().startsWith(COLOR_FORMAT_PREFIX)) {
                    this.mColorFormatValues.put(f.getInt((Object) null), f.getName());
                }
            }
            for (Field f2 : Class.forName("android.media.MediaCodecInfo$CodecProfileLevel").getFields()) {
                if (Modifier.isStatic(f2.getModifiers()) && Modifier.isFinal(f2.getModifiers())) {
                    if (f2.getName().startsWith("AVCLevel")) {
                        this.mAVCLevelValues.put(f2.getInt((Object) null), f2.getName());
                    } else if (f2.getName().startsWith("AVCProfile")) {
                        this.mAVCProfileValues.put(f2.getInt((Object) null), f2.getName());
                    } else if (f2.getName().startsWith("H263Level")) {
                        this.mH263LevelValues.put(f2.getInt((Object) null), f2.getName());
                    } else if (f2.getName().startsWith("H263Profile")) {
                        this.mH263ProfileValues.put(f2.getInt((Object) null), f2.getName());
                    } else if (f2.getName().startsWith("MPEG4Level")) {
                        this.mMPEG4LevelValues.put(f2.getInt((Object) null), f2.getName());
                    } else if (f2.getName().startsWith("MPEG4Profile")) {
                        this.mMPEG4ProfileValues.put(f2.getInt((Object) null), f2.getName());
                    } else if (f2.getName().startsWith("AAC")) {
                        this.mAACProfileValues.put(f2.getInt((Object) null), f2.getName());
                    }
                }
            }
        } catch (ClassNotFoundException e) {
        } catch (SecurityException e2) {
        } catch (IllegalAccessException e3) {
        } catch (IllegalArgumentException e4) {
        }
    }

    @TargetApi(16)
    @NonNull
    private Element collectMediaCodecList() throws JSONException {
        MediaCodecInfo[] infos;
        prepare();
        if (Build.VERSION.SDK_INT < 21) {
            int codecCount = MediaCodecList.getCodecCount();
            infos = new MediaCodecInfo[codecCount];
            for (int codecIdx = 0; codecIdx < codecCount; codecIdx++) {
                infos[codecIdx] = MediaCodecList.getCodecInfoAt(codecIdx);
            }
        } else {
            infos = new MediaCodecList(1).getCodecInfos();
        }
        ComplexElement result = new ComplexElement();
        for (int i = 0; i < infos.length; i++) {
            MediaCodecInfo codecInfo = infos[i];
            JSONObject codec = new JSONObject();
            String[] supportedTypes = codecInfo.getSupportedTypes();
            codec.put("name", codecInfo.getName()).put("isEncoder", codecInfo.isEncoder());
            JSONObject supportedTypesJson = new JSONObject();
            for (String type : supportedTypes) {
                supportedTypesJson.put(type, collectCapabilitiesForType(codecInfo, type));
            }
            codec.put("supportedTypes", supportedTypesJson);
            result.put(String.valueOf(i), codec);
        }
        return result;
    }

    @TargetApi(16)
    @NonNull
    private JSONObject collectCapabilitiesForType(@NonNull MediaCodecInfo codecInfo, @NonNull String type) throws JSONException {
        JSONObject result = new JSONObject();
        MediaCodecInfo.CodecCapabilities codecCapabilities = codecInfo.getCapabilitiesForType(type);
        int[] colorFormats = codecCapabilities.colorFormats;
        if (colorFormats.length > 0) {
            JSONArray colorFormatsJson = new JSONArray();
            for (int colorFormat : colorFormats) {
                colorFormatsJson.put(this.mColorFormatValues.get(colorFormat));
            }
            result.put("colorFormats", colorFormatsJson);
        }
        CodecType codecType = identifyCodecType(codecInfo);
        MediaCodecInfo.CodecProfileLevel[] codecProfileLevels = codecCapabilities.profileLevels;
        if (codecProfileLevels.length > 0) {
            JSONArray profileLevels = new JSONArray();
            int length = codecProfileLevels.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    MediaCodecInfo.CodecProfileLevel codecProfileLevel = codecProfileLevels[i];
                    int profileValue = codecProfileLevel.profile;
                    int levelValue = codecProfileLevel.level;
                    if (codecType == null) {
                        profileLevels.put(profileValue + 45 + levelValue);
                    } else {
                        switch (codecType) {
                            case AVC:
                                profileLevels.put(profileValue + this.mAVCProfileValues.get(profileValue) + '-' + this.mAVCLevelValues.get(levelValue));
                                break;
                            case H263:
                                profileLevels.put(this.mH263ProfileValues.get(profileValue) + '-' + this.mH263LevelValues.get(levelValue));
                                break;
                            case MPEG4:
                                profileLevels.put(this.mMPEG4ProfileValues.get(profileValue) + '-' + this.mMPEG4LevelValues.get(levelValue));
                                break;
                            case AAC:
                                profileLevels.put(this.mAACProfileValues.get(profileValue));
                                break;
                        }
                        i++;
                    }
                }
            }
            result.put("profileLevels", profileLevels);
        }
        return result;
    }

    @Nullable
    @TargetApi(16)
    private CodecType identifyCodecType(@NonNull MediaCodecInfo codecInfo) {
        String name = codecInfo.getName();
        for (String token : AVC_TYPES) {
            if (name.contains(token)) {
                return CodecType.AVC;
            }
        }
        for (String token2 : H263_TYPES) {
            if (name.contains(token2)) {
                return CodecType.H263;
            }
        }
        for (String token3 : MPEG4_TYPES) {
            if (name.contains(token3)) {
                return CodecType.MPEG4;
            }
        }
        for (String token4 : AAC_TYPES) {
            if (name.contains(token4)) {
                return CodecType.AAC;
            }
        }
        return null;
    }
}
