package com.example.kbourgeois.opengl.openGLAdapter;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Camera {

    // Options
    private float near;
    private float far;
    private float fov;
    private float aspect;
    private float[] bg_color = {0, 0, 1};

    // OpenGL MVP
    private float[] view;
    private float[] projection;

    public Camera() {
        this(.1f, 100, 70);
    }

    public Camera(float near, float far, float fov) {
        this.view = new float[16];
        this.projection = new float[16];

        this.near = near;
        this.far = far;
        this.fov = fov;
        this.aspect = 9.0f / 16.0f;
    }

    float[] getView() {
        return view;
    }

    float[] getProjection() {
        return projection;
    }

    void init() {
        GLES20.glClearColor(bg_color[0], bg_color[1], bg_color[2], 1.0f);
        GLES20.glClearDepthf(1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        this.updateMatrix();
    }

    private void updateMatrix() {
        Matrix.setLookAtM(view, 0, 0, 1, 2, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.perspectiveM(projection, 0, fov, aspect, near, far);
    }

    public float getNear() {
        return near;
    }
    public void setNear(float near) {
        this.near = near;
        this.updateMatrix();
    }

    public float getFar() {
        return far;
    }
    public void setFar(float far) {
        this.far = far;
        this.updateMatrix();
    }

    public float getFov() {
        return fov;
    }
    public void setFov(float fov) {
        this.fov = fov;
        this.updateMatrix();
    }

    public float getAspect() {
        return aspect;
    }
    public void setAspect(float aspect) {
        this.aspect = aspect;
        this.updateMatrix();
    }

}
