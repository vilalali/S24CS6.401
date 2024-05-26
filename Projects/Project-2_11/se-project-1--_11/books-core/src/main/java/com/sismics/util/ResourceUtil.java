package com.sismics.util;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Resource utilities.
 *
 * @author jtremeaux  
 */
public class ResourceUtil {

    private ResourceUtil() {
        // Private constructor to prevent instantiation
    }

    private static boolean isDirectoryInJar(URL dirUrl) {
        return dirUrl != null && dirUrl.getProtocol().equals("jar");
    }

    private static boolean isDirectoryOnFilesystem(URL dirUrl) {
        return dirUrl != null && dirUrl.getProtocol().equals("file");
    }

    private static String normalizePath(String path) {
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        return path;
    }

    private static Set<String> collectFilesFromJar(URL dirUrl, String path, FilenameFilter filter) throws IOException {
        String jarPath = dirUrl.getPath().substring(5, dirUrl.getPath().indexOf("!"));
        JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
        Set<String> fileSet = new HashSet<>();

        try {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                String entryName = entries.nextElement().getName();
                if (!entryName.startsWith(path)) {
                    continue;
                }
                String name = entryName.substring(path.length());
                if (!"".equals(name)) {
                    int checkSubdir = name.indexOf(File.separatorChar);
                    if (checkSubdir >=  0) {
                        name = name.substring(0, checkSubdir);
                    }
                    if (filter == null || filter.accept(null, name)) {
                        fileSet.add(name);
                    }
                }
            }
        } finally {
            jar.close();
        }

        return fileSet;
    }

    public static List<String> list(Class<?> clazz, String path, FilenameFilter filter) throws URISyntaxException, IOException {
        URL dirUrl = clazz.getResource(path);
        if (isDirectoryOnFilesystem(dirUrl)) {
            return Arrays.asList(new File(dirUrl.toURI()).list(filter));
        }

        if (isDirectoryInJar(dirUrl)) {
            path = normalizePath(path);
            return Lists.newArrayList(collectFilesFromJar(dirUrl, path, filter));
        }

        throw new UnsupportedOperationException(MessageFormat.format("Cannot list files for URL {0}", dirUrl));
    }

    public static List<String> list(Class<?> clazz, String path) throws URISyntaxException, IOException {
        return list(clazz, path, null);
    }
}