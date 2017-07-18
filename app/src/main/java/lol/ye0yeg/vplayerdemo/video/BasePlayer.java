package lol.ye0yeg.vplayerdemo.video;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import lol.ye0yeg.vplayerdemo.R;
import lol.ye0yeg.vplayerdemo.listener.OnTransitionListener;
import lol.ye0yeg.vplayerdemo.model.SwitchVideoModel;

/**
 * Created by Administrator on 7/18/2017.
 */

public class BasePlayer extends AppCompatActivity{

    public final static String IMG_TRANSITION = "IMG_TRANSITION";
    public final static String TRANSITION = "TRANSITION";

    @BindView(R.id.svp_standard_player)
    StandardGSYVideoPlayer mStandardGSYVideoPlayer;

    private boolean isTransition;

    private android.transition.Transition mTransition;

    OrientationUtils mOrientationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_player);
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
        mStandardGSYVideoPlayer.setUp(source1,true,name);

        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.xxx2);
        mStandardGSYVideoPlayer.setThumbImageView(imageView);
        /**
         * SetTitle
         * */
        mStandardGSYVideoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        mStandardGSYVideoPlayer.getTitleTextView().setText("JustViedo");
        mStandardGSYVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //跟随系统旋转
        mOrientationUtils = new OrientationUtils(this, mStandardGSYVideoPlayer);
        /**
         * If Slide to move
         * */
        mStandardGSYVideoPlayer.setIsTouchWiget(true);
        /**
         * Set BackButton
         * */
        mStandardGSYVideoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
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

    private void initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
            ViewCompat.setTransitionName(mStandardGSYVideoPlayer, IMG_TRANSITION);
            addTransitionListener();
            startPostponedEnterTransition();
        } else {
            mStandardGSYVideoPlayer.startPlayLogic();
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
                    mStandardGSYVideoPlayer.startPlayLogic();
                    transition.removeListener(this);
                }
            });
            return true;
        }

        return false;
    }

}
