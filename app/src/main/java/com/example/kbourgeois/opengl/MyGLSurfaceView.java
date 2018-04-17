package com.example.kbourgeois.opengl;

import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer myRenderer;
    private final float TOUCH_SCALE_FACTOR = 0.00001f;
    private float mPreviousX;
    private float mPreviousY;

    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;
                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1;
                }

                //myRenderer.translate(dx / 10000.0f, dy / 10000.0f, 0);
                System.out.println(dx + " " + dy);
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;

        return true;
    }

    public MyGLSurfaceView(OpenGLActivity mainActivity) {
        super(mainActivity);

        Log.d("Debug : ", "MyGLSurfaceView");
        setEGLContextClientVersion(2);
        Log.d("Debug : ", "Setting OpenGLES version");
        myRenderer = new MyGLRenderer(mainActivity);
        Log.d("Debug : ", "Initialize renderer");
        setRenderer(myRenderer);
        Log.d("Debug : ", "Set renderer");
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        Log.d("Debug : ", "Set render mode");
    }


}
