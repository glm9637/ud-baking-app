package com.example.android.bakingapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

public class StepFragment extends Fragment {

    PlayerView mPlayerView;
    SimpleExoPlayer mPlayer;
    Step mStep;
    TextView mDescription;
    ImageView mThumbnailView;
    Bundle savedInstanceState;
    long position;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            rootView = inflater.inflate(R.layout.recipe_step_fragment, container, false);
            mDescription = rootView.findViewById(R.id.txt_description);
            if(mStep!=null) {
                mDescription.setText(mStep.getDescription());
            }
        }else {
            rootView = inflater.inflate(R.layout.recipe_step_fragment_landscape, container, false);
        }
        mPlayerView = rootView.findViewById(R.id.exo_video);
        mThumbnailView = rootView.findViewById(R.id.img_thumb);
        if(mStep!=null){
            initializePlayer();
        }
        if(savedInstanceState!=null && mPlayer != null){
            mPlayer.setPlayWhenReady(savedInstanceState.getBoolean("PlayWhenReady"));
            mPlayer.seekTo(savedInstanceState.getLong("Position"));
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    /**
	 * Initializes a new Player Object
	 */
	private void initializePlayer() {
        if(mPlayerView==null || mThumbnailView == null ||mPlayer != null){
            return;
        }

        mPlayerView.setVisibility(View.VISIBLE);
        mThumbnailView.setVisibility(View.GONE);

        if ((mStep.getVideoUrl() == null || mStep.getVideoUrl().isEmpty())) {
                mThumbnailView.setVisibility(View.VISIBLE);
                mPlayerView.setVisibility(View.INVISIBLE);
                String uriString = mStep.getThumbnailUrl();
                Uri uri = Uri.parse(uriString);
                Picasso.with(getContext()).load(uri).error(R.drawable.ic_no_photo).fit().into(mThumbnailView);
                mPlayerView.setEnabled(false);
            return;
        }

        if (getContext() == null) {
            mPlayerView.setEnabled(false);
            return;
        }
        mPlayerView.setEnabled(true);
        mPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mPlayerView.setPlayer(mPlayer);
        mPlayer.setPlayWhenReady(false);
		String uriString = mStep.getVideoUrl();
        Uri uri = Uri.parse(uriString);

        if(savedInstanceState!=null){
            mPlayer.seekTo(savedInstanceState.getLong("Position"));
            savedInstanceState = null;
        }

        MediaSource mediaSource = buildMediaSource(uri);
        mPlayer.prepare(mediaSource,false,false);
        mPlayer.setPlayWhenReady(true);
    }


    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }


    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-baking-step")).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            position = mPlayer.getCurrentPosition();
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
	
	/**
	 * updates the Displayed Description and the Player to the new Step passed over as Argument
	 */
	public void updateData() {
        mStep = getArguments().getParcelable("Step");
        releasePlayer();
        initializePlayer();
        if (mDescription != null)
            mDescription.setText(mStep.getDescription());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
	    outState.putLong("Position", position);
        super.onSaveInstanceState(outState);
    }
}
