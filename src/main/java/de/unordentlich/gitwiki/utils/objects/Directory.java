package de.unordentlich.gitwiki.utils.objects;

import de.unordentlich.gitwiki.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        Directory directory = Constants.getRepository();
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
        return (parent != null) ? parent : Constants.getRepository();
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

        ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta paneMeta = pane.getItemMeta();
        paneMeta.setDisplayName("§f");
        paneMeta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ATTRIBUTES);
        pane.setItemMeta(paneMeta);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (i < 10) {
                inventory.setItem(i, pane);
            } else if (i > 16 && i < 19) {
                inventory.setItem(i, pane);
            } else if (i > 25 && i < 28) {
                inventory.setItem(i, pane);
            } else if (i > 27) {
                inventory.setItem(i, pane);
            }
        }

        for (Directory directory : directories) {
            ItemStack item = new ItemStack(Material.BOOKSHELF, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§7◌ §6" + directory.getName());
            item.setItemMeta(meta);
            inventory.addItem(item);
        }

        for (File file : files) {
            ItemStack item = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§e" + file.getName());
            //TODO Description from Readme
            item.setItemMeta(meta);
            inventory.addItem(item);
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
