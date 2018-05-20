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

    private float width;
    private float height;

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
        this.setSize(1920, 1080);
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
        Matrix.setLookAtM(view, 0, 0, 0, 2, 0f, 0f, 0f, 0f, 1f, 0f);
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
    void setSize(int w, int h) {
        this.width = w;
        this.height = h;
        this.aspect = this.width / this.height;
        this.updateMatrix();
    }

    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }
}
