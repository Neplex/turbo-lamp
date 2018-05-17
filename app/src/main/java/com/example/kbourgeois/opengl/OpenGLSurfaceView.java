package com.example.kbourgeois.opengl;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import com.example.kbourgeois.opengl.openGLAdapter.Camera;
import com.example.kbourgeois.opengl.openGLAdapter.GameRenderer;
import com.example.kbourgeois.opengl.openGLAdapter.Scene;

class OpenGLSurfaceView extends GLSurfaceView {
    private final Camera camera;
    private final Scene scene;
    private final GameRenderer gameRenderer;
    private final Game game;

    private final float TOUCH_SCALE_FACTOR = 0.0005f;
    private float mPreviousX;
    private float mPreviousY;

    OpenGLSurfaceView(OpenGLActivity mainActivity) {
        super(mainActivity);

        setEGLContextClientVersion(2);

        camera = new Camera();
        scene = new Scene();
        game = new Game(mainActivity, scene);
        gameRenderer = new GameRenderer(mainActivity, camera, scene, game);

        setRenderer(gameRenderer);

        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
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
