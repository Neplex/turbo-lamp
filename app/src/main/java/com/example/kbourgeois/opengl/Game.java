package com.example.kbourgeois.opengl;

import android.content.Context;
import com.example.kbourgeois.opengl.openGLAdapter.Controller;
import com.example.kbourgeois.opengl.openGLAdapter.Object3D;
import com.example.kbourgeois.opengl.openGLAdapter.Scene;
import com.example.kbourgeois.opengl.openGLAdapter.Vector3;

public class Game implements Controller {

    private Context context;
    private Scene scene;

    // Objects
    private Object3D ship;
    private Object3D skybox;

    Game(Context context, Scene scene) {
        this.context = context;
        this.scene = scene;
    }

    void moveShip(Vector3 vec) {
        if (ship != null) ship.getTransform().translate(vec);
    }

    private Object3D newObject(String file, int texture) {
        Object3D obj = new Object3D();
        obj.loadFromFile(context, file);
        obj.setShaders("vertexshader.vert", "fragmentshader.frag", "vPosition", "vNormal", "vTexCoord", texture);

        return obj;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void start() {
        /// CREATE OBJECTS ///
        ship = newObject("SpaceCraft.obj", R.drawable.spacecraft);
        ship.getTransform().setScale(new Vector3(.5f, .5f, .5f));
        ship.getTransform().rotateY(180);

        skybox = newObject("cube.obj", R.drawable.brick);
        skybox.getTransform().setScale(new Vector3(10, 10, 10));

        /// ADD OBJECTS ///
        scene.addObject(ship);
        scene.addObject(skybox);
    }

    public void update(long delta_time) {
        /// LOOP ///
    }
}
