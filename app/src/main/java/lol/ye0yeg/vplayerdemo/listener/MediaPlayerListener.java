package lol.ye0yeg.vplayerdemo.listener;

/**
 * Created by Administrator on 7/17/2017.
 */

public interface MediaPlayerListener {

    void onPrepared();

    void onAutoCompletion();

    void onCompletion();

    void onBufferingUpdate(int percent);

    void onSeekComplete();

    void onError(int what, int extra);

    void onInfo(int what, int extra);

    void onVideoSizeChanged();

    void onBackFullscreen();

    void onVideoPause();

    void onVideoResume();

}
