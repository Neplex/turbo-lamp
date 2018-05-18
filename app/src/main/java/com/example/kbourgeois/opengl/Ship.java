package com.example.kbourgeois.opengl;

import android.content.Context;

import com.example.kbourgeois.opengl.openGLAdapter.Object3D;
import com.example.kbourgeois.opengl.openGLAdapter.Vector3;

public class Ship extends Object3D {

    public Ship(Context context) {
        super();
        this.loadFromFile(context, "SpaceCraft.obj");
        this.setShaders("vertexshader.vert", "fragmentshader.frag", "vPosition", "vNormal", "vTexCoord", R.drawable.spacecraft);
        this.getTransform().setScale(new Vector3(.5f, .5f, .5f));
        this.getTransform().rotateY(180);
    }



}
