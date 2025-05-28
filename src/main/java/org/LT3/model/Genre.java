package org.LT3.model;

public class Genre {
    Long id;
    String name;

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "Genre{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
