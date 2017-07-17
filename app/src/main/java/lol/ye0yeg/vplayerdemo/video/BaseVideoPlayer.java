package lol.ye0yeg.vplayerdemo.video;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import lol.ye0yeg.vplayerdemo.listener.MediaPlayerListener;

/**
 * Created by Administrator on 7/17/2017.
 */

public abstract class BaseVideoPlayer extends FrameLayout implements MediaPlayerListener {


    //是否当前fullScreen
    private Boolean mIfCurrentIsFullscreen;

    public BaseVideoPlayer(Context context, Boolean fullFlag) {
        super(context);
        mIfCurrentIsFullscreen = fullFlag;
    }

    public BaseVideoPlayer(Context context) {
        super(context);
    }

    public BaseVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseVideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
