# CharView

#V1.04
Describe：Add RuleCircleView into the ChartView project source code.
![](https://github.com/JackWaiting/ChartView/blob/master/images/chart_rule_circle_img.png)
View Code:
```
**
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
    private float mFirstPoint = 0, mSecondPoint = 5.97f, mThirdPoint = 6.18f, mFourthPoint = 6.43f, mFifthPoint = 6.94f, mSixthPoint = 7.45f, mMaxPoint = 10;  //每个区间的关键点
    private int  mFristMaxDegrees = 270,mSecondMaxDegrees = 300,mThirdMaxDegrees = 330,mFourthMaxDegrees = 30,mFifthMaxDegrees = 90,mSixthMaxDegrees = 135;  //每个区间点的最大角度
    private float mFristDegrees  = 45,mSecondDegrees= 30,mThirdDegrees = 30,mFourthDegrees = 60,mFifthDegrees = 60,mSixthDegrees = 45;  //每个区间的角度范围

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
        else if(mNum6VData>mSixthPoint &&mNum6VData<=mMaxPoint){
            return mSixthMaxDegrees - ((mMaxPoint-mNum6VData)/(mMaxPoint-mSixthPoint))*mSixthDegrees;
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
```

#V1.03
Describe：Add screen adaptation and image rendering capabilities
![](https://github.com/JackWaiting/ChartView/blob/master/images/chart_image_rendering.png)



Add Important View Code：
```
    // 绘制渲染后的曲线图
    private void drawBeautifulCurve(Canvas canvas) {
        if (mCurveBitmap == null) {
            mCurveBitmap = getBeautfulCurve();
        }
        canvas.drawBitmap(mCurveBitmap, 0, 0, null);
    }

    private Rect mColorBgRect;
    private Paint mColorBgPaint;
    private Bitmap getBeautfulCurve() {
        // 绘制一个渲染的背景
        Bitmap tagBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas tagCanvas = new Canvas(tagBitmap);
        tagCanvas.drawRect(mColorBgRect, mColorBgPaint);
       // 绘制弧形
        Bitmap curveBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas curveCanvas = new Canvas(curveBitmap);
        drawCurve(curveCanvas);
        Paint paint = new Paint();
        // 设置合成模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        tagCanvas.drawBitmap(curveBitmap, mMatrix, paint);
        return tagBitmap;
    }

    // 渲染背景笔
    public void shaderColorBgPaint(Rect rect) {
        LinearGradient linearGradient = new LinearGradient(rect.left, rect.top, rect.left, rect.bottom, getShaderColor(), getShaderPosition(), Shader.TileMode.MIRROR);
        mColorBgPaint.setShader(linearGradient);
    }

    // 将正常理解的颜色@COLORS_SHADER转换为LinearGradient绘制所需的颜色
    public int[] getShaderColor(){
        int[] colors = new int[COLORS_SHADER.length * 2];
        for (int i = 0, len = colors.length; i < len ; i+=2) {
            colors[i] = COLORS_SHADER[i/2];
            colors[i+1] = COLORS_SHADER[i/2];
        }
        return colors;
    }

    // 将正常理解的比例@RATIOS_SHADER转换为LinearGradient绘制所需的比例
    public float[] getShaderPosition() {
        float[] position = new float[COLORS_SHADER.length * 2];
        position[0] = JOIN_SHADER;
        position[1] = RATIOS_SHADER[0] - JOIN_SHADER;
        for (int i = 1, len = RATIOS_SHADER.length; i < len ; i++) {
            position[i*2] = RATIOS_SHADER[i-1] + JOIN_SHADER;
            position[i*2+1] = RATIOS_SHADER[i] - JOIN_SHADER;
        }
        return position;
    }

```

#V1.02
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


#V1.01
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


