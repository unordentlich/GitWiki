package de.unordentlich.gitwiki.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.unordentlich.gitwiki.utils.objects.Directory;
import de.unordentlich.gitwiki.utils.objects.File;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.bukkit.Bukkit;

public class RepositoryScan {

    public static void analyze(String path) throws UnirestException {
        HttpResponse<String> response = Unirest.get("https://api.github.com/repos/" + Variables.owner + "/" + Variables.repo + "/contents/" + path)
                .asString();
        String json = response.getBody();
        JsonArray files = new Gson().fromJson(json, JsonArray.class);
        Directory currentDirectory = Variables.getRepository();
        for (int i = 0; i < files.size(); i++) {
            JsonObject file = files.get(i).getAsJsonObject();
            String name = file.get("name").getAsString();
            String type = file.get("type").getAsString();
            String filePath = file.get("path").getAsString();

            if (name.contains("#")) {
                Bukkit.getConsoleSender().sendMessage("§c§lGitWiki §7§l> §c§l" + name + "§7§l: §c§l#§7§l is not allowed in file names!");
                continue;
            }

            if (type.equals("file") && filePath.endsWith(".md")) {
                currentDirectory.add(new File(name, filePath, currentDirectory));
            }
        }
        for (int i = 0; i < files.size(); i++) {
            JsonObject file = files.get(i).getAsJsonObject();
            String name = file.get("name").getAsString();
            String type = file.get("type").getAsString();
            String filePath = file.get("path").getAsString();
            if (type.equals("dir")) {
                Directory newDirectory = new Directory(name, filePath, currentDirectory);

                if (isSubDirectory(newDirectory.getPath(), currentDirectory.getPath())) {
                    currentDirectory.add(newDirectory);
                    currentDirectory = newDirectory;
                } else {
                    currentDirectory = currentDirectory.getParent();
                    currentDirectory.add(newDirectory);
                    currentDirectory = newDirectory;
                }

                analyze(path + "/" + name, currentDirectory);
            }
        }
    }

    public static void analyze(String path, Directory current) throws UnirestException {
        HttpResponse<String> response = Unirest.get("https://api.github.com/repos/" + Variables.owner + "/" + Variables.repo + "/contents/" + path)
                .asString();
        String json = response.getBody();
        JsonArray files = new Gson().fromJson(json, JsonArray.class);
        Directory currentDirectory = current;
        for (int i = 0; i < files.size(); i++) {
            JsonObject file = files.get(i).getAsJsonObject();
            String name = file.get("name").getAsString();
            String type = file.get("type").getAsString();
            String filePath = file.get("path").getAsString();

            if (name.contains("#")) {
                Bukkit.getConsoleSender().sendMessage("§c§lGitWiki §7§l> §c§l" + name + "§7§l: §c§l#§7§l is not allowed in file names!");
                continue;
            }

            if (type.equals("file") && filePath.endsWith(".md")) {
                currentDirectory.add(new File(name, filePath, currentDirectory));
            }
        }
        for (int i = 0; i < files.size(); i++) {
            JsonObject file = files.get(i).getAsJsonObject();
            String name = file.get("name").getAsString();
            String type = file.get("type").getAsString();
            String filePath = file.get("path").getAsString();

            if (name.contains("#")) {
                Bukkit.getConsoleSender().sendMessage("§c§lGitWiki §7§l> §c§l" + name + "§7§l: §c§l#§7§l is not allowed in file names!");
                continue;
            }

            if (type.equals("dir")) {
                Directory newDirectory = new Directory(name, filePath, currentDirectory);

                if (isSubDirectory(newDirectory.getPath(), currentDirectory.getPath())) {
                    currentDirectory.add(newDirectory);
                    currentDirectory = newDirectory;
                } else {
                    currentDirectory = currentDirectory.getParent();
                    currentDirectory.add(newDirectory);
                    currentDirectory = newDirectory;
                }

                analyze(path + "/" + name, currentDirectory);
            }
        }
    }

    private static boolean isSubDirectory(String path, String subPath) {
        if (!subPath.equals("/")) {
            return path.startsWith(subPath) && !(subPath.equalsIgnoreCase("/"));
        }
        return false;
    }

}
