package com.example.kbourgeois.opengl.openGLAdapter;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GameRenderer implements GLSurfaceView.Renderer {

    private Context context;
    private Camera camera;
    private Scene scene;
    private Controller controller;
    private long last_time;

    public GameRenderer(Context context, Camera camera, Scene scene, Controller controller) {
        super();

        this.context = context;
        this.camera = camera;
        this.scene = scene;
        this.controller = controller;
        this.last_time = System.nanoTime();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        camera.init();
        controller.start();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        camera.setAspect((float) width / (float) height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        long time = System.nanoTime();
        int delta_time = (int) ((time - last_time) / 1000000);
        last_time = time;

        // Update scene
        controller.update(delta_time);

        // Draw object
        for (Drawable obj : scene.getObjects()) {
            obj.draw(camera, scene.getTransform());
        }
    }
}

