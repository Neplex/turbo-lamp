package com.example.kbourgeois.opengl;

import android.content.Context;
import com.example.kbourgeois.opengl.openGLAdapter.Camera;
import com.example.kbourgeois.opengl.openGLAdapter.Object3D;

public class Game {

    private Context context;
    private Camera scene;

    // Objects
    private Object3D ship;
    private Object3D skybox;

    Game(Context context) {
        this.context = context;
    }

    void moveShip(float x, float y, float z) {
        ship.getTransform().translate(x, y, z);
    }

    private Object3D newObject(String file, int texture) {
        Object3D obj = new Object3D();
        obj.loadFromFile(context, "cube.obj");
        obj.setShaders("vertexshader.vert", "fragmentshader.frag", "vPosition", "vNormal", "vTexCoord", texture);

        return obj;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void init(Camera camera) {
        this.scene = camera;

        /// CREATE OBJECTS ///
        ship = newObject("cube.obj", R.drawable.no_texture);

        skybox = newObject("cube.obj", R.drawable.brick);
        skybox.getTransform().setScale(10, 10, 10);

        /// ADD OBJECTS ///
        scene.addObject(ship);
        scene.addObject(skybox);
    }

    public void loop(long delta_time) {
        /// LOOP ///
    }
}
