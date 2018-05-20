package com.example.kbourgeois.opengl;

import android.content.Context;
import com.example.kbourgeois.opengl.openGLAdapter.Controller;
import com.example.kbourgeois.opengl.openGLAdapter.Object3D;
import com.example.kbourgeois.opengl.openGLAdapter.Scene;
import com.example.kbourgeois.opengl.openGLAdapter.Vector3;

import java.util.ArrayList;

public class Game implements Controller {

    private Context context;
    private Scene scene;

    // Objects
    private Object3D ship;
    private Object3D skybox;
    private ArrayList<Asteroid> asteroids;

    Game(Context context, Scene scene) {
        this.context = context;
        this.scene = scene;
    }

    void moveShip(Vector3 vec) {
        if (ship != null) ship.getTransform().translate(vec);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void start() {
        /// CREATE OBJECTS ///

        ship = new Ship(context);

        skybox = new Skybox(context);

        asteroids = new ArrayList<>();
        for (int i = 0; i < 50; ++i) {
            asteroids.add(new Asteroid(context));
        }

        /// ADD OBJECTS ///
        scene.addObject(ship);
        scene.addObject(skybox);
        for(int i=0; i<asteroids.size();++i){
            scene.addObject(asteroids.get(i));
        }
    }

    public void update(long delta_time) {
        /// LOOP ///
        for(int i=0;i<asteroids.size();++i){
            asteroids.get(i).update(delta_time);
        }
    }
}
