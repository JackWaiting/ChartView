# CharView

First look at the effect：
![](https://github.com/JackWaiting/CharView/blob/master/images/char_jackwaiting.png)  

View Code:
```
/**
 * Created by JackWaiting on 2016/6/24.
 */
public class CharView extends View {

    private  Paint mRowLinePaint,mColLinePaint;  //横线、竖线的画笔
    private Paint redPaint,greenPaint,yellowPaint;
    private Paint mColTextPaint;  //横坐标画笔
    private int mTopPadding, mLeftPadding, mRightPadding, mBottomPadding;  //图标距离上下左右的边距
    private int mFontHeight, mFontTopMargin;  //字体的高度与字体上方的边距
    private int mRowNumber = 7, mColNumber = 30;  //行与列
    private int[] mRowYs,mColYs,mColTextYs;  //装动态行列坐标的数组
    private int mRowHeight, mColWidth;  //行高与列高
    private int mRowLineWidth , mColLineWidth ; //行线与列线的宽度
    private int mRowStartX, mRowEndX,mColStartY,mColEndY;
    private int mColTextWidth,mColTextHeight;
    private float[] lifeTimeData = new float[mColNumber+1];  //每月最多31个值
    private int mLifeTimeWidth;
    private float lifeTimeHeight;  //由于高度的精确度比较高，建议使用float
    private float mLifeTimeStartX[],mLifeTimeEndX[],mLifeTimeStartY[],mLifeTimeEndY[];
    private float mMaxLifeTimeData = 8f,mOneRowTip = 7.45f,mTwoRowTip = 6.34f,mThreeRowTip = 6.18f,mFourRowTip = 5.97f;
    private float mRowTipData[] = new float[]{0f,7.45f,0f,0f,6.34f,6.18f,5.97f,0f}; //不显示则用0f表示
    private String[] chars = new String[]{"0","5","10","15","20","25","30"};
    private  Path path;
    private int mCircleRadius = 20;
    private PathEffect effects;
    private int mRowTipWidth =150,mRowTipHeight = 60;

    public CharView(Context context) {
        super(context);
        init(context,null,0);
    }

    public CharView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,null,0);
    }

    public CharView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    public void getLifeTimeData(float [] data){
        this.lifeTimeData = data;
    }

    private void init(Context context,AttributeSet attrs,int defStyle) {
        mTopPadding =  PixelUtil.dp2px(50, getContext());
        mRowLineWidth = PixelUtil.dp2px(1, getContext());
        mColLineWidth = PixelUtil.dp2px(1, getContext());
        mLeftPadding = PixelUtil.dp2px(20, getContext());
        mRightPadding = PixelUtil.dp2px(20, getContext());
        mBottomPadding = PixelUtil.dp2px(55, getContext());
        mFontHeight = PixelUtil.dp2px(10, getContext());
        mFontTopMargin = PixelUtil.dp2px(15, getContext());
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyCharView);
        effects= new DashPathEffect(new float[]{5,5,5,5},10);
        path = new Path();
        mRowLinePaint = new Paint();
        mRowLinePaint.setColor(a.getColor(R.styleable.MyCharView_cv_row,getResources().getColor(R.color.row)));
        mRowLinePaint.setStrokeWidth(mRowLineWidth);
        mColLinePaint = new Paint();
        mColLinePaint.setColor(a.getColor(R.styleable.MyCharView_cv_col,getResources().getColor(R.color.col)));
        mColLinePaint.setStrokeWidth(mColLineWidth);
        mColLinePaint.setStyle(Paint.Style. STROKE);
        mColLinePaint.setAntiAlias( true);
        redPaint = new Paint();
        redPaint.setColor(a.getColor(R.styleable.MyCharView_cv_red,getResources().getColor(R.color.red)));
        redPaint.setStrokeWidth(PixelUtil.dp2px(2, getContext()));
        greenPaint = new Paint();
        greenPaint.setColor(a.getColor(R.styleable.MyCharView_cv_green,getResources().getColor(R.color.green)));
        greenPaint.setStrokeWidth(PixelUtil.dp2px(2, getContext()));
        yellowPaint = new Paint();
        yellowPaint.setColor(a.getColor(R.styleable.MyCharView_cv_row,getResources().getColor(R.color.yellow)));
        yellowPaint.setStrokeWidth(PixelUtil.dp2px(2, getContext()));

        mColTextPaint = new Paint();
        mColTextPaint.setColor(a.getColor(R.styleable.MyCharView_cv_row,Color.WHITE));
        mColTextPaint.setTextSize(50);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i= 0;i<mColYs.length;i++){
            if(i%5==0){
                canvas.drawLine(mColYs[i], mColStartY, mColYs[i],mColEndY, mRowLinePaint);
                canvas.drawText(chars[i/5]+"",mColTextYs[i/5]-(mColWidth/2), mColTextHeight,mColTextPaint);
            }
            else{
                //canvas.drawLine(mColYs[i], mColStartY, mColYs[i],mColEndY, mColLinePaint);
                path.moveTo(mColYs[i], mColStartY);
                path.lineTo(mColYs[i],mColEndY);
                mColLinePaint.setPathEffect(effects);
                canvas.drawPath(path, mColLinePaint);
            }
        }

        for (int i= 0;i<mRowYs.length;i++){
            canvas.drawLine(mRowStartX, mRowYs[i], mRowEndX, mRowYs[i], mRowLinePaint);
        }

        for (int i= 0;i<lifeTimeData.length-1;i++){
            if(lifeTimeData[i] >mOneRowTip){
                canvas.drawLine(mLifeTimeStartX[i], mLifeTimeStartY[i], mLifeTimeEndX[i], mLifeTimeEndY[i], redPaint);
                drawCircleView(i,redPaint,canvas);
            }
            else if(lifeTimeData[i] >mTwoRowTip && lifeTimeData[i] <= mOneRowTip){
                canvas.drawLine(mLifeTimeStartX[i], mLifeTimeStartY[i], mLifeTimeEndX[i], mLifeTimeEndY[i], greenPaint);
                drawCircleView(i,greenPaint,canvas);
            }
            else if(lifeTimeData[i]  >mThreeRowTip && lifeTimeData[i] < mTwoRowTip){
                canvas.drawLine(mLifeTimeStartX[i], mLifeTimeStartY[i], mLifeTimeEndX[i], mLifeTimeEndY[i], greenPaint);
                drawCircleView(i,greenPaint,canvas);
            }
            else if(lifeTimeData[i]  >mFourRowTip && lifeTimeData[i] <= mThreeRowTip){
                canvas.drawLine(mLifeTimeStartX[i], mLifeTimeStartY[i], mLifeTimeEndX[i], mLifeTimeEndY[i], yellowPaint);
                drawCircleView(i,yellowPaint,canvas);
            }
            else if(lifeTimeData[i]  >=0 && lifeTimeData[i] <= mFourRowTip){
                canvas.drawLine(mLifeTimeStartX[i], mLifeTimeStartY[i], mLifeTimeEndX[i], mLifeTimeEndY[i], redPaint);
                drawCircleView(i,redPaint,canvas);
            }
            else{
                canvas.drawLine(mLifeTimeStartX[i], mLifeTimeStartY[i], mLifeTimeEndX[i], mLifeTimeEndY[i], redPaint);
            }
        }
        //最后一点无法循环到，手动赋值
        canvas.drawCircle(mLifeTimeStartX[30],mLifeTimeStartY[30], mCircleRadius,redPaint);
        for(int i=0;i< mRowYs.length;i++){

            if(i==0 ||i==2 ||i == 3 ||i == 7){
                continue;
            }
            else{
                canvas.drawRect(0, mRowYs[i]-mRowTipHeight/2,mRowTipWidth, mRowYs[i]+mRowTipHeight/2, mRowLinePaint);  // 矩形
                canvas.drawText(mRowTipData[i]+"",20, mRowYs[i]+20,mColTextPaint);  //给每个矩形进行参数赋值
            }

        }
    }


    //每隔5个点画圆
    private void  drawCircleView(int i,Paint paint,Canvas canvas){
        if(i%5==0){
            canvas.drawCircle(mLifeTimeStartX[i],mLifeTimeStartY[i], mCircleRadius,paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int viewWidth = w - mLeftPadding - mRightPadding;
        int viewHeight = h - mTopPadding - mBottomPadding - mFontHeight - mFontTopMargin;
        mColTextHeight = h - mTopPadding  - mFontTopMargin;
        mRowStartX = mLeftPadding - mRowLineWidth/2;
        mRowEndX = w - mRightPadding  - (mRowLineWidth*mRowNumber)+mRowLineWidth/2;
        mColStartY = mTopPadding -mColLineWidth/2;
        mColEndY  =  h-mBottomPadding - mFontHeight - mFontTopMargin - (mRowNumber+1);
        mRowYs = new int[mRowNumber + 1];
        mColYs = new int[mColNumber + 1];
        mColTextYs = new int[chars.length];
        mRowHeight = (viewHeight-(mRowNumber+1)*mRowLineWidth)/mRowNumber;
        mColWidth  = (viewWidth - (mColNumber+1)*mColLineWidth)/mColNumber;
        for (int i = 0; i < mRowYs.length ; i++){
            mRowYs[i] = mTopPadding + i * (mRowHeight+mRowLineWidth) ;
        }
        for (int i = 0; i < mColYs.length ; i++){
            mColYs[i] = mLeftPadding + i * (mColWidth+mColLineWidth);
        }
        for (int i = 0; i < mColTextYs.length ; i++){
            mColTextYs[i] = mLeftPadding + i * (mColWidth+mColLineWidth)*5;
        }

        //具体画线
        mLifeTimeStartX = new float[mColNumber+1];
        mLifeTimeStartY = new float[mColNumber+1];
        mLifeTimeEndX = new float[mColNumber+1];
        mLifeTimeEndY = new float[mColNumber+1];
        for (int i=0;i<mLifeTimeStartX.length;i++){
            mLifeTimeStartX[i] =  mLeftPadding + i * (mColWidth+mColLineWidth);
        }
        for (int i=0;i<mLifeTimeEndX.length;i++){
            mLifeTimeEndX[i] =  mLeftPadding +mColWidth+mColLineWidth+ i * (mColWidth+mColLineWidth);
        }
        getLifeTime();
    }

    //获取每个点进行连接
    private void getLifeTime() {

        //计算Y轴的坐标
        for (int i = 0;i<lifeTimeData.length;i++){
            if(lifeTimeData[i] >=mOneRowTip){
                mLifeTimeStartY[i] = mTopPadding + (((mMaxLifeTimeData-mOneRowTip)-(lifeTimeData[i]-mOneRowTip))/(mMaxLifeTimeData-mOneRowTip))*(mRowHeight+mRowLineWidth);
            }
            else if(lifeTimeData[i] >mTwoRowTip && lifeTimeData[i] < mOneRowTip){
                mLifeTimeStartY[i] = mTopPadding + mRowHeight+mRowLineWidth+ (((mOneRowTip-mTwoRowTip)-(lifeTimeData[i]-mTwoRowTip))/(mOneRowTip-mTwoRowTip))*3*(mRowHeight+mRowLineWidth);
            }
            else if(lifeTimeData[i]  >=mThreeRowTip && lifeTimeData[i] < mTwoRowTip){
                mLifeTimeStartY[i] = mTopPadding + ((mRowNumber+1)/2)*(mRowHeight+mRowLineWidth) + (((mTwoRowTip-mThreeRowTip)-(lifeTimeData[i]-mThreeRowTip))/(mTwoRowTip-mThreeRowTip))*(mRowHeight+mRowLineWidth);
            }
            else if(lifeTimeData[i]  >mFourRowTip && lifeTimeData[i] < mThreeRowTip){
                mLifeTimeStartY[i] = mTopPadding + ((mRowNumber+1)-3)*(mRowHeight+mRowLineWidth)+ (((mThreeRowTip-mFourRowTip)-(lifeTimeData[i]-mFourRowTip))/(mThreeRowTip-mFourRowTip))*(mRowHeight+mRowLineWidth);
            }
            else if(lifeTimeData[i]  >=0 && lifeTimeData[i] <= mFourRowTip){
                mLifeTimeStartY[i] = mTopPadding + ((mRowNumber+1)-2)*(mRowHeight+mRowLineWidth)+ ((mFourRowTip-lifeTimeData[i])/mFourRowTip)*(mRowHeight+mRowLineWidth);
            }
            else{
                mLifeTimeStartY[i] = mTopPadding + mRowNumber * ( mRowHeight + mRowLineWidth );
            }
        }
        //把Y轴连在一起
        for (int i=1;i<mLifeTimeStartY.length;i++){
            mLifeTimeEndY[i-1] = mLifeTimeStartY[i];
        }
        //mLifeTimeEndY[mLifeTimeStartX.length-1 ] = mLifeTimeStartY[mLifeTimeStartX.length-1];
    }
}
```

#Update
Describe：Add onTouch event, optimize the onDraw code.

Design images ：
![](https://github.com/JackWaiting/ChartView/blob/master/images/chart_onTouch.png)  

View Code:
```
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCharts(canvas);
        if(mSpot >= 0 && mSpot <= 30){
             if(mLifeTimeStartY[mSpot] >=mTopPadding +mRowHeight+mRowLineWidth &&mLifeTimeStartY[mSpot] <=mTopPadding +5*(mRowHeight+mRowLineWidth)){
                drawTouchData(canvas,greenPaint);
            }
            else if(mLifeTimeStartY[mSpot] >=mTopPadding +5*(mRowHeight+mRowLineWidth) &&mLifeTimeStartY[mSpot] <=mTopPadding +6*(mRowHeight+mRowLineWidth)){
                drawTouchData(canvas,yellowPaint);
            }
            else{
                drawTouchData(canvas,redPaint);
             }
        }

    }
    
        @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(ignoreTouch(event.getX(),event.getY())){
                    mSpot = getTouchSpot(event.getX());
                    Log.i("进来了","ACTION_DOWN已处理"+mSpot);
//                    setPressed(true);
                   invalidate();
                    return true;
                }
                else{
                    mSpot =-1;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
//                setPressed(false);
                break;
            case MotionEvent.ACTION_CANCEL:
//                setPressed(false);

                break;
        }

        return super.onTouchEvent(event);
    }


    //根据点击的范围获取到当前点击的点
    private int  getTouchSpot(float x) {
        int index = 0;
        for (int i = 0;i<mLifeTimeStartX.length;i++){
            if(x>mLifeTimeStartX[i]-mColWidth/2 && x<mLifeTimeEndX[i] -mColWidth/2){
                index = i;
            }
        }
        return index;
    }


    //判断当前点击的范围是否需要处理
    private boolean ignoreTouch(float x , float y) {
        boolean ignore = false;
        if((x>=(mLeftPadding-mColWidth/2) &&x <=(mRowEndX+mColWidth/2))&&(y>=mTopPadding-mColWidth/2 && y<=mColEndY+mColWidth/2) ){
            ignore = true;
        }
        return  ignore;
    }


    //点击Touch后给出每个点的具体数据
    private void drawTouchData(Canvas canvas,Paint paint) {
        canvas.drawRect(mLifeTimeStartX[mSpot]-mRowTipWidth/2, mLifeTimeStartY[mSpot]-100-mRowTipHeight/2,mLifeTimeStartX[mSpot]+mRowTipWidth/2, mLifeTimeStartY[mSpot]+mRowTipHeight/2-100, paint);  // 矩形
        canvas.drawText(lifeTimeData[mSpot]+"",mLifeTimeStartX[mSpot]-mRowTipWidth/2+20, mLifeTimeStartY[mSpot]-80,mColTextPaint);  //查看任意一点的信息
    }


```
