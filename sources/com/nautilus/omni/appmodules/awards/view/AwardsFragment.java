package com.nautilus.omni.appmodules.awards.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseFragment;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.awards.adapters.AwardsAdapter;
import com.nautilus.omni.appmodules.awards.adapters.ItemOffsetDecoration;
import com.nautilus.omni.appmodules.awards.adapters.detail.AwardDetailAdapter;
import com.nautilus.omni.appmodules.awards.presenter.AwardsPresenterContract;
import com.nautilus.omni.dependencyinjection.components.MainOmniComponent;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.util.BroadcastKeys;
import java.util.List;
import javax.inject.Inject;

public class AwardsFragment extends BaseFragment implements AwardsFragmentContract {
    public static final String TAG = AwardsFragment.class.getSimpleName();
    private final AwardsAdapter.OnItemClickListener mAdapterListener = new AwardsAdapter.OnItemClickListener() {
        public void onItemClicked(Award award) {
            if (award.getmDate() != null && !AwardsFragment.this.mPopupDetailView.isShown()) {
                AwardsFragment.this.mAwardsPresenter.loadAwardDetailInfo(award);
            }
        }
    };
    private AwardDetailAdapter mAwardDetailAdapter;
    @Inject
    AwardValueBuilder mAwardValueBuilder;
    private AwardsAdapter mAwardsAdapter;
    @Inject
    AwardsPresenterContract mAwardsPresenter;
    private ImageView mImgViewExitDetail;
    private OnAwardBackNavigationListener mOnAwardBackNavigationCallback;
    /* access modifiers changed from: private */
    public RelativeLayout mPopupDetailView;
    public BroadcastReceiver syncDataFinishedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (AwardsFragment.this.mAwardsPresenter != null) {
                AwardsFragment.this.mAwardsPresenter.loadAwards();
            }
        }
    };

    public interface OnAwardBackNavigationListener {
        void executeBackNavigation();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
        registerBroadcastReceivers();
        this.mAwardsPresenter.refreshData();
    }

    public void onPause() {
        super.onPause();
        unregisterBroadcastReceivers();
    }

    private void registerBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.syncDataFinishedReceiver, new IntentFilter(BroadcastKeys.SYNC_FINISHED));
    }

    private void unregisterBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.syncDataFinishedReceiver);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        this.mAwardsPresenter.setmAwardsFragment(this);
        this.mAwardsPresenter.loadAwards();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainOmniComponent) getComponent(MainOmniComponent.class)).inject(this);
        View root = inflater.inflate(R.layout.fragment_awards, container, false);
        initAwardsRecyclerView(root);
        initAwardDetailDialog(root);
        return root;
    }

    private void initAwardsRecyclerView(View root) {
        RecyclerView mRecyclerViewAwards = (RecyclerView) root.findViewById(R.id.recycler_view_awards);
        mRecyclerViewAwards.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), Integer.parseInt(getString(R.string.award_columns))));
        mRecyclerViewAwards.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.awards_item_offset));
        this.mAwardValueBuilder.setUnit(this.mPreferences.getInt(Preferences.UNITS_SETTINGS, 0));
        this.mAwardsAdapter = new AwardsAdapter(getActivity().getApplicationContext(), this.mAdapterListener, this.mAwardValueBuilder);
        mRecyclerViewAwards.setAdapter(this.mAwardsAdapter);
    }

    private void initAwardDetailDialog(View root) {
        this.mPopupDetailView = (RelativeLayout) root.findViewById(R.id.award_detail_content);
        RecyclerView mRecyclerViewAwardDetail = (RecyclerView) this.mPopupDetailView.findViewById(R.id.recycler_view_award_detail);
        mRecyclerViewAwardDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mAwardDetailAdapter = new AwardDetailAdapter(this.mAwardValueBuilder);
        mRecyclerViewAwardDetail.setAdapter(this.mAwardDetailAdapter);
        this.mImgViewExitDetail = (ImageView) this.mPopupDetailView.findViewById(R.id.image_view_exit_detail);
        setDetailExitClickListener();
    }

    private void setDetailExitClickListener() {
        this.mImgViewExitDetail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AwardsFragment.this.closeDetailView();
            }
        });
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void setAwards(List<Award> awards, int unit) {
        this.mAwardsAdapter.setmAwardsList(awards, unit);
    }

    public void loadAwardDetailInfo(List<Award> awards, int unit) {
        this.mAwardDetailAdapter.setmAwardsList(awards, unit);
        if (isVisible()) {
            showDetailView();
        }
    }

    private void showDetailView() {
        this.mPopupDetailView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_slide_up));
        this.mPopupDetailView.setVisibility(0);
        this.mImgViewExitDetail.setEnabled(true);
    }

    public void closeDetailView() {
        if (this.mPopupDetailView.getVisibility() == 0) {
            this.mImgViewExitDetail.setEnabled(false);
            this.mPopupDetailView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_slide_down));
            this.mPopupDetailView.setVisibility(8);
            return;
        }
        this.mOnAwardBackNavigationCallback.executeBackNavigation();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mOnAwardBackNavigationCallback = (OnAwardBackNavigationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement mOnAwardBackNavigationCallback");
        }
    }
}
