package de.unordentlich.gitwiki.utils;

import de.unordentlich.gitwiki.utils.objects.Directory;
import de.unordentlich.gitwiki.utils.objects.TopicView;

public class Variables {

    public static String repo, owner;
    public static Directory repository = new Directory("*", "/");
    public static TopicView viewType = TopicView.CHAT;
    public static boolean permissionRequired = false;

    public static Directory getRepository() {
        return repository;
    }
}
