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

public class StepFragment extends Fragment {

    PlayerView mPlayerView;
    SimpleExoPlayer mPlayer;
    Step mStep;
    TextView mDescription;

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

        if(mStep!=null){
            initializePlayer();
        }


        return rootView;
    }

    private void initializePlayer() {
        if(mPlayerView==null){
            return;
        }
        if (mStep.getVideoUrl() == null) {
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

        Uri uri = Uri.parse(mStep.getVideoUrl());
        MediaSource mediaSource = buildMediaSource(uri);

        mPlayer.prepare(mediaSource);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
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

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void updateData() {
        mStep = getArguments().getParcelable("Step");
        releasePlayer();
        initializePlayer();
        if (mDescription != null)
            mDescription.setText(mStep.getDescription());
    }


}
