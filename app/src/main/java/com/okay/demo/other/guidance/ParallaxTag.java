package com.okay.demo.other.guidance;

/**
 * Created by hcDarren on 2017/8/12.
 */

public class ParallaxTag {
    public float translationXIn;
    public float translationXOut;
    public float translationYIn;
    public float translationYOut;

    @Override
    public String toString() {
        return "translationXIn->"+translationXIn+" translationXOut->"+translationXOut
                +" translationYIn->"+translationYIn+" translationYOut->"+translationYOut;
    }
}
