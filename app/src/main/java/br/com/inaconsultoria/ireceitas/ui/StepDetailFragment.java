package br.com.inaconsultoria.ireceitas.ui;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.inaconsultoria.ireceitas.R;
import br.com.inaconsultoria.ireceitas.data.model.Step;

@EFragment(R.layout.fragment_step_detail)
public class StepDetailFragment extends Fragment implements ExoPlayer.EventListener {

    private SimpleExoPlayer player;

    @FragmentArg
    Step step;

    @FragmentArg
    List<Step> steps;

    @ViewById
    SimpleExoPlayerView playerView;

    @ViewById
    TextView stepDescription;

    @AfterViews
    void init() {
        initPlayer();
    }

    private void initPlayer() {
        playerView.setVisibility(View.VISIBLE);
        stepDescription.setText(step.getDescription());
        // Obtem a uri
        Uri mediaUri = getMediaUri();
        if (player == null && step != null && mediaUri != null) {
            // Criando uma instancia do ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(player);

            // Preenche o listner
            player.addListener(this);

            // Prepare o MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "iReceitas");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        } else {
            playerView.setVisibility(View.GONE);
        }

    }

    private Uri getMediaUri() {
        Uri uri = null;
        if (!TextUtils.isEmpty(step.getVideoURL())) {
            uri = Uri.parse(step.getVideoURL());
        } else if (!TextUtils.isEmpty(step.getThumbnailURL())) {
            uri = Uri.parse(step.getThumbnailURL());
        }
        return uri;
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        //
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }
}
