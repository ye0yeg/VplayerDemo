package lol.ye0yeg.vplayerdemo.video;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lol.ye0yeg.vplayerdemo.R;
import lol.ye0yeg.vplayerdemo.listener.OnTransitionListener;
import lol.ye0yeg.vplayerdemo.model.SwitchVideoModel;

/**
 * Created by Administrator on 7/18/2017.
 */

public class PlayActivity extends AppCompatActivity {
    public final static String IMG_TRANSITION = "IMG_TRANSITION";
    public final static String TRANSITION = "TRANSITION";

    @BindView(R.id.video_player)
    SampleVideo videoPlayer;

    private boolean isTransition;

    private android.transition.Transition mTransition;

    OrientationUtils mOrientationUtils;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        isTransition = getIntent().getBooleanExtra(TRANSITION, false);
        init();
    }

    private void init() {
        String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        //借用了jjdxm_ijkplayer的URL
        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        String name = "普通";
        SwitchVideoModel switchVideoModel = new SwitchVideoModel(name, source1);

        String source2 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4";
        String name2 = "清晰";

        SwitchVideoModel switchVideoModel2 = new SwitchVideoModel(name2, source2);
        List<SwitchVideoModel> list = new ArrayList<>();
        list.add(switchVideoModel);
        list.add(switchVideoModel2);
        videoPlayer.setUp(list, true, "");

        /**
         * SetThumb
         * */
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.xxx2);
        videoPlayer.setThumbImageView(imageView);

        /**
         * SetTitle
         * */
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        videoPlayer.getTitleTextView().setText("JustViedo");

        /**
         * SetBackSpace
         * */
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);

        /**
         * SetRoad
         * */
        mOrientationUtils = new OrientationUtils(this, videoPlayer);

        /**
         * Set FullScreen, choose a Screen, not Full Screen
         * */
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mOrientationUtils.resolveByClick();
            }
        });

        /**
         * If Slide to move
         * */
        videoPlayer.setIsTouchWiget(true);

        /**
         * Set BackButton
         * */
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                onBackPressed();
            }
        });

        /**
         * 过度
         * */
        initTransition();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOrientationUtils != null) {
            mOrientationUtils.releaseListener();
        }
    }

    @Override
    public void onBackPressed() {
        //如果没有恢复
        if (mOrientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //Release
        videoPlayer.setStandardVideoAllCallBack(null);
        GSYVideoPlayer.releaseAllVideos();
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.onBackPressed();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
            }, 500);
        }

    }

    private void initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
            ViewCompat.setTransitionName(videoPlayer, IMG_TRANSITION);
            addTransitionListener();
            startPostponedEnterTransition();
        } else {
            videoPlayer.startPlayLogic();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean addTransitionListener() {
        mTransition = getWindow().getSharedElementEnterTransition();
        if (mTransition != null) {
            mTransition.addListener(new OnTransitionListener() {
                @Override
                public void onTransitionEnd(final Transition transition) {
                    super.onTransitionEnd(transition);
                    videoPlayer.startPlayLogic();
                    transition.removeListener(this);
                }
            });
            return true;
        }

        return false;
    }
}
