package com.example.kbourgeois.opengl.openGLAdapter;

import android.content.Context;
import android.opengl.GLES20;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Object3D extends Transformable implements Drawable {

    private static final int COORDS_PER_VERTEX = 3;
    private static final int COORDS_PER_NORMAL = 3;
    private static final int COORDS_PER_TEX = 2;

    private int[] textureID = new int[1];

    private final int VERTEX_STRIDE = COORDS_PER_VERTEX * 4;
    private final int TEX_STRIDE = COORDS_PER_TEX * 4;
    private final int NORMAL_STRIDE = COORDS_PER_NORMAL * 4;

    private List<Object3D> children;

    private int program;
    private int vertexShaderID;
    private int fragShaderID;
    private int vertexID;
    private int normalID;
    private int texID;
    private int viewMatrixID;
    private int projMatrixID;
    private int modelMatrixID;

    private float vertices[];
    private float normals[];
    private float uvs[];
    private int indices[];

    private FloatBuffer vertexBuffer;
    private IntBuffer indexBuffer;
    private FloatBuffer texBuffer;
    private FloatBuffer normalBuffer;

    public Object3D() {
        children = new ArrayList<>();
    }

    /**
     * Add child
     *
     * @param child
     */
    void addChild(Object3D child) {
        children.add(child);
    }

    /**
     * Remove child
     *
     * @param child
     */
    void removeChild(Object3D child) {
        children.remove(child);
    }

    @Override
    public String toString() {
        return "Object3D";
    }

    /**
     * Draw object and children (relative to parent)
     *
     * @param camera
     */
    @Override
    public void draw(Camera camera, Transform parentTransform) {
        GLES20.glUseProgram(program);

        // Get new transform
        Transform tf = new Transform();
        tf.applyTransform(parentTransform);
        tf.applyTransform(this.getTransform());

        // Apply the projection and view transformation.
        GLES20.glUniformMatrix4fv(viewMatrixID, 1, false, camera.getView(), 0);
        GLES20.glUniformMatrix4fv(projMatrixID, 1, false, camera.getProjection(), 0);
        GLES20.glUniformMatrix4fv(modelMatrixID, 1, false, tf.getModelMatrix(), 0);

        // Draw
        GLES20.glEnableVertexAttribArray(vertexID);
        GLES20.glVertexAttribPointer(vertexID, 3, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffer);

        GLES20.glEnableVertexAttribArray(texID);
        GLES20.glVertexAttribPointer(texID, 2, GLES20.GL_FLOAT, false, TEX_STRIDE, texBuffer);

        GLES20.glEnableVertexAttribArray(normalID);
        GLES20.glVertexAttribPointer(normalID, 2, GLES20.GL_FLOAT, false, NORMAL_STRIDE, normalBuffer);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureID[0]);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_INT, indexBuffer);

        GLES20.glDisableVertexAttribArray(vertexID);
        GLES20.glDisableVertexAttribArray(texID);
        GLES20.glDisableVertexAttribArray(normalID);

        // Draw children
        for (Object3D obj : children) {
            obj.draw(camera, tf);
        }
    }

    void clean() {
        GLES20.glDetachShader(program, vertexShaderID);
        GLES20.glDetachShader(program, fragShaderID);
        GLES20.glDeleteProgram(program);
    }

    public void setShaders(String vertexShader, String fragmentShader, String vertexLoc, String normalLoc, String texLoc, int texture) {
        program = GLES20.glCreateProgram();

        vertexShaderID = ShaderUtilities.loadShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        fragShaderID = ShaderUtilities.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);

        GLES20.glAttachShader(program, vertexShaderID);
        GLES20.glAttachShader(program, fragShaderID);
        GLES20.glLinkProgram(program);
        int[] linkStatus = {0};
        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);

        vertexID = GLES20.glGetAttribLocation(program, vertexLoc);
        normalID = GLES20.glGetAttribLocation(program, normalLoc);
        texID = GLES20.glGetAttribLocation(program, texLoc);


        GLES20.glGenTextures(1, textureID, 0);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE);
        ShaderUtilities.loadTexture(texture, textureID, 0);

        viewMatrixID = GLES20.glGetUniformLocation(program, "view");
        projMatrixID = GLES20.glGetUniformLocation(program, "projection");
        modelMatrixID = GLES20.glGetUniformLocation(program, "model");
    }

    public void loadFromFile(final Context c, final String filename) {
        try {
            InputStream is = c.getAssets().open(filename);

            java.util.Scanner sc = new java.util.Scanner(is);
            Vector<Float> vertices_tmp = new Vector<>();
            Vector<Float> normals_tmp = new Vector<>();
            Vector<Float> UVs_tmp = new Vector<>();
            Vector<Integer> verticesIndex = new Vector<>();
            Vector<Integer> normalsIndex = new Vector<>();
            Vector<Integer> UVsIndex = new Vector<>();

            while (sc.hasNext()) {
                String s = sc.nextLine();
                String[] splited = s.split("\\s");

                switch (splited[0]) {
                    case "v":
                        vertices_tmp.add(Float.valueOf(splited[1]));
                        vertices_tmp.add(Float.valueOf(splited[2]));
                        vertices_tmp.add(Float.valueOf(splited[3]));
                        break;
                    case "vt":
                        UVs_tmp.add(Float.valueOf(splited[1]));
                        UVs_tmp.add(Float.valueOf(splited[2]));
                        break;
                    case "vn":
                        normals_tmp.add(Float.valueOf(splited[1]));
                        normals_tmp.add(Float.valueOf(splited[2]));
                        normals_tmp.add(Float.valueOf(splited[3]));
                        break;
                    case "f":
                        String[] face1 = splited[1].split("/");
                        String[] face2 = splited[2].split("/");
                        String[] face3 = splited[3].split("/");
                        verticesIndex.add(Integer.valueOf(face1[0]));
                        verticesIndex.add(Integer.valueOf(face2[0]));
                        verticesIndex.add(Integer.valueOf(face3[0]));
                        UVsIndex.add(Integer.valueOf(face1[1]));
                        UVsIndex.add(Integer.valueOf(face2[1]));
                        UVsIndex.add(Integer.valueOf(face3[1]));
                        normalsIndex.add(Integer.valueOf(face1[2]));
                        normalsIndex.add(Integer.valueOf(face2[2]));
                        normalsIndex.add(Integer.valueOf(face3[2]));
                        break;
                    default:
                        break;
                }
            }

            is.close();

            vertices = new float[verticesIndex.size() * 3];
            uvs = new float[UVsIndex.size() * 2];
            normals = new float[normalsIndex.size() * 3];
            indices = new int[verticesIndex.size()];

            for (int i = 0; i < verticesIndex.size(); ++i) {
                vertices[i * 3] = vertices_tmp.elementAt((verticesIndex.elementAt(i) - 1) * 3);
                vertices[i * 3 + 1] = vertices_tmp.elementAt((verticesIndex.elementAt(i) - 1) * 3 + 1);
                vertices[i * 3 + 2] = vertices_tmp.elementAt((verticesIndex.elementAt(i) - 1) * 3 + 2);

                uvs[i * 2] = UVs_tmp.elementAt((UVsIndex.elementAt(i) - 1) * 2);
                uvs[i * 2 + 1] = UVs_tmp.elementAt((UVsIndex.elementAt(i) - 1) * 2 + 1);

                normals[i * 3] = normals_tmp.elementAt((normalsIndex.elementAt(i) - 1) * 3);
                normals[i * 3 + 1] = normals_tmp.elementAt((normalsIndex.elementAt(i) - 1) * 3 + 1);
                normals[i * 3 + 2] = normals_tmp.elementAt((normalsIndex.elementAt(i) - 1) * 3 + 2);

                indices[i] = i;
            }

            ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
            byteBuf.order(ByteOrder.nativeOrder());
            vertexBuffer = byteBuf.asFloatBuffer();
            vertexBuffer.put(vertices);
            vertexBuffer.position(0);

            byteBuf = ByteBuffer.allocateDirect(normals.length * 4);
            byteBuf.order(ByteOrder.nativeOrder());
            normalBuffer = byteBuf.asFloatBuffer();
            normalBuffer.put(normals);
            normalBuffer.position(0);

            byteBuf = ByteBuffer.allocateDirect(uvs.length * 4);
            byteBuf.order(ByteOrder.nativeOrder());
            texBuffer = byteBuf.asFloatBuffer();
            texBuffer.put(uvs);
            texBuffer.position(0);

            byteBuf = ByteBuffer.allocateDirect(indices.length * 4);
            byteBuf.order(ByteOrder.nativeOrder());
            indexBuffer = byteBuf.asIntBuffer();
            indexBuffer.put(indices);
            indexBuffer.position(0);

        } catch (IOException e) {
            // Do nothing
        }
    }
}
