package com.example.kbourgeois.opengl;

import android.content.Context;

import com.example.kbourgeois.opengl.openGLAdapter.Object3D;
import com.example.kbourgeois.opengl.openGLAdapter.Vector3;

public class Skybox extends Object3D {

    public Skybox(Context context) {
        super();
        this.loadFromFile(context, "sphere.obj");
        this.setShaders("vertexshader.vert", "fragmentshader.frag", "vPosition", "vNormal", "vTexCoord", R.drawable.skysphere);
        this.getTransform().setScale(new Vector3(80, 80, 80));
    }
}
