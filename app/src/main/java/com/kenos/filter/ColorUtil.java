package com.kenos.filter;

import android.content.Context;
import android.util.Log;

/**
 * RGB色彩模式分红，绿，蓝三种，它们颜色值的范围是0-255（通俗点就是深浅程度），其它的颜色则是通过这三个基本颜色的最大值合成的。255对应16进制是FF,对应2进制是1111111，在看看代码
 * <p>
 * int red = (color & 0xff0000) >> 16;
 * <p>
 * 0xff0000的2进制是：111111110000000000000000（一共24位）
 * color & 0xff0000的意思：求和运算，得到一个000000000000000000000000-111111110000000000000000的数值，
 * >> 16的意思：右移16位，得到前8位的值，也就是00000000-11111111的数值，对应的2进制也就是0-255的值了。
 * <p>
 * green 和blue 也是上面的道理算出来的。
 * 这样就得到了rgb的三个值
 * //这句很简单，根据上面的值得到对应的颜色，然后调用setColor方法
 * paint.setColor(Color.rgb(red, green, blue));
 */
public class ColorUtil {

    private static final String TAG = "ColorUtil";

    // 成新的颜色值
    public static int getNewColorByStartEndColor(Context context, float fraction, int startValue, int endValue) {
        return evaluate(fraction, context.getResources().getColor(startValue), context.getResources().getColor(endValue));
    }

    /**
     * 成新的颜色值
     *
     * @param fraction   颜色取值的级别 (0.0f ~ 1.0f)
     * @param startValue 开始显示的颜色
     * @param endValue   结束显示的颜色
     * @return 返回生成新的颜色值
     */
    public static int evaluate(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        int result = ((startA + (int) (fraction * (endA - startA))) << 24) |
                ((startR + (int) (fraction * (endR - startR))) << 16) |
                ((startG + (int) (fraction * (endG - startG))) << 8) |
                ((startB + (int) (fraction * (endB - startB))));

        Log.i(TAG, "startA= " + startA + " startR= " + startR + " startG= " + startG + " startB= " + startB);

        Log.i(TAG, "endA= " + endA + " endR= " + endR + " endG= " + endG + " endB= " + endB + " fraction= " + fraction);

        Log.i(TAG, "startValue= " + startValue + " endValue= " + endValue + " result= " + result);

        return result;
    }

}
