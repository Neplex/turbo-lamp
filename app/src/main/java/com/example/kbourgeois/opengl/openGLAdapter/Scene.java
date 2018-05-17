package com.example.kbourgeois.opengl.openGLAdapter;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private Transform transform;

    // Objects in scene
    private List<Drawable> objects;

    public Scene() {
        this.transform = new Transform();
        this.objects = new ArrayList<>();
    }

    /**
     * Add object to scene
     *
     * @param object
     */
    public void addObject(Drawable object) {
        objects.add(object);
    }

    List<Drawable> getObjects() {
        return objects;
    }

    public Transform getTransform() {
        return transform;
    }
}
