package com.nautilus.omni.appmodules.journal.view.day;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseFragment;
import com.nautilus.omni.appmodules.journal.adapters.JournalDayAdapter;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutlist.DayPresenter;
import com.nautilus.omni.appmodules.journal.view.day.workoutdetail.WorkoutDetailActivity;
import com.nautilus.omni.customviews.stickyheaders.StickyHeadersAdapter;
import com.nautilus.omni.customviews.stickyheaders.StickyHeadersBuilder;
import com.nautilus.omni.customviews.stickyheaders.StickyHeadersItemDecoration;
import com.nautilus.omni.dependencyinjection.components.MainOmniComponent;
import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.BroadcastKeys;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JournalDayFragment extends BaseFragment implements DayViewContract, RecyclerView.OnItemTouchListener, View.OnClickListener {
    public static final String TAG = JournalDayFragment.class.getSimpleName();
    public BroadcastReceiver connectionErrorReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            JournalDayFragment.this.mSwipeRefreshLayout.setRefreshing(false);
            Log.d(JournalDayFragment.TAG, "DEBUG - DEVICE SCREEN - CONNECTION ERROR");
        }
    };
    private ActionMode mActionMode;
    @Inject
    DayPresenter mDayPresenter;
    private GestureDetectorCompat mGestureDetector;
    /* access modifiers changed from: private */
    public JournalDayAdapter mJournalDayAdapter;
    @BindView(2131755332)
    RecyclerView mJournalDayRecyclerView;
    /* access modifiers changed from: private */
    public LinearLayoutManager mLayoutManager;
    /* access modifiers changed from: private */
    public boolean mNewWorkoutsLoaded;
    private StickyHeadersItemDecoration mOverlay;
    @BindView(2131755331)
    SwipeRefreshLayout mSwipeRefreshLayout;
    public BroadcastReceiver stopSwipeToRefreshReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            JournalDayFragment.this.mSwipeRefreshLayout.setRefreshing(false);
        }
    };
    public BroadcastReceiver syncDataFailedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            JournalDayFragment.this.mSwipeRefreshLayout.setRefreshing(false);
            Log.d(JournalDayFragment.TAG, "DEBUG - DEVICE SCREEN - SYNC DATA FAILED");
        }
    };
    public BroadcastReceiver syncDataFinishedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            boolean unused = JournalDayFragment.this.mNewWorkoutsLoaded = intent.getBooleanExtra(BroadcastKeys.NEW_WORKOUTS_LOADED, false);
            JournalDayFragment.this.mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(JournalDayFragment.this.getActivity(), JournalDayFragment.this.getString(R.string.journal_day_sync_complete_message), 0).show();
            JournalDayFragment.this.mDayPresenter.loadNewSyncedData(JournalDayFragment.this.mNewWorkoutsLoaded);
            Log.d(JournalDayFragment.TAG, "DEBUG - DEVICE SCREEN - SYNC FINISHED");
        }
    };
    public BroadcastReceiver unexpectedDisconnectionProcessReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            JournalDayFragment.this.mSwipeRefreshLayout.setRefreshing(false);
            Log.d(JournalDayFragment.TAG, "DEBUG - UNEXPECTED DISCONNECTION FROM TREADCLIMBER...");
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_journal_day, container, false);
        ButterKnife.bind((Object) this, rootView);
        return rootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainOmniComponent) getComponent(MainOmniComponent.class)).inject(this);
        this.mDayPresenter.setIDayView(this);
        initJournalDayRecyclerView();
    }

    public void onResume() {
        super.onResume();
        registerBroadcastReceivers();
    }

    private void registerBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.connectionErrorReceiver, new IntentFilter(BroadcastKeys.OMNI_CONNECTION_ERROR));
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.syncDataFinishedReceiver, new IntentFilter(BroadcastKeys.SYNC_FINISHED));
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.syncDataFailedReceiver, new IntentFilter(BroadcastKeys.SYNC_FAILED));
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.unexpectedDisconnectionProcessReceiver, new IntentFilter(BroadcastKeys.UNEXPECTED_DISCONNECTION));
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.stopSwipeToRefreshReceiver, new IntentFilter(BroadcastKeys.STOP_SYNC_STATE_ON_OTHER_SCREENS));
    }

    public void onPause() {
        super.onPause();
        unregisterBroadcastReceivers();
        this.mSwipeRefreshLayout.setRefreshing(false);
    }

    private void unregisterBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.connectionErrorReceiver);
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.syncDataFinishedReceiver);
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.syncDataFailedReceiver);
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.unexpectedDisconnectionProcessReceiver);
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.stopSwipeToRefreshReceiver);
    }

    private void initJournalDayRecyclerView() {
        this.mGestureDetector = new GestureDetectorCompat(getActivity(), new RecyclerViewOnGestureListener());
        this.mJournalDayRecyclerView.addOnItemTouchListener(this);
        this.mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        this.mJournalDayRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mJournalDayAdapter = new JournalDayAdapter(getActivity().getApplicationContext());
        this.mDayPresenter.loadWorkoutsList();
        this.mJournalDayAdapter.setHasStableIds(true);
        this.mOverlay = new StickyHeadersBuilder().setAdapter(this.mJournalDayAdapter).setRecyclerView(this.mJournalDayRecyclerView).setStickyHeadersAdapter(new InitialHeaderAdapter(), true).build();
        this.mJournalDayRecyclerView.setAdapter(this.mJournalDayAdapter);
        this.mJournalDayRecyclerView.addItemDecoration(this.mOverlay);
        initSwipeRefreshLayout();
        setJournalDayRecyclerViewOnScrollListener();
    }

    private void initSwipeRefreshLayout() {
        this.mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.blue_nautilus), getResources().getColor(R.color.blue));
        this.mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                JournalDayFragment.this.loadMoreData();
            }
        });
    }

    private void setJournalDayRecyclerViewOnScrollListener() {
        this.mJournalDayRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                JournalDayFragment.this.mDayPresenter.loadOlderData(recyclerView.getChildCount(), JournalDayFragment.this.mLayoutManager.getItemCount(), JournalDayFragment.this.mLayoutManager.findFirstVisibleItemPosition());
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadMoreData() {
        Toast.makeText(getActivity(), getString(R.string.journal_day_sync_data_message), 0).show();
        this.mDayPresenter.sendBroadcastToSyncNewData();
    }

    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        this.mGestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public void onClick(View view) {
        if (view != null && view.getId() == R.id.container_list_item) {
            executeWorkoutItemClickEvent(view);
        }
    }

    private void executeWorkoutItemClickEvent(View view) {
        if (this.mActionMode != null) {
            toggleSelection(this.mJournalDayRecyclerView.getChildAdapterPosition(view));
        } else {
            callWorkoutDetailActivity(view);
        }
    }

    private void callWorkoutDetailActivity(View view) {
        this.mDayPresenter.getSelectedWorkout(Integer.valueOf(((TextView) view.findViewById(R.id.workout_id_text_view)).getText().toString()).intValue());
    }

    public void showWorkoutsList(List<Object> dataList) {
        this.mJournalDayAdapter.updateWorkoutsList(dataList);
    }

    public void refreshListWithOlderData(List<Object> dataList) {
        this.mJournalDayAdapter.updateWorkoutsList(dataList);
    }

    public void refreshListWithNewSyncedData(List<Object> dataList) {
        this.mJournalDayAdapter.updateWorkoutsList(dataList);
    }

    public void openWorkoutDetailScreen(Workout workout) {
        Intent intent = new Intent(getActivity().getApplicationContext(), WorkoutDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("workout", workout);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        private RecyclerViewOnGestureListener() {
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            JournalDayFragment.this.onClick(JournalDayFragment.this.mJournalDayRecyclerView.findChildViewUnder(e.getX(), e.getY()));
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
        }
    }

    private void toggleSelection(int itemIndex) {
        this.mJournalDayAdapter.toggleSelection(itemIndex);
        this.mActionMode.setTitle(getString(R.string.journal_day_selected_count, new Object[]{Integer.valueOf(this.mJournalDayAdapter.getSelectedItemCount())}));
    }

    class InitialHeaderAdapter implements StickyHeadersAdapter<JournalDayAdapter.DateRowViewHolder> {
        DateTimeFormatter dateFormatter;

        public InitialHeaderAdapter() {
            this.dateFormatter = DateTimeFormat.forPattern(JournalDayFragment.this.getString(R.string.journal_date_extended));
        }

        public JournalDayAdapter.DateRowViewHolder onCreateViewHolder(ViewGroup parent) {
            return new JournalDayAdapter.DateRowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_day_date_row, parent, false));
        }

        public void onBindViewHolder(JournalDayAdapter.DateRowViewHolder dateViewHolder, int position) {
            if (JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position) instanceof String) {
                position--;
            }
            if (JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position) instanceof Workout) {
                dateViewHolder.mTextViewDate.setText(this.dateFormatter.print((ReadableInstant) ((Workout) JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position)).getmWorkoutDate()));
            } else if (JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position) instanceof Achievement) {
                dateViewHolder.mTextViewDate.setText(this.dateFormatter.print((ReadableInstant) ((Achievement) JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position)).getmGoalAchievementDate()));
            } else if (JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position) instanceof DateTime) {
                dateViewHolder.mTextViewDate.setText(this.dateFormatter.print((ReadableInstant) (DateTime) JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position)));
            } else if (!(JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position) instanceof Award)) {
                dateViewHolder.mTextViewDate.setText("");
            }
        }

        public long getHeaderId(int position) {
            if (JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position) instanceof String) {
                position--;
            }
            if (JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position) instanceof Workout) {
                return (long) this.dateFormatter.print((ReadableInstant) ((Workout) JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position)).getmWorkoutDate()).hashCode();
            }
            if (JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position) instanceof Achievement) {
                return (long) this.dateFormatter.print((ReadableInstant) ((Achievement) JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position)).getmGoalAchievementDate()).hashCode();
            }
            if (JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position) instanceof DateTime) {
                return (long) this.dateFormatter.print((ReadableInstant) (DateTime) JournalDayFragment.this.mJournalDayAdapter.getmDataList().get(position)).hashCode();
            }
            return 0;
        }
    }
}
