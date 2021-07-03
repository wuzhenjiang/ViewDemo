package com.okay.demo.other.praise

import android.animation.TypeEvaluator
import android.graphics.PointF
import android.graphics.RectF
import kotlin.math.pow

/**
 *@author wzj
 *@date 4/5/21 7:21 PM
 * 曲线的参数形式为：
 */
class PraiseTypeEvaluator : TypeEvaluator<PointF> {
    private lateinit var mPoint1: PointF
    private lateinit var mPoint2: PointF
    private var rectF = PointF()
    constructor(point1: PointF, point2: PointF) {
        mPoint1 = point1
        mPoint2 = point2
    }


    override fun evaluate(fraction: Float, point0: PointF, point3: PointF): PointF {
        rectF.x = (point0.x* (1.0 - fraction).pow(3) +
                3*mPoint1.x*fraction*(1-fraction).pow(2)+
                3*mPoint2.x*fraction.pow(2)*(1-fraction)+
                point3.x*fraction.pow(3)).toFloat()

        rectF.y = (point0.y* (1.0 - fraction).pow(3) +
                3*mPoint1.y*fraction*(1-fraction).pow(2)+
                3*mPoint2.y*fraction.pow(2)*(1-fraction)+
                point3.y*fraction.pow(3)).toFloat()
        return rectF
    }
}