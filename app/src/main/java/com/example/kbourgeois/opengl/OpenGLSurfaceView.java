package com.example.kbourgeois.opengl;

import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import com.example.kbourgeois.opengl.openGLAdapter.Camera;

class OpenGLSurfaceView extends GLSurfaceView {
    private final Camera camera;
    private final Game game;
    private final float TOUCH_SCALE_FACTOR = 0.0005f;
    private float mPreviousX;
    private float mPreviousY;

    OpenGLSurfaceView(OpenGLActivity mainActivity) {
        super(mainActivity);

        Log.d("Debug : ", "MyGLSurfaceView");
        setEGLContextClientVersion(2);
        Log.d("Debug : ", "Setting OpenGLES version");
        game = new Game(mainActivity);
        camera = new Camera(mainActivity, game);
        Log.d("Debug : ", "Initialize renderer");
        setRenderer(camera);
        Log.d("Debug : ", "Set renderer");
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        Log.d("Debug : ", "Set render mode");
    }

    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                game.moveShip(dx * TOUCH_SCALE_FACTOR, -dy * TOUCH_SCALE_FACTOR, 0);

                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;

        return true;
    }


}
