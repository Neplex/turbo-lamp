package com.example.kbourgeois.opengl.openGLAdapter;

public abstract class Transformable {

    private Transform transform;

    Transformable() {
        this.transform = new Transform();
    }

    /**
     * Get object transform
     *
     * @return
     */
    public Transform getTransform() {
        return transform;
    }
}
