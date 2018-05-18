package com.example.kbourgeois.opengl.openGLAdapter;

import android.opengl.Matrix;

public class Transform {

    private Vector3 position;
    private Vector3 rotation;
    private Vector3 scale;

    Transform() {
        this.position = new Vector3();
        this.rotation = new Vector3();
        this.scale = new Vector3();
    }

    float[] getModelMatrix() {
        float[] modelMatrix = new float[16];
        Matrix.setIdentityM(modelMatrix, 0);

        Matrix.translateM(modelMatrix, 0, position.x, position.y, position.z);
        Matrix.rotateM(modelMatrix, 0, rotation.x, 1, 0, 0);
        Matrix.rotateM(modelMatrix, 0, rotation.y, 0, 1, 0);
        Matrix.rotateM(modelMatrix, 0, rotation.z, 0, 0, 1);
        Matrix.scaleM(modelMatrix, 0, scale.x, scale.y, scale.z);

        return modelMatrix;
    }

    void applyTransform(Transform transform) {
        this.position = this.position.add(transform.getPosition());
        this.rotation = this.rotation.add(transform.getRotation());
        this.scale = this.scale.add(transform.getScale());
    }

    public void translate(Vector3 translate) {
        this.position = this.position.add(translate);
    }
    public void translateX(float x) {
        this.position.x += x;
    }
    public void translateY(float y) {
        this.position.y += y;
    }
    public void translateZ(float z) {
        this.position.z += z;
    }

    public void rotate(Vector3 rotation) {
        this.rotation = this.rotation.add(rotation);
    }
    public void rotateX(float angle) {
        this.rotation.x += angle;
    }
    public void rotateY(float angle) {
        this.rotation.y += angle;
    }
    public void rotateZ(float angle) {
        this.rotation.z += angle;
    }

    public void scale(Vector3 scale) {
        this.scale = this.scale.add(scale);
    }
    public void scaleX(float x) {
        this.scale.x = x;
    }
    public void scaleY(float y) {
        this.scale.y = y;
    }
    public void scaleZ(float z) {
        this.scale.z = z;
    }

    public Vector3 getPosition() {
        return position;
    }
    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector3 getRotation() {
        return rotation;
    }
    public void setRotation(Vector3 rotation) {
        this.rotation = rotation;
    }

    public Vector3 getScale() {
        return scale;
    }
    public void setScale(Vector3 scale) {
        this.scale = scale;
    }
}
