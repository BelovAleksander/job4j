package ru.job4j.concurrency;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;


import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 16.07.18
 * Класс осуществляет поиск, используя два потока:
 * один - для перебора файлов в файловой системе,
 * второй - для поиска заданного текста в файле.
 */

@ThreadSafe
public class ParallelSearch {
    private final String root;
    private final String text;
    private final List<String> extensions;
    private volatile boolean searchFinish = false;
    @GuardedBy("this")
    private final ArrayBlockingQueue<String> files = new ArrayBlockingQueue<>(1);

    private final ArrayList<String> paths = new ArrayList<>();

    public ParallelSearch(String root, String text, List<String> extensions) {
        this.root = root;
        this.text = text;
        this.extensions = extensions;
    }

    public void init() {
        Thread search = new Thread() {
            public void run() {
                SearchText st = new SearchText();
                Path path = Paths.get(root);
                try {
                    Files.walkFileTree(path, st);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    searchFinish = true;
                    System.out.println("search finished");
                }
            }
        };

        Thread read = new Thread() {
            public void run() {
                while (!searchFinish) {
                    String file = null;
                    try {
                        file = files.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (containsText(file, text)) {
                        paths.add(file);
                    }
                }
                System.out.println("read finished");
            }
        };
        search.start();
        read.start();
    }

    private boolean containsText(String file, String text) {
        boolean result = false;

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file))
            );
            String str;
            while ((str = reader.readLine()) != null) {
                if (str.contains(text)) {
                    result = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public synchronized ArrayList<String> result() {
        return this.paths;
    }


    class SearchText extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
            if (attr.isRegularFile()) {
                String extension = file.toString();
                extension = extension.substring(extension.lastIndexOf(".") + 1);
                if (extensions.contains(extension)) {
                    try {
                        files.put(file.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return CONTINUE;
        }
    }
}
