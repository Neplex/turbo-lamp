package com.example.kbourgeois.opengl;

import android.content.Context;
import com.example.kbourgeois.opengl.openGLAdapter.Object3D;
import com.example.kbourgeois.opengl.openGLAdapter.Vector3;

import java.util.Random;

public class Asteroid extends Object3D {

    private Vector3 rotation;
    private float speed;

    public Asteroid(Context context) {
        super();
        this.loadFromFile(context, "rock_c_01.obj");
        this.setShaders("vertexshader.vert", "fragmentshader.frag", "vPosition", "vNormal", "vTexCoord", R.drawable.stone);

        this.getTransform().translate(getNewPosition());

        Random rand = new Random();
        float size = rand.nextFloat() / 2 + .2f;
        speed = rand.nextFloat() * .04f + .01f;
        this.getTransform().setScale(new Vector3(size, size, size));
        rotation = new Vector3(
                rand.nextFloat(),
                rand.nextFloat(),
                rand.nextFloat()
        );
    }

    public void update(long delta_time){
        this.getTransform().translateZ(speed * delta_time);
        this.getTransform().rotate(rotation);

        if(this.getTransform().getPosition().z > 20){
            this.getTransform().setPosition(getNewPosition());
        }
    }

    private Vector3 getNewPosition() {
        Random rand = new Random();
        Vector3 v = new Vector3(
                rand.nextInt(20) - 10,
                rand.nextInt(20) - 10,
                -100 - rand.nextInt(200)
        );

        return v;
    }
}
