package retrofit.jackwaiting.jackwaiting_chars.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import retrofit.jackwaiting.jackwaiting_chars.R;
import retrofit.jackwaiting.jackwaiting_chars.util.PixelUtil;

/**
 * Created by JackWaiting on 2016/6/30.
 */
public class RuleCircleView extends View {

    private float mLeftPadding,mRightPadding,mTopPadding,mBottomPadding;  //上下左右边距
    private float mRectFWidth,mRectFHeight;  //控件高与宽
    private float mRadius;  //半径
    private Bitmap mNum6VBitmap,mNumPointer;
    private Rect mSrcRect,mDstRect;
    private Matrix mPointMatrix;
    private float mNum6VData = 0; //数据
    private float mDegrees = 0; //指针角度
    private float mScale = 0.7f;  //缩放比例

    public RuleCircleView(Context context) {
        super(context);
        init();
    }

    public RuleCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RuleCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化
    private void init() {
        mLeftPadding = PixelUtil.dp2px(10, getContext());
        mTopPadding = PixelUtil.dp2px(10, getContext());
        mRightPadding = PixelUtil.dp2px(10, getContext());
        mBottomPadding = PixelUtil.dp2px(10, getContext());
        mRadius = PixelUtil.dp2px(150, getContext());
        mNum6VBitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.img_num_6v);
        mNumPointer = BitmapFactory.decodeResource(getResources(), R.mipmap.img_num_pointer);
    }

    @Override
    protected void onDraw(Canvas canvas) {
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
        canvas.drawBitmap(mNum6VBitmap, mSrcRect,mDstRect, null);
    }

    private float mFirstPoint = 0, mSecondPoint = 5.97f, mThirdPoint = 6.18f, mFourthPoint = 6.43f, mFifthPoint = 6.94f, mSixthPoint = 7.45f, mMaxPoint = 10;
    //把用户给的值换算成角度
    private float getDegrees(float mNum6VData) {
        if(mNum6VData>=mFirstPoint &&mNum6VData<=mSecondPoint){
            return 270 - ((mSecondPoint-mNum6VData)/mSecondPoint)*45f;
        }
        else if(mNum6VData>mSecondPoint &&mNum6VData<=mThirdPoint){
            return 300 - ((mThirdPoint-mNum6VData)/(mThirdPoint-mSecondPoint))*30f;
        }
        else if(mNum6VData>mThirdPoint &&mNum6VData<=mFourthPoint){
            return 330 - ((mFourthPoint-mNum6VData)/(mFourthPoint-mThirdPoint))*30f;
        }
        else if(mNum6VData>mFourthPoint &&mNum6VData<=mFifthPoint){
            float flagNum = 30 - ((mFifthPoint-mNum6VData)/(mFifthPoint-mFourthPoint))*60f;
            //如果是负数，换算成对应的正数
            if(flagNum<0){
                flagNum = 360+flagNum;
            }
            return flagNum;
        }
        else if(mNum6VData>mFifthPoint &&mNum6VData<=mSixthPoint){
            return 90 - ((mSixthPoint-mNum6VData)/(mSixthPoint-mFifthPoint))*60f;
        }
        else if(mNum6VData>mSixthPoint &&mNum6VData<=mMaxPoint){
            return 135 - ((mMaxPoint-mNum6VData)/(mMaxPoint-mSixthPoint))*45f;
        }
        return 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectFWidth = w - mLeftPadding - mRightPadding;
        mRectFHeight = h  - mTopPadding - mBottomPadding;
        mSrcRect = new Rect(0, 0,mNum6VBitmap.getWidth(), mNum6VBitmap.getHeight());// BitMap源区域
        mDstRect = new Rect((int)(mRectFWidth/2-mRadius),(int) (mRectFHeight/2-mRadius),(int)(mRectFWidth/2+mRadius), (int)(mRectFHeight/2+mRadius));// BitMap目标区域
        mPointMatrix = new Matrix();
        mDegrees= getDegrees(mNum6VData);
        mPointMatrix.postRotate(mDegrees, mNumPointer.getWidth()/2,mNumPointer.getHeight()*12/13);
        mPointMatrix.postScale(mScale,mScale);
        mPointMatrix.postTranslate(mRectFWidth/2-mNumPointer.getWidth()/2*mScale,getHeight()/2 - mNumPointer.getHeight()*mScale);
    }


}
