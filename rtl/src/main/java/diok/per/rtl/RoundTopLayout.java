package diok.per.rtl;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


public class RoundTopLayout extends RelativeLayout {

    private float GapSize = 30;//弧顶和底线间的高度
    private float MidWidth = 75;// 所在矩形宽
    private float Paint_Diameter = 2;//线宽
    private int LineColor = Color.BLACK;//线颜色
    private int BackColor = Color.WHITE;//填充颜色

    private Paint paint;//线画笔
    private Paint innerPaint;//填充画笔
    private int height;//控件高
    private int width;//控件宽

    private RectF rectF;//凸起边缘容器
    private RectF innerRectF;//凸起填充容器
    private RectF backRectF;//横线下方填充容器

    private int startAngle = 180;//弧线的起始角度
    private int sweepAngle = 180;//弧线烧过角度

    public RoundTopLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RoundTopLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public RoundTopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundTopLayout, defStyle, 0);
            GapSize = a.getDimension(R.styleable.RoundTopLayout_gapsize, GapSize);
            MidWidth = a.getDimension(R.styleable.RoundTopLayout_centerwidth, MidWidth);
            Paint_Diameter = a.getDimension(R.styleable.RoundTopLayout_linesize, Paint_Diameter);
            LineColor = a.getColor(R.styleable.RoundTopLayout_linecolor, LineColor);
            BackColor = a.getColor(R.styleable.RoundTopLayout_backcolor, BackColor);
            a.recycle();
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(LineColor);
        paint.setStrokeWidth(Paint_Diameter);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Cap.ROUND);

        innerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerPaint.setColor(BackColor);
        innerPaint.setStrokeWidth(Paint_Diameter * 2);
        innerPaint.setDither(true);
        innerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        innerPaint.setStrokeCap(Cap.ROUND);

        rectF = new RectF();
        innerRectF = new RectF();
        backRectF = new RectF();

        setBackgroundColor(getResources().getColor(android.R.color.transparent));//不设置背景会使画的东西消失
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画从左端开始到弧左端的直线
        canvas.drawLine(0, GapSize + Paint_Diameter, width, GapSize + Paint_Diameter, paint);
        //画填充弧线，可以避免填充覆盖弧线
        canvas.drawArc(innerRectF, startAngle, sweepAngle, false, innerPaint);
        //画弧线的时候，如果画笔的宽度比较低，那么看起来直线和弧线的宽度会不一样，需要设置画笔画完弧线后重置再继续画
        paint.setStrokeWidth(Paint_Diameter/2);
        //画外层弧线
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);
        paint.setStrokeWidth(Paint_Diameter);//重置
        //画填充，并覆盖弧线及其填充
        canvas.drawRect(backRectF, innerPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);

        rectF.set(((width - MidWidth) / 2) - Paint_Diameter, Paint_Diameter, ((width + MidWidth) / 2) + Paint_Diameter, getMeasuredHeight());
        innerRectF.set(((width - MidWidth) / 2), Paint_Diameter * 2, ((width + MidWidth) / 2), getMeasuredHeight());
        backRectF.set(0, (float) (GapSize + Paint_Diameter * 2), width, getMeasuredHeight());
    }
}
