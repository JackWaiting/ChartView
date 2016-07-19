package retrofit.jackwaiting.jackwaiting_chars.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import retrofit.jackwaiting.jackwaiting_chars.R;
import retrofit.jackwaiting.jackwaiting_chars.util.PixelUtil;

/**
 * Created by JackWaiting on 2016/6/30.
 */
public class RuleCircleView extends View {

    private float mLeftPadding, mRightPadding, mTopPadding, mBottomPadding;  //上下左右边距
    private float mRectFWidth, mRectFHeight;  //控件高与宽
    private float mRadius;  //半径
    private Bitmap mNum6VBitmap, mNumPointer;
    private Rect mSrcRect, mDstRect;
    private Matrix mPointMatrix;
    private float mNum6VData = 0; //数据
    private float mDynamicDegrees =226; //此数据初始数据一定要大于initDegrees
    private float mScale = 0.7f;  //缩放比例
    private float mFirstPoint = 0, mSecondPoint = 5.97f, mThirdPoint = 6.18f, mFourthPoint = 6.43f, mFifthPoint = 6.94f, mSixthPoint = 7.45f, mMaxPoint = 10;  //每个区间的关键点
    private int mFristMaxDegrees = 270, mSecondMaxDegrees = 300, mThirdMaxDegrees = 330, mFourthMaxDegrees = 30, mFifthMaxDegrees = 90, mSixthMaxDegrees = 135;  //每个区间点的最大角度
    private float mFristDegrees = 45, mSecondDegrees = 30, mThirdDegrees = 30, mFourthDegrees = 60, mFifthDegrees = 60, mSixthDegrees = 45;  //每个区间的角度范围
    private int mViewBackGround;
    private Context mContext;
    private int mSpeed = 1;  //动画速度
    private float initDegrees = 223f;  //由起始点225减去动画的角度3
    private float startDegrees  = 223f,endDegrees = 135;
    private int mBattaryGrade;

    public RuleCircleView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public RuleCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public RuleCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void setViewBackGround(int viewId) {
        Log.i("进来了", "setViewBackGround");
        mViewBackGround = viewId;
        mNum6VBitmap = BitmapFactory.decodeResource(getResources(), mViewBackGround);
    }

    //初始化
    private void init() {
        Log.i("进来了", "init");
        mLeftPadding = PixelUtil.dp2px(10, getContext());
        mTopPadding = PixelUtil.dp2px(10, getContext());
        mRightPadding = PixelUtil.dp2px(10, getContext());
        mBottomPadding = PixelUtil.dp2px(10, getContext());
        //mRadius = PixelUtil.dp2px(150, getContext());
        mNumPointer = BitmapFactory.decodeResource(getResources(), R.mipmap.img_num_pointer);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("进来了", "onDraw");
        super.onDraw(canvas);
        drawBitMapBackGround(canvas);
        drawBitMapPointer(canvas);
    }

    public void setDynamicDegrees(float mDynamicDegrees){
        this.mDynamicDegrees =getDegrees(mDynamicDegrees);
        myHander.sendEmptyMessage(0x00);
    }

    public void refreshPointerRotateAnimation() {
        Log.i("我想知道这个值：", initDegrees + "," + mDynamicDegrees);
        if((initDegrees >=startDegrees && initDegrees <360 && mDynamicDegrees>initDegrees && mDynamicDegrees>=startDegrees && mDynamicDegrees <360) ||
                (initDegrees >=0 && initDegrees <=endDegrees && mDynamicDegrees>initDegrees && mDynamicDegrees>=0 && mDynamicDegrees <=endDegrees) ||
                (initDegrees >=startDegrees && initDegrees <360 && mDynamicDegrees <initDegrees && mDynamicDegrees>=0 && mDynamicDegrees <=endDegrees)){
            Log.i("我想知道这个值：", "进来了+"+initDegrees + "," + mDynamicDegrees);
            if (mDynamicDegrees >= initDegrees && mDynamicDegrees < 360) {

                if(initDegrees<=mDynamicDegrees ){
                    initDegrees = initDegrees + mSpeed;  //没帧画的角度间隔
                }
                if(initDegrees>=mDynamicDegrees){
                    initDegrees = mDynamicDegrees;
                    setmPointMatrix(mDynamicDegrees);
                }else{

                    setmPointMatrix(initDegrees);
                }

                if(initDegrees<mDynamicDegrees){

                    myHander.sendEmptyMessage(0x00);  //由于是线程处理，交给handle

                }

            } else if (mDynamicDegrees >= 0 && mDynamicDegrees <= endDegrees) {
                initDegrees = initDegrees + mSpeed;
                if (initDegrees >= 360) {
                    initDegrees = 0;
                }
                if(initDegrees>=mDynamicDegrees &&initDegrees<startDegrees){
                    initDegrees = mDynamicDegrees;
                    setmPointMatrix(mDynamicDegrees);
                }else{
                    setmPointMatrix(initDegrees);
                }

                if(initDegrees>=startDegrees &&initDegrees<=360 || initDegrees<mDynamicDegrees ){
                    myHander.sendEmptyMessage(0x00);
                }
            }
        }
        else{

            Log.i("我想知道这个值：", "进来了-"+initDegrees + "," + mDynamicDegrees);
            if (mDynamicDegrees > initDegrees && mDynamicDegrees >=startDegrees &&mDynamicDegrees <360) {

                if((mDynamicDegrees<=initDegrees)||(initDegrees >=0&&initDegrees <=endDegrees) ){
                    initDegrees = initDegrees - mSpeed;  //没帧画的角度间隔
                }
                if(initDegrees<=0) {
                    initDegrees = 360;
                }
                if(initDegrees<=mDynamicDegrees &&initDegrees>=startDegrees){
                    initDegrees = mDynamicDegrees;
                    setmPointMatrix(mDynamicDegrees);
                }else{

                    setmPointMatrix(initDegrees);
                }

                if((initDegrees>0 &&initDegrees<endDegrees) ||initDegrees >mDynamicDegrees){

                    myHander.sendEmptyMessage(0x00);  //由于是线程处理，交给handle

                }
            } else if ((mDynamicDegrees >= 0 && mDynamicDegrees <= endDegrees && mDynamicDegrees <initDegrees)
                    ||(mDynamicDegrees >=startDegrees &&mDynamicDegrees <360 &&mDynamicDegrees < initDegrees )) {
                initDegrees = initDegrees -mSpeed;
                if(initDegrees < mDynamicDegrees &&initDegrees>=0){
                    initDegrees = mDynamicDegrees;
                    setmPointMatrix(mDynamicDegrees);
                }else{
                    setmPointMatrix(initDegrees);
                }
                setmPointMatrix(initDegrees);
                if((initDegrees>0 &&initDegrees<=endDegrees && initDegrees>mDynamicDegrees)
                        ||(initDegrees >=startDegrees &&initDegrees <360 &&mDynamicDegrees < initDegrees )){
                    myHander.sendEmptyMessage(0x00);
                }
            }
        }
    }

    private Handler myHander = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch(msg.what){
                case 0x00:
                    invalidate();
                    //系统方法  调用ondraw

                    break;

                default:
                    break;

            }
        }

    };

    private void setmPointMatrix(float mDegrees) {
        mPointMatrix = new Matrix();
        mPointMatrix.postRotate(mDegrees, mNumPointer.getWidth() / 2,  mNumPointer.getHeight() * 12 / 13); //
        mPointMatrix.postTranslate(mRectFWidth / 2 - mNumPointer.getWidth() / 2, getHeight() / 2 - mNumPointer.getHeight() * 12 / 13);
    }

    //描绘指针
    private void drawBitMapPointer(Canvas canvas) {
        //setmPointMatrix(mDegrees);
        refreshPointerRotateAnimation();
        canvas.drawBitmap(mNumPointer, mPointMatrix, null);
    }


    //拿到界面传给我们的值
    public void getNum6VData(float data) {
        this.mNum6VData = data;
    }

    //描绘背景图
    private void drawBitMapBackGround(Canvas canvas) {
        if (mNum6VBitmap != null) {
            canvas.drawBitmap(mNum6VBitmap, mSrcRect, mDstRect, null);
        }
    }


    private void CallbackGrade(int grade){
        if(mListener!=null){
            //把等级区间回调给界面
            mListener.OnGradeChanged(grade);
        }
    }

    //把用户给的值换算成角度
    private float getDegrees(float mNum6VData) {

        if (mNum6VData >= mFirstPoint && mNum6VData <= mSecondPoint) {
            CallbackGrade(0);
            return mFristMaxDegrees - ((mSecondPoint - mNum6VData) / mSecondPoint) * mFristDegrees;
        } else if (mNum6VData > mSecondPoint && mNum6VData <= mThirdPoint) {
            CallbackGrade(1);
            return mSecondMaxDegrees - ((mThirdPoint - mNum6VData) / (mThirdPoint - mSecondPoint)) * mSecondDegrees;
        } else if (mNum6VData > mThirdPoint && mNum6VData <= mFourthPoint) {
            CallbackGrade(2);
            return mThirdMaxDegrees - ((mFourthPoint - mNum6VData) / (mFourthPoint - mThirdPoint)) * mThirdDegrees;
        } else if (mNum6VData > mFourthPoint && mNum6VData <= mFifthPoint) {
            CallbackGrade(3);
            float flagNum = mFourthMaxDegrees - ((mFifthPoint - mNum6VData) / (mFifthPoint - mFourthPoint)) * mFourthDegrees;
            //如果是负数，换算成对应的正数
            if (flagNum < 0) {
                flagNum = 360 + flagNum;
            }
            return flagNum;
        } else if (mNum6VData > mFifthPoint && mNum6VData <= mSixthPoint) {
            CallbackGrade(3);
            return mFifthMaxDegrees - ((mSixthPoint - mNum6VData) / (mSixthPoint - mFifthPoint)) * mFifthDegrees;
        } else if (mNum6VData > mSixthPoint && mNum6VData <= mMaxPoint) {
            CallbackGrade(4);
            return mSixthMaxDegrees - ((mMaxPoint - mNum6VData) / (mMaxPoint - mSixthPoint)) * mSixthDegrees;
        }
        CallbackGrade(4);

        return 135f;
    }


    //根据角度显示电量等级
    public int  getBattaryGrade(){
        return mBattaryGrade;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i("进来了", "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        mRectFWidth = w;
        mRectFHeight = h;
        mRadius = mRectFWidth / 2;
        if (mNum6VBitmap != null) {
            mSrcRect = new Rect(0, 0, mNum6VBitmap.getWidth(), mNum6VBitmap.getHeight());// BitMap源区域
        }

        mDstRect = new Rect((int) (mRectFWidth / 2 - mRadius), (int) (mRectFHeight / 2 - mRadius), (int) (mRectFWidth / 2 + mRadius), (int) (mRectFHeight / 2 + mRadius));// BitMap目标区域

        mDynamicDegrees = getDegrees(mNum6VData);
       /* if(mListener!=null){
            //把等级区间回调给界面
            mListener.OnGradeChanged(getBattaryGrade());
        }*/
    }





    //设置动画
    private void setAnim(float anim) {

        /** 设置旋转动画 */
        final RotateAnimation animation = new RotateAnimation(225f, anim, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.9f);
        animation.setDuration(3000);//设置动画持续时间
        /** 常用方法 */
        animation.setRepeatCount(0);//设置重复次数
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        animation.setStartOffset(100);//执行前的等待时间
        /*imageView.setImageBitmap(mNumPointer);
        imageView.setAnimation(animation);
        animation.startNow();*/
    }

    private OnGradeChangedListener mListener;

    public void setOnGradeChangedListener(OnGradeChangedListener listener) {
        this.mListener = listener;
    }

    public void removeOnGradeChangedListener() {
        this.mListener = null;
    }

    public interface OnGradeChangedListener {
        void  OnGradeChanged(int grade);
    }

}
