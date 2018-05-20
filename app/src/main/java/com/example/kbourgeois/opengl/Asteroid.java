package com.example.kbourgeois.opengl;

import android.content.Context;

import com.example.kbourgeois.opengl.openGLAdapter.Object3D;
import com.example.kbourgeois.opengl.openGLAdapter.Vector3;

import java.util.Random;

public class Asteroid extends Object3D {

    private Vector3 rotation;

    public Asteroid(Context context) {
        super();
        this.loadFromFile(context, "rock_c_01.obj");
        this.setShaders("vertexshader.vert", "fragmentshader.frag", "vPosition", "vNormal", "vTexCoord", R.drawable.stone);
        Random rand = new Random();
        Vector3 v = new Vector3(rand.nextInt(3)-1,
                rand.nextInt(2),
                -100 - rand.nextInt(100));
        this.getTransform().translate(v);
        this.getTransform().setScale(new Vector3(0.4f, 0.4f, 0.4f));
        rotation = new Vector3(
                rand.nextFloat(),
                rand.nextFloat(),
                rand.nextFloat()
        );
    }

    public void update(long delta_time){
        this.getTransform().translateZ(0.05f*delta_time);
        if(this.getTransform().getPosition().z > 20){
            Random rand = new Random();
            Vector3 v = new Vector3(rand.nextInt(3)-1,
                    rand.nextInt(2),
                    -100 - rand.nextInt(100));
            this.getTransform().setPosition(v);
        }
        this.getTransform().rotate(rotation);
    }
}
