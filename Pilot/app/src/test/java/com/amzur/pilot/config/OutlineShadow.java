package com.amzur.pilot.config;

import android.graphics.Path;
import android.graphics.Rect;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

@Implements(android.graphics.Outline.class)
public class OutlineShadow {

    @RealObject
    private android.graphics.Outline outline;

    public Path mPath;

    /** @hide */
    public Rect mRect;
    /** @hide */
    public float mRadius;
    /** @hide */
    public float mAlpha;

    /**
     * Sets the Constructs an Outline from a {@link Path#isConvex() convex path}.
     */
    @Implementation
    public void setConvexPath(Path convexPath) {
        if (mPath == null) mPath = new Path();
        mRect = null;
        mRadius = -1.0f;
        mPath.set(convexPath);
    }
}