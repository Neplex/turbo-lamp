package com.example.kbourgeois.opengl;

import android.content.Context;

import com.example.kbourgeois.opengl.openGLAdapter.Object3D;
import com.example.kbourgeois.opengl.openGLAdapter.Vector3;

public class Ship extends Object3D {

    public Ship(Context context) {
        super();
        this.loadFromFile(context, "spaceship.obj");
        this.setShaders("vertexshader.vert", "fragmentshader.frag", "vPosition", "vNormal", "vTexCoord", R.drawable.no_texture);
        this.getTransform().setScale(new Vector3(.03f, .03f, .03f));
        this.getTransform().rotateY(180);
    }



}
