package de.unordentlich.gitwiki.utils.objects;

public class File {

    String name, path;
    Directory parent;
    String content;

    public File(String name, String path, Directory parent) {
        this.name = name;
        this.path = path;
        this.parent = parent;
    }

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public File name(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public File path(String path) {
        this.path = path;
        return this;
    }

    public Directory getParent() {
        return parent;
    }

    public File parent(Directory parent) {
        this.parent = parent;
        return this;
    }

    public String getContent() {
        return content;
    }

    public File content(String content) {
        this.content = content;
        return this;
    }
}
