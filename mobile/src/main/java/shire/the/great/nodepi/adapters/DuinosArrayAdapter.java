package shire.the.great.nodepi.adapters;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import shire.the.great.duinos.Duino;
import shire.the.great.duinos.DuinoActions;
import shire.the.great.duinos.DuinoTypes;
import shire.the.great.http.DuinoExecution;
import shire.the.great.nodepi.R;
import shire.the.great.nodepi.tasks.AsyncResult;
import shire.the.great.nodepi.tasks.PingTask;
import shire.the.great.nodepi.tasks.RelayTask;

/**
 * Created by ZachS on 11/6/2016.
 */

public class DuinosArrayAdapter extends RecyclerView.Adapter<DuinosArrayAdapter.ViewHolder> {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout mLinearLayout;
        public Button mPingButton;
        public Button mLightsOn;
        public Button mLightsOff;
        public TextView mTypeTextView;
        public TextView mIdTextView;
        public TextView mHBTextView;
        public TextView mExtraTextView;


        public ViewHolder(LinearLayout v) {
            super(v);
            mLinearLayout = v;
            mPingButton = (Button) mLinearLayout.findViewById(R.id.ping);
            mLightsOn = (Button) mLinearLayout.findViewById(R.id.lightsOn);
            mLightsOff = (Button) mLinearLayout.findViewById(R.id.lightsOff);
            mTypeTextView = (TextView) mLinearLayout.findViewById(R.id.type);
            mIdTextView = (TextView) mLinearLayout.findViewById(R.id.id);
            mHBTextView = (TextView) mLinearLayout.findViewById(R.id.heartbeat);
            mExtraTextView = (TextView) mLinearLayout.findViewById(R.id.extra);
        }
    }

    private List<Duino> mDuinos;

    public DuinosArrayAdapter(List<Duino> duinos) {
        mDuinos = duinos;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DuinosArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_item_duino, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder((LinearLayout) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Button pingButton = holder.mPingButton;
        Button lightsOnButton = holder.mLightsOn;
        Button lightsOffButton = holder.mLightsOff;
        TextView idTextView = holder.mIdTextView;
        TextView typeTextView = holder.mTypeTextView;
        TextView hbTextView = holder.mHBTextView;
        TextView extraTextView = holder.mExtraTextView;

        final Duino duino = mDuinos.get(position);
        idTextView.setText(duino.getId() + "");
        typeTextView.setText(duino.getType().typeName);
        hbTextView.setText(duino.getHeartbeat().toString());
        extraTextView.setText(duino.getExtra().extra.toString());

        pingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTask task = new PingTask(new AsyncResult<Boolean>() {
                    @Override
                    public void onComplete(Boolean output) {
                    }
                }).execute(new DuinoExecution(duino.getId(), DuinoActions.Ping));
            }
        });

        if (duino.getType() == DuinoTypes.Relay) {
            if (duino.getExtra().extra.equals("1")) {
                lightsOnButton.setVisibility(View.GONE);
                lightsOffButton.setVisibility(View.VISIBLE);
            } else {
                lightsOnButton.setVisibility(View.VISIBLE);
                lightsOffButton.setVisibility(View.GONE);
            }
            lightsOnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AsyncTask task = new RelayTask(new AsyncResult<Boolean>() {
                        @Override
                        public void onComplete(Boolean output) {
                        }
                    }).execute(new DuinoExecution(duino.getId(), DuinoActions.RelayOn));
                }
            });

            lightsOffButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AsyncTask task = new RelayTask(new AsyncResult<Boolean>() {
                        @Override
                        public void onComplete(Boolean output) {
                        }
                    }).execute(new DuinoExecution(duino.getId(), DuinoActions.RelayOff));
                }
            });
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDuinos.size();
    }

    public void swap(List<Duino> duinos) {
        mDuinos.clear();
        mDuinos.addAll(duinos);
        notifyDataSetChanged();
    }
}
