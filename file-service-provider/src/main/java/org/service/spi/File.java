package org.service.spi;


public class File {

    public File(long id, String size,  String name) {
        this.id = id;
        this.size = size;
        this.name = name;
    }

    private long id;
    private String size;
    private String name;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
