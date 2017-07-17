package lol.ye0yeg.vplayerdemo.video;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import lol.ye0yeg.vplayerdemo.R;
import lol.ye0yeg.vplayerdemo.model.SwitchVideoModel;

/**
 * Created by Administrator on 7/17/2017.
 */

public class SimplePlayer extends StandardGSYVideoPlayer {

    private TextView mMoreScale;

    private TextView mSwitchSize;

    private TextView mChangeRotate;

    private TextView mChangeTransform;

    private List<SwitchVideoModel> mUrlList = new ArrayList<>();

    private int mType = 0;

    private int mTransformSize = 0;

    private int mSourcePosition = 0;


    public SimplePlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public SimplePlayer(Context context) {
        super(context);
    }

    public SimplePlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.sample_video;
    }

    @Override
    protected void init(final Context context) {
        super.init(context);
        initView();

    }

    private void initView() {
        mMoreScale = findViewById();
    }
}
