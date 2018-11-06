package ru.job4j.listeners;

import org.apache.log4j.Logger;
import ru.job4j.cars.logic.HibernateManager;
import ru.job4j.items.logic.ItemStorage;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 28.09.18
 */
public class DBInit implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger("APP2");
    private static final String PATH = System.getProperty("user.dir") + "/images/";
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new File(PATH).mkdirs();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateManager.closeFactory();
        ItemStorage.getInstance().closeFactory();

        Path path = Paths.get(PATH);
        try {
            deleteFileOrFolder(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFileOrFolder(final Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
            @Override public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
                    throws IOException {
                Files.delete(file);
                return CONTINUE;
            }

            @Override public FileVisitResult visitFileFailed(final Path file, final IOException e) {
                return handleException(e);
            }

            private FileVisitResult handleException(final IOException e) {
                e.printStackTrace(); // replace with more robust error handling
                return TERMINATE;
            }

            @Override public FileVisitResult postVisitDirectory(final Path dir, final IOException e)
                    throws IOException {
                if(e!=null)return handleException(e);
                Files.delete(dir);
                return CONTINUE;
            }
        });
    }
}
