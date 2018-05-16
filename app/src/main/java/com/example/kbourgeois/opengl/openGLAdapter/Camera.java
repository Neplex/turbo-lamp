package com.example.kbourgeois.opengl.openGLAdapter;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import com.example.kbourgeois.opengl.Game;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;
import java.util.List;

public class Camera implements GLSurfaceView.Renderer {

    // Options
    private final static float near = .1f;
    private final static float far = 1000;
    private final static float fov = 70;
    private final static float[] bg_color = {0, 0, 1};
    // Android context
    private Context context;
    private Game game;
    private long last_time;
    // Objects in scene
    private List<Drawable> objects;
    // OpenGL MVP
    private Transform model;
    private float[] view;
    private float[] projection;

    public Camera(Context context, Game game) {
        super();

        this.context = context;

        this.game = game;
        this.last_time = System.nanoTime();

        this.objects = new ArrayList<>();

        this.model = new Transform();
        this.view = new float[16];
        this.projection = new float[16];
    }

    public Transform getModel() {
        return model;
    }

    float[] getView() {
        return view;
    }

    float[] getProjection() {
        return projection;
    }

    /**
     * Add object to scene
     *
     * @param object
     */
    public void addObject(Drawable object) {
        objects.add(object);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(bg_color[0], bg_color[1], bg_color[2], 1.0f);
        GLES20.glClearDepthf(1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);

        Matrix.setLookAtM(view, 0, 0, 1, 2, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.perspectiveM(projection, 0, fov, 9.0f / 16.0f, near, far);

        game.init(this);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        Matrix.perspectiveM(projection, 0, fov, (float) width / (float) height, near, far);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        long time = System.nanoTime();
        int delta_time = (int) ((time - last_time) / 1000000);
        last_time = time;

        game.loop(delta_time);

        for (Drawable obj : objects) {
            obj.draw(this, model);
        }
    }
}

