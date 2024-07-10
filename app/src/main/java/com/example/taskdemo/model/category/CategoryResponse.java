package com.example.taskdemo.model.category;

public class CategoryResponse {

        private int id;
        private String name;
        private String image;
        private String creationAt;
        private String updatedAt;

        // Constructor
        public CategoryResponse(int id, String name, String image, String creationAt, String updatedAt) {
            this.id = id;
            this.name = name;
            this.image = image;
            this.creationAt = creationAt;
            this.updatedAt = updatedAt;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setCreationAt(String creationAt) {
            this.creationAt = creationAt;
        }

        public String getCreationAt() {
            return creationAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
