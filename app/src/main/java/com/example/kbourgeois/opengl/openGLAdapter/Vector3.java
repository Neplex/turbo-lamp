package com.example.kbourgeois.opengl.openGLAdapter;

public class Vector3 {

    public float x, y, z;

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 add(Vector3 vec) {
        return new Vector3(
                this.x + vec.x,
                this.y + vec.y,
                this.z + vec.z
        );
    }
}
