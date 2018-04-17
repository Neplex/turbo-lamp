package com.example.kbourgeois.opengl;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Vector;


public class Cube {

    //IntBuffer vboID;
    private int mIdProjMatrix;
    private int mIdViewMatrix;
    private int mIdModelMatrix;

    Vector<Light> mLights;
    private float vertices[] = {
            -1, -1, 1, //0
            1, -1, 1, //1
            1, 1, 1, //2
            -1, 1, 1, //3

            1, -1, 1, //1 4
            1, 1, 1, //2 5
            1, 1, -1, //6 6
            1, -1, -1, //5 7

            -1, -1, -1, //4 8
            1, -1, -1, //5 9
            1, 1, -1, //6 10
            -1, 1, -1, //7 11

            -1, -1, 1, //0 12
            -1, -1, -1, //4 13
            -1, 1, -1, //7 14
            -1, 1, 1, //3 15

            -1, 1, 1, //3 16
            -1, 1, -1, //7 17
            1, 1, -1, //6 18
            1, 1, 1, //2 19

            -1, -1, 1, //0 20
            -1, -1, -1, //4 21
            1, -1, -1, //5 22
            1, -1, 1  //1 23

    };

    private byte indices[] = {
            0, 1, 2,
            2, 3, 0,

            4, 5, 6,
            6, 7, 4,

            8, 9, 10,
            10, 11, 8,

            12, 13, 14,
            14, 15, 12,

            16, 17, 18,
            18, 19, 16,

            20, 21, 22,
            22, 23, 20
    };

    private float tex_coords[] = {
            0.0f, 0.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            0.0f, 0.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            0.0f, 0.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            0.0f, 0.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            0.0f, 0.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            0.0f, 0.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
    };

    private FloatBuffer mVertexBuffer;
    private ByteBuffer mIndexBuffer;
    private FloatBuffer mTexBuffer;

    private static final int COORDS_PER_VERTEX = 3;

    private static final int COORDS_PER_TEX = 2;

    private final int VERTEX_STRIDE = COORDS_PER_VERTEX * 4;
    private final int TEX_STRIDE = COORDS_PER_TEX * 4;


    private int mProgram;
    private int mVertexID;
    private int mTexCoordID;
    int[] linkStatus = {0};

    private int texID[];

    public Cube() {
        mLights = new Vector<>();
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mVertexBuffer = byteBuf.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        byteBuf = ByteBuffer.allocateDirect(tex_coords.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mTexBuffer = byteBuf.asFloatBuffer();
        mTexBuffer.put(tex_coords);
        mTexBuffer.position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);


        mProgram = GLES20.glCreateProgram();

        Log.d("Debug: ", "Cube: mProgram : " + mProgram);
        int vertexShader = ShaderUtilities.loadShader(GLES20.GL_VERTEX_SHADER,
                "vertexshader.vert");
        int fragShader = ShaderUtilities.loadShader(GLES20.GL_FRAGMENT_SHADER,
                "fragmentshader.frag");
        GLES20.glAttachShader(mProgram, vertexShader);
        Log.d("Debug :", "Attach vertex shader : " + vertexShader);
        GLES20.glAttachShader(mProgram, fragShader);
        Log.d("Debug :", "Attach fragment shader : " + fragShader);
        GLES20.glLinkProgram(mProgram);
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);

        mVertexID = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mTexCoordID = GLES20.glGetAttribLocation(mProgram, "vTexCoord");

        mIdProjMatrix = GLES20.glGetUniformLocation(mProgram, "projection");
        mIdViewMatrix = GLES20.glGetUniformLocation(mProgram, "view");
        mIdModelMatrix = GLES20.glGetUniformLocation(mProgram, "model");


        texID = new int[2];
        GLES20.glGenTextures(2, texID, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        ShaderUtilities.loadTexture(R.drawable.brick, texID, 0);


        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        ShaderUtilities.loadTexture(R.drawable.normal_map, texID, 1);

        Log.d("Debug: ", "Cube: mVertexID : " + mVertexID);
        Log.d("Debug: ", "Cube: mTexCoordID : " + mTexCoordID);
        Log.d("Debug: ", "Cube: mIdProjMatrix : " + mIdProjMatrix);
        Log.d("Debug: ", "Cube: mIdViewMatrix : " + mIdViewMatrix);
        Log.d("Debug: ", "Cube: mIdModelMatrix : " + mIdModelMatrix);
    }

    public void addLight(Light l) {
        mLights.add(l);
    }

    public void draw(float[] projection, float[] view, float[] model) {
        // Add program to OpenGL environment.
        GLES20.glUseProgram(mProgram);

        // Prepare the cube coordinate data.
        GLES20.glEnableVertexAttribArray(mVertexID);
        GLES20.glVertexAttribPointer(
                mVertexID, 3, GLES20.GL_FLOAT, false, VERTEX_STRIDE, mVertexBuffer);

        // Prepare the cube tex data.
        GLES20.glEnableVertexAttribArray(mTexCoordID);
        GLES20.glVertexAttribPointer(
                mTexCoordID, 2, GLES20.GL_FLOAT, false, TEX_STRIDE, mTexBuffer);

        // Apply the projection and view transformation.
        GLES20.glUniformMatrix4fv(mIdViewMatrix, 1, false, view, 0);
        GLES20.glUniformMatrix4fv(mIdModelMatrix, 1, false, model, 0);
        GLES20.glUniformMatrix4fv(mIdProjMatrix, 1, false, projection, 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texID[0]);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texID[1]);

        for (Light l : mLights) {
            l.draw(mProgram);
        }
        // Draw the cube.
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_BYTE, mIndexBuffer);

        // Disable vertex arrays.
        GLES20.glDisableVertexAttribArray(mVertexID);
        GLES20.glDisableVertexAttribArray(mTexCoordID);

    }

}
