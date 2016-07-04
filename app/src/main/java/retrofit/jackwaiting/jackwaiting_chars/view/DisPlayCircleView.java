package retrofit.jackwaiting.jackwaiting_chars.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import retrofit.jackwaiting.jackwaiting_chars.R;
import retrofit.jackwaiting.jackwaiting_chars.util.PixelUtil;

/**
 * Created by JackWaiting on 2016/6/30.
 */
public class DisPlayCircleView extends View {

    private float mLeftPadding,mRightPadding,mTopPadding,mBottomPadding;  //上下左右边距
    private float mRectFWidth,mRectFHeight;  //控件高与宽
    private float mRadius;  //半径
    private Bitmap mNum6VBitmap,mNumPointer;
    private Rect mSrcRect,mDstRect;
    private Matrix mPointMatrix;
    private float mNum6VData = 0; //数据
    private float mDegrees = 0; //指针角度
    private float mScale = 0.7f;  //缩放比例
    private float mFirstPoint = 0, mSecondPoint = 6.6f, mThirdPoint = 6.9f, mFourthPoint = 13.2f, mFifthPoint = 13.8f, mSixthPoint = 26.4f, mSevenPoint = 27.6f, mMaxPoint = 32f;  //每个区间的关键点
    private int  mFristMaxDegrees = 290,mSecondMaxDegrees = 320,mThirdMaxDegrees = 350,mFourthMaxDegrees = 20,mFifthMaxDegrees = 50,mSixthMaxDegrees = 80,mSevenMaxDegrees = 90;  //每个区间点的最大角度
    private float mFristDegrees  = 20,mSecondDegrees= 30,mThirdDegrees = 30,mFourthDegrees = 30,mFifthDegrees = 30,mSixthDegrees = 30, mSevenDegrees = 10;  //每个区间的角度范围
    private int mViewBackGround;

    public DisPlayCircleView(Context context) {
        super(context);
        init();
    }

    public DisPlayCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DisPlayCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setViewBackGround(int viewId){
        Log.i("进来了", "setViewBackGround");
        mViewBackGround = viewId;
        mNum6VBitmap= BitmapFactory.decodeResource(getResources(), mViewBackGround);
    }

    //初始化
    private void init() {
        Log.i("进来了","init");
        mLeftPadding = PixelUtil.dp2px(10, getContext());
        mTopPadding = PixelUtil.dp2px(10, getContext());
        mRightPadding = PixelUtil.dp2px(10, getContext());
        mBottomPadding = PixelUtil.dp2px(10, getContext());
        //mRadius = PixelUtil.dp2px(150, getContext());
        mNumPointer = BitmapFactory.decodeResource(getResources(), R.mipmap.img_num_pointer);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("进来了","onDraw");
        super.onDraw(canvas);
        drawBitMapBackGround(canvas);
        drawBitMapPointer(canvas);
    }

    //描绘指针
    private void drawBitMapPointer(Canvas canvas) {
        canvas.drawBitmap(mNumPointer, mPointMatrix, null);
    }


    //拿到界面传给我们的值
    public void getNum6VData(float data) {
        this.mNum6VData = data;
    }

    //描绘背景图
    private void drawBitMapBackGround(Canvas canvas) {
        if(mNum6VBitmap!=null){
            canvas.drawBitmap(mNum6VBitmap, mSrcRect,mDstRect, null);
        }
    }

    //把用户给的值换算成角度
    private float getDegrees(float mNum6VData) {
        if(mNum6VData>=mFirstPoint &&mNum6VData<=mSecondPoint){
            return mFristMaxDegrees - ((mSecondPoint-mNum6VData)/mSecondPoint)*mFristDegrees;
        }
        else if(mNum6VData>mSecondPoint &&mNum6VData<=mThirdPoint){
            return mSecondMaxDegrees - ((mThirdPoint-mNum6VData)/(mThirdPoint-mSecondPoint))*mSecondDegrees;
        }
        else if(mNum6VData>mThirdPoint &&mNum6VData<=mFourthPoint){
            return mThirdMaxDegrees - ((mFourthPoint-mNum6VData)/(mFourthPoint-mThirdPoint))*mThirdDegrees;
        }
        else if(mNum6VData>mFourthPoint &&mNum6VData<=mFifthPoint){
            float flagNum = mFourthMaxDegrees - ((mFifthPoint-mNum6VData)/(mFifthPoint-mFourthPoint))*mFourthDegrees;
            //如果是负数，换算成对应的正数
            if(flagNum<0){
                flagNum = 360+flagNum;
            }
            return flagNum;
        }
        else if(mNum6VData>mFifthPoint &&mNum6VData<=mSixthPoint){
            return mFifthMaxDegrees - ((mSixthPoint-mNum6VData)/(mSixthPoint-mFifthPoint))*mFifthDegrees;
        }
        else if(mNum6VData>mSixthPoint &&mNum6VData<=mSevenPoint){
            return mSixthMaxDegrees - ((mSevenPoint-mNum6VData)/(mSevenPoint-mSixthPoint))*mSixthDegrees;
        }
        else if(mNum6VData>mSevenPoint &&mNum6VData<=mMaxPoint){
            return mSevenMaxDegrees - ((mMaxPoint-mNum6VData)/(mMaxPoint-mSevenPoint))*mSevenDegrees
                    ;
        }
        return 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i("进来了", "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        mRectFWidth = w ;
        mRectFHeight = h;
        mRadius = mRectFWidth/2;
        if(mNum6VBitmap!=null){
            mSrcRect = new Rect(0, 0,mNum6VBitmap.getWidth(), mNum6VBitmap.getHeight());// BitMap源区域
        }

        mDstRect = new Rect((int)(mRectFWidth/2-mRadius),(int) (mRectFHeight/2-mRadius),(int)(mRectFWidth/2+mRadius), (int)(mRectFHeight/2+mRadius));// BitMap目标区域
        mPointMatrix = new Matrix();
        mDegrees= getDegrees(mNum6VData);
        mPointMatrix.postRotate(mDegrees, mNumPointer.getWidth() / 2, mNumPointer.getHeight() * 12 / 13);
        //mPointMatrix.postScale(mScale,mScale);
        mPointMatrix.postTranslate(mRectFWidth/2-mNumPointer.getWidth()/2,getHeight()/2 - mNumPointer.getHeight()*12/13);
    }

}

