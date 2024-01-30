package com.nautilus.omni.appmodules.connectionwizard.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.connectionwizard.presenter.DevicesListPresenterContract;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.model.dto.OmniMachineType;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.Constants;
import java.util.List;

public class OmniTrainerListAdapter extends RecyclerView.Adapter<OmniTrainerRowViewHolder> {
    private static final float AVAILABLE_ALPHA = 1.0f;
    private static final int RANGE_VALUE = 15;
    private static final float UNAVAILABLE_ALPHA = 0.5f;
    public static OmniData mCurrentSelectedOmniData;
    /* access modifiers changed from: private */
    public static boolean mIsOmniTrainerSelected;
    /* access modifiers changed from: private */
    public static SortedList<OmniData> mOmniTrainerList;
    /* access modifiers changed from: private */
    public static View mToastLayout;
    /* access modifiers changed from: private */
    public static TextView mTxtViewSkip;
    AdapterView adapterView = new AdapterView() {
        public void onVerifieDefaultOmniDataSuccess(OmniData omniData) {
            OmniTrainerListAdapter.mCurrentSelectedOmniData = new OmniData();
            OmniTrainerListAdapter.mCurrentSelectedOmniData = omniData;
        }

        public int getItemsCount() {
            return OmniTrainerListAdapter.mOmniTrainerList.size();
        }
    };
    DevicesListPresenterContract devicesListPresenter;
    private Context mContext;

    public interface AdapterView {
        int getItemsCount();

        void onVerifieDefaultOmniDataSuccess(OmniData omniData);
    }

    public OmniTrainerListAdapter(Context context, DevicesListPresenterContract pDevicesListPresenter, View toastLayout, TextView txtViewSkip) {
        this.mContext = context;
        this.devicesListPresenter = pDevicesListPresenter;
        this.devicesListPresenter.setAdapterView(this.adapterView);
        mIsOmniTrainerSelected = false;
        mToastLayout = toastLayout;
        mTxtViewSkip = txtViewSkip;
        initMaxTrainerSortedList();
    }

    public class OmniTrainerRowViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageViewSignal;
        public ImageView mImgViewOmniTrainer;
        public RelativeLayout mMaxTrainer5InfoContainer;
        public LinearLayout mMaxTrainer7InfoContainer;
        public OmniData mOmniData;
        public LinearLayout mOmniTrainerContainer;
        public TextView mTxtViewOmniTrainerId;
        public TextView mTxtViewOmniTrainerState;
        public TextView mTxtViewOmniTrainerTitle;

        public OmniTrainerRowViewHolder(View view) {
            super(view);
            this.mOmniTrainerContainer = (LinearLayout) view.findViewById(R.id.max_trainer_row_container);
            this.mMaxTrainer7InfoContainer = (LinearLayout) view.findViewById(R.id.max_trainer_7_info_container);
            this.mMaxTrainer5InfoContainer = (RelativeLayout) view.findViewById(R.id.max_trainer_5_info_container);
            this.mImageViewSignal = (ImageView) view.findViewById(R.id.signal_image_view);
            this.mImgViewOmniTrainer = (ImageView) view.findViewById(R.id.max_trainer_image_view);
            this.mTxtViewOmniTrainerTitle = (TextView) view.findViewById(R.id.max_trainer_title_text_view);
            this.mTxtViewOmniTrainerId = (TextView) view.findViewById(R.id.max_trainer_id_text_view);
            this.mTxtViewOmniTrainerState = (TextView) view.findViewById(R.id.max_trainer_state_text_view);
            setOnClickListener(view);
        }

        public void setOnClickListener(final View view) {
            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int layoutPosition = OmniTrainerRowViewHolder.this.getLayoutPosition();
                    if (layoutPosition > -1 && !((OmniData) OmniTrainerListAdapter.mOmniTrainerList.get(layoutPosition)).ismIsBusy() && !OmniTrainerListAdapter.mIsOmniTrainerSelected) {
                        boolean unused = OmniTrainerListAdapter.mIsOmniTrainerSelected = true;
                        OmniTrainerRowViewHolder.this.mOmniTrainerContainer.setBackgroundColor(view.getContext().getResources().getColor(R.color.omni_trainer_list_selected_color));
                        OmniTrainerRowViewHolder.this.executeMaxTrainerListClickEvent(view, layoutPosition);
                    }
                }
            });
        }

        /* access modifiers changed from: private */
        public void executeMaxTrainerListClickEvent(View view, int layoutPosition) {
            this.mOmniData = (OmniData) OmniTrainerListAdapter.mOmniTrainerList.get(layoutPosition);
            if (!this.mOmniData.ismIsBusy()) {
                OmniTrainerListAdapter.mTxtViewSkip.setEnabled(false);
                OmniTrainerListAdapter.this.devicesListPresenter.updateDefaultMaxTrainer(this.mOmniData);
                showConnectingDeviceToast(view);
                sendConnectToMaxTrainerBroadcast(view);
            }
        }

        private void showConnectingDeviceToast(View view) {
            Animation fadeInAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.abc_fade_in);
            fadeInAnimation.setDuration(2000);
            OmniTrainerListAdapter.mToastLayout.setVisibility(0);
            OmniTrainerListAdapter.mToastLayout.startAnimation(fadeInAnimation);
        }

        private void sendConnectToMaxTrainerBroadcast(View view) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.OMNI_TRAINER, this.mOmniData);
            bundle.putBoolean(Constants.CONNECTION_FROM_OMNI_TRAINER_LIST, true);
            intent.setAction(BroadcastKeys.CONNECT_TO_OMNI_DEVICE);
            intent.putExtras(bundle);
            LocalBroadcastManager.getInstance(view.getContext().getApplicationContext()).sendBroadcast(intent);
        }
    }

    public OmniTrainerRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OmniTrainerRowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.omni_trainer_row, parent, false));
    }

    public void onBindViewHolder(OmniTrainerRowViewHolder holder, int position) {
        if (mOmniTrainerList.size() > 0) {
            OmniData omniData = mOmniTrainerList.get(position);
            showOmniTrainerInfoContainer(omniData, holder);
            setImageSignal(holder, omniData);
            setOmniTrainerId(holder, omniData);
            setOmniTrainerState(holder, omniData);
            holder.mOmniData = omniData;
        }
    }

    private void showOmniTrainerInfoContainer(OmniData omniData, OmniTrainerRowViewHolder holder) {
        if (omniData.getmOmniMachineType().equals(OmniMachineType.MY14_BIKE_ELLIPTICAL) || omniData.getmOmniMachineType().equals(OmniMachineType.MY16_BIKE_ELLIPTICAL) || omniData.getmOmniMachineType().equals(OmniMachineType.ELLIPTICAL_E628_INTERNATIONAL) || omniData.getmOmniMachineType().equals(OmniMachineType.UPRIGHT_BIKE_UR628_INTERNATIONAL) || omniData.getmOmniMachineType().equals(OmniMachineType.MY16_INTERNATIONAL_RECUMBENT_BIKE) || omniData.getmOmniMachineType().equals(OmniMachineType.MY17_ELLIPTICAL_E618) || omniData.getmOmniMachineType().equals(OmniMachineType.MY17_ELLIPTICAL_E616) || omniData.getmOmniMachineType().equals(OmniMachineType.MY17_UPRIGHT_BIKE_UR616) || omniData.getmOmniMachineType().equals(OmniMachineType.MY17_BIKE_B618)) {
            holder.mImgViewOmniTrainer.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), R.drawable.ic_bike_elliptical, (Resources.Theme) null));
            holder.mTxtViewOmniTrainerTitle.setText(this.mContext.getString(R.string.connection_elliptical_title));
        } else if (omniData.getmOmniMachineType().equals(OmniMachineType.MY14_TREADMILL) || omniData.getmOmniMachineType().equals(OmniMachineType.MY17_TREADMILL_T616)) {
            holder.mImgViewOmniTrainer.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), R.drawable.ic_treadmill, (Resources.Theme) null));
            holder.mTxtViewOmniTrainerTitle.setText(this.mContext.getString(R.string.connection_t616_treadmill_title));
        } else if (omniData.getmOmniMachineType().equals(OmniMachineType.MY16_INTERNATIONAL_TREADMILL)) {
            holder.mImgViewOmniTrainer.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), R.drawable.ic_treadmill, (Resources.Theme) null));
            holder.mTxtViewOmniTrainerTitle.setText(this.mContext.getString(R.string.connection_t628_treadmill_title));
        } else {
            holder.mImgViewOmniTrainer.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), R.drawable.ic_treadmill, (Resources.Theme) null));
            holder.mTxtViewOmniTrainerTitle.setText(this.mContext.getString(R.string.connection_t618_treadmill_title));
        }
    }

    private void setImageSignal(OmniTrainerRowViewHolder holder, OmniData omniData) {
        if (isSignalStrong(omniData.getmSignalStrength())) {
            holder.mImageViewSignal.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), R.drawable.ic_signal_4, (Resources.Theme) null));
        } else if (isSignalMedium(omniData.getmSignalStrength())) {
            holder.mImageViewSignal.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), R.drawable.ic_signal_3, (Resources.Theme) null));
        } else if (isSignalWeak(omniData.getmSignalStrength())) {
            holder.mImageViewSignal.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), R.drawable.ic_signal_2, (Resources.Theme) null));
        } else if (isVeryWeakSignal(omniData.getmSignalStrength())) {
            holder.mImageViewSignal.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), R.drawable.ic_signal_1, (Resources.Theme) null));
        } else {
            holder.mImageViewSignal.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), R.drawable.ic_signal_0, (Resources.Theme) null));
        }
    }

    private boolean isSignalStrong(int signal) {
        return signal > 75;
    }

    private boolean isSignalMedium(int signal) {
        return signal > 50;
    }

    private boolean isSignalWeak(int signal) {
        return signal > 25;
    }

    private boolean isVeryWeakSignal(int signal) {
        return signal < 25 && signal > 0;
    }

    private void setOmniTrainerId(OmniTrainerRowViewHolder holder, OmniData omniData) {
        holder.mTxtViewOmniTrainerId.setText(String.valueOf(omniData.getmUniqueID()));
    }

    private void setOmniTrainerState(OmniTrainerRowViewHolder holder, OmniData omniData) {
        if (omniData.ismIsBusy()) {
            holder.mTxtViewOmniTrainerState.setText(this.mContext.getString(R.string.connection_omni_trainer_in_use) + Constants.EMPTY_SPACE + omniData.getmUserNumber());
            holder.mImageViewSignal.setAlpha(UNAVAILABLE_ALPHA);
            holder.mImgViewOmniTrainer.setAlpha(UNAVAILABLE_ALPHA);
            holder.mTxtViewOmniTrainerId.setAlpha(UNAVAILABLE_ALPHA);
            holder.mTxtViewOmniTrainerTitle.setAlpha(UNAVAILABLE_ALPHA);
            holder.mOmniTrainerContainer.setAlpha(UNAVAILABLE_ALPHA);
            return;
        }
        holder.mTxtViewOmniTrainerState.setText(this.mContext.getString(R.string.connection_omni_trainer_idle));
        holder.mImageViewSignal.setAlpha(1.0f);
        holder.mImgViewOmniTrainer.setAlpha(1.0f);
        holder.mTxtViewOmniTrainerId.setAlpha(1.0f);
        holder.mTxtViewOmniTrainerTitle.setAlpha(1.0f);
        holder.mOmniTrainerContainer.setAlpha(1.0f);
    }

    public int getItemCount() {
        return mOmniTrainerList.size();
    }

    private void initMaxTrainerSortedList() {
        mOmniTrainerList = new SortedList<>(OmniData.class, new SortedList.Callback<OmniData>() {
            public int compare(OmniData omniData1, OmniData omniData2) {
                if (omniData1.getmSignalStrength() > omniData2.getmSignalStrength()) {
                    return -1;
                }
                if (omniData1.getmSignalStrength() == omniData2.getmSignalStrength()) {
                    return 0;
                }
                return 1;
            }

            public void onInserted(int position, int count) {
                OmniTrainerListAdapter.this.notifyItemRangeInserted(position, count);
            }

            public void onRemoved(int position, int count) {
                OmniTrainerListAdapter.this.notifyItemRangeRemoved(position, count);
            }

            public void onMoved(int fromPosition, int toPosition) {
                OmniTrainerListAdapter.this.notifyItemMoved(fromPosition, toPosition);
            }

            public void onChanged(int position, int count) {
                OmniTrainerListAdapter.this.notifyItemRangeChanged(position, count);
            }

            public boolean areContentsTheSame(OmniData oldItem, OmniData newItem) {
                return oldItem.getmSignalStrength() == newItem.getmSignalStrength();
            }

            public boolean areItemsTheSame(OmniData item1, OmniData item2) {
                return item1.getmUniqueID().equals(item2.getmUniqueID());
            }
        });
    }

    public void addAll(List<OmniData> items) {
        mOmniTrainerList.beginBatchedUpdates();
        for (OmniData item : items) {
            OmniData newOmniData = new OmniData();
            newOmniData.setmSignalStrength(item.getmSignalStrength());
            newOmniData.setIsBusy(item.ismIsBusy());
            newOmniData.setmUniqueID(item.getmUniqueID());
            newOmniData.setmUserNumber(item.getmUserNumber());
            newOmniData.setmName(item.getmName());
            newOmniData.setmOmniBluetoothDevice(item.getmOmniTrainerBluetoothDevice());
            newOmniData.setmOmniConsoleType(item.getmOmniConsoleType());
            newOmniData.setmOmniMachineType(item.getmOmniMachineType());
            mOmniTrainerList.add(newOmniData);
        }
        mOmniTrainerList.endBatchedUpdates();
    }

    public void updateList(OmniData newOmniData) {
        if (newOmniData.isEnabled().booleanValue() && isItemInList(newOmniData)) {
            makeUpdate(newOmniData);
        } else if (newOmniData.isEnabled().booleanValue() && !isItemInList(newOmniData)) {
            addOmniTrainerToList(newOmniData);
        } else if (!newOmniData.isEnabled().booleanValue() && isItemInList(newOmniData)) {
            removeOmniTrainerFromList(newOmniData);
            if (mOmniTrainerList.size() == 0) {
                this.devicesListPresenter.listEmpty();
            }
        }
    }

    private void addOmniTrainerToList(OmniData newOmniData) {
        OmniData omniData = new OmniData();
        omniData.setmSignalStrength(newOmniData.getmSignalStrength());
        omniData.setIsBusy(newOmniData.ismIsBusy());
        omniData.setmUniqueID(newOmniData.getmUniqueID());
        omniData.setmUserNumber(newOmniData.getmUserNumber());
        omniData.setmName(newOmniData.getmName());
        omniData.setmOmniBluetoothDevice(newOmniData.getmOmniTrainerBluetoothDevice());
        omniData.setmOmniConsoleType(newOmniData.getmOmniConsoleType());
        omniData.setmOmniMachineType(newOmniData.getmOmniMachineType());
        mOmniTrainerList.add(omniData);
    }

    private void removeOmniTrainerFromList(OmniData newOmniData) {
        for (int i = 0; i < mOmniTrainerList.size(); i++) {
            if (mOmniTrainerList.get(i).getmUniqueID().equals(newOmniData.getmUniqueID())) {
                mOmniTrainerList.removeItemAt(i);
            }
        }
    }

    private boolean isItemInList(OmniData omniData) {
        for (int i = 0; i < mOmniTrainerList.size(); i++) {
            if (mOmniTrainerList.get(i).getmUniqueID().equals(omniData.getmUniqueID())) {
                return true;
            }
        }
        return false;
    }

    private void makeUpdate(OmniData newOmniData) {
        for (int position = 0; position < mOmniTrainerList.size(); position++) {
            OmniData oldOmniData = mOmniTrainerList.get(position);
            boolean stateUpdated = updateOmniTrainerWhenUseStateChange(oldOmniData, newOmniData);
            if (updateOmniTrianerWhenSignalChange(oldOmniData, newOmniData) || stateUpdated) {
                mOmniTrainerList.updateItemAt(position, oldOmniData);
                return;
            }
        }
    }

    private boolean updateOmniTrianerWhenSignalChange(OmniData oldOmniData, OmniData newOmniData) {
        if (!omniTrainerSignalChanged(oldOmniData, newOmniData)) {
            return false;
        }
        oldOmniData.setmSignalStrength(newOmniData.getmSignalStrength());
        return true;
    }

    private boolean updateOmniTrainerWhenUseStateChange(OmniData oldOmniData, OmniData newOmniData) {
        if (!newOmniData.getmUniqueID().equals(oldOmniData.getmUniqueID()) || oldOmniData.ismIsBusy() == newOmniData.ismIsBusy()) {
            return false;
        }
        oldOmniData.setIsBusy(newOmniData.ismIsBusy());
        return true;
    }

    private boolean omniTrainerSignalChanged(OmniData oldOmniData, OmniData newOmniData) {
        return newOmniData.getmUniqueID().equals(oldOmniData.getmUniqueID()) && (newOmniData.getmSignalStrength() > oldOmniData.getmSignalStrength() + 15 || newOmniData.getmSignalStrength() < oldOmniData.getmSignalStrength() + -15);
    }

    public static void setIsOmniTrainerSelected(boolean mIsOmniTrainerSelected2) {
        mIsOmniTrainerSelected = mIsOmniTrainerSelected2;
    }
}
