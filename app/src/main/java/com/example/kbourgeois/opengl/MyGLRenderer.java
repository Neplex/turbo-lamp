package com.example.kbourgeois.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {


    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];

    private Context mContext;

    public MyGLRenderer(Context context) {
        super();
        mContext = context;
        Log.d("Debug : ", "MyGLRenderer");
    }

    Model3D ship;
    Model3D cube;

    public void translate(float dx, float dy, float dz) {
        Matrix.translateM(mModelMatrix, 0, dx, dy, dz);
    }

    public void rotateX(float angle) {
        Matrix.rotateM(mModelMatrix, 0, angle, 1, 0, 0);
    }

    public void rotateY(float angle) {
        Matrix.rotateM(mModelMatrix, 0, angle, 0, 1, 0);
    }

    public void rotateZ(float angle) {
        Matrix.rotateM(mModelMatrix, 0, angle, 0, 0, 1);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        Log.d("Debug : ", "onSurfaceCreated");
        GLES20.glClearColor(0.0f, 0.0f, 0.5f, 1.0f);
        GLES20.glClearDepthf(1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);

        Matrix.setLookAtM(mViewMatrix, 0, 0, 1, 2, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.perspectiveM(mProjectionMatrix, 0, 70.0f, 9.0f / 16.0f, 0.1f, 1000.0f);
        Matrix.setIdentityM(mModelMatrix, 0);

        ship = ModelLoader.readOBJFile(mContext, "spaceship.obj");
        ship.init("vertexshader.vert", "fragmentshader.frag",
                "vPosition", "vNormal", "vTexCoord", R.drawable.brick);

        translate(0, -15, -40);
        cube = ModelLoader.readOBJFile(mContext, "cube.obj");
        cube.init("vertexshader.vert", "fragmentshader.frag",
                "vPosition", "vNormal", "vTexCoord", R.drawable.no_texture);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d("Debug", "onSurfaceChanged");
        GLES20.glViewport(0, 0, width, height);
        Matrix.perspectiveM(mProjectionMatrix, 0, 70.0f, (float) width / (float) height, 0.1f, 100.0f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.d("Debug", "onDrawFrame");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        //ship.draw(mProjectionMatrix, mViewMatrix, mModelMatrix);

        float[] tmp = mModelMatrix.clone();
        Matrix.translateM(tmp, 0, 0, 0, 10);
        cube.draw(mProjectionMatrix, mViewMatrix, tmp);
    }


}