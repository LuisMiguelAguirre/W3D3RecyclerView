package com.example.admin.w3d3recyclerview;

/**
 * Created by Luis Aguirre on 9/6/2017.
 */

public class Famous {

    private String name;
    private String description;
    private int image_id;

    public Famous(String name, String description, int image_id) {
        this.name = name;
        this.description = description;
        this.image_id = image_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }
}
