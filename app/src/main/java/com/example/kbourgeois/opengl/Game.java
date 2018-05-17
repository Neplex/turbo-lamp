package com.example.kbourgeois.opengl;

import android.content.Context;
import com.example.kbourgeois.opengl.openGLAdapter.Controller;
import com.example.kbourgeois.opengl.openGLAdapter.Object3D;
import com.example.kbourgeois.opengl.openGLAdapter.Scene;

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

    void moveShip(float x, float y, float z) {
        ship.getTransform().translate(x, y, z);
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
        ship = newObject("cube.obj", R.drawable.no_texture);

        skybox = newObject("cube.obj", R.drawable.brick);
        skybox.getTransform().setScale(10, 10, 10);

        /// ADD OBJECTS ///
        scene.addObject(ship);
        scene.addObject(skybox);
    }

    public void update(long delta_time) {
        /// LOOP ///
    }
}
