package com.tapan.collegememories.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tapan.collegememories.Models.VideoModel;
import com.tapan.collegememories.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class FunnyVideosDetailsAdapter extends FirestoreRecyclerAdapter<VideoModel, FunnyVideosDetailsAdapter.MyViewHolder> {

    public FunnyVideosDetailsAdapter(@NonNull FirestoreRecyclerOptions<VideoModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull VideoModel model) {
//        Application application = ((Activity)holder.itemView.getContext()).getApplication();
//        holder.setExoPlayer(application,model.getFunnyVideo());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_video_details,parent,false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        SimpleExoPlayer simpleExoPlayer;
        PlayerView playerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

//        public void setExoPlayer(Application application, String funnyVideo) {
//
//            playerView = itemView.findViewById(R.id.iv_video_recycler_detail);
//
//            try {
//                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(application).build();
//                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
//                simpleExoPlayer = (SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(application,trackSelector);
//
//                Uri video = Uri.parse(funnyVideo);
//                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("video");
//                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//                MediaSource mediaSource = new ExtractorMediaSource(video, dataSourceFactory, extractorsFactory, null, null);
//
//                playerView.setPlayer(simpleExoPlayer);
//                simpleExoPlayer.prepare(mediaSource);
//                simpleExoPlayer.setPlayWhenReady(false);
//
//            } catch (Exception e) {
//                Toast.makeText(application, "exoplayer error: " + e.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }
    }


}
