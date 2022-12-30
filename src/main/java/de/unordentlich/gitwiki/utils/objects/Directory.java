package de.unordentlich.gitwiki.utils.objects;

import de.unordentlich.gitwiki.utils.Variables;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Directory {

    String name, path;
    Directory parent;
    ArrayList<Directory> directories = new ArrayList<>();
    ArrayList<File> files = new ArrayList<>();

    public Directory(String name, String path, Directory parent) {
        this.name = name;
        this.path = path;
        this.parent = parent;
    }

    public Directory(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public static Directory getByPath(String path) {
        Directory directory = Variables.getRepository();
        String targetDirectory = path.split("/")[path.length() - 1];
        for (int i = 0; i < path.split("/").length; i++) {
            String currentDirectory = path.split("/")[i];
            if (currentDirectory.equals(targetDirectory)) {
                return directory;
            }
            directory = directory.getSubDirectory(currentDirectory);
        }
        return directory;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Directory getParent() {
        return (parent != null) ? parent : Variables.getRepository();
    }

    public Directory add(Directory directory) {
        directories.add(directory);
        return this;
    }

    public Directory add(File file) {
        files.add(file);
        return this;
    }

    public Inventory print(Player p) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 4, "§k⁂§f §eHelp§6Center");

        for (int i = 0; i < inventory.getSize(); i++) {
            if (i < 10) {
                inventory.setItem(i, new ItemStack(Material.STRING, 1));
            } else if (i > 16 && i < 19) {
                inventory.setItem(i, new ItemStack(Material.STRING, 1));
            } else if (i > 25 && i < 28) {
                inventory.setItem(i, new ItemStack(Material.STRING, 1));
            } else if (i > 27) {
                inventory.setItem(i, new ItemStack(Material.STRING, 1));
            }
        }

        for (Directory directory : directories) {
            inventory.addItem(new ItemStack(Material.BOOKSHELF, 1));
        }

        for (File file : files) {
            inventory.addItem(new ItemStack(Material.PAPER, 1));
        }

        p.openInventory(inventory);
        return inventory;
    }

    public Directory getSubDirectory(String name) {
        for (Directory directory : directories) {
            if (directory.getName().equals(name)) {
                return directory;
            }
        }
        return null;
    }
}
