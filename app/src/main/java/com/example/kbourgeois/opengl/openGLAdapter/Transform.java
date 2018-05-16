package com.example.kbourgeois.opengl.openGLAdapter;

import android.opengl.Matrix;

public class Transform {

    private final float[] modelMatrix;

    Transform() {
        modelMatrix = new float[16];
        Matrix.setIdentityM(modelMatrix, 0);
    }

    float[] getModelMatrix() {
        return modelMatrix;
    }

    void applyTransform(Transform transform) {
        float[] newModel = new float[modelMatrix.length];
        Matrix.multiplyMM(newModel, 0, modelMatrix, 0, transform.getModelMatrix(), 0);
        System.arraycopy(newModel, 0, modelMatrix, 0, modelMatrix.length);
    }

    public void translate(float dx, float dy, float dz) {
        Matrix.translateM(modelMatrix, 0, dx, dy, dz);
    }

    public void rotateX(float angle) {
        Matrix.rotateM(modelMatrix, 0, angle, 1, 0, 0);
    }

    public void rotateY(float angle) {
        Matrix.rotateM(modelMatrix, 0, angle, 0, 1, 0);
    }

    public void rotateZ(float angle) {
        Matrix.rotateM(modelMatrix, 0, angle, 0, 0, 1);
    }

    public void setScale(float x, float y, float z) {
        Matrix.scaleM(modelMatrix, 0, x, y, z);
    }
}
