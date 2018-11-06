package ru.job4j.callboard.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;


/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 24.10.18
 */
@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {
    private String imagePath;

    public void init() {
        this.imagePath = "C:/upload/images/";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String requestedImage = request.getPathInfo();

        if (requestedImage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        // Decode the file name (might contain spaces and on) and prepare file object.
        final File image = new File(URLDecoder.decode(requestedImage, "UTF-8"));

        if (!image.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        final String contentType = getServletContext().getMimeType(image.getName());

        if (contentType == null || !contentType.startsWith("image")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        response.reset();
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(image.length()));

        Files.copy(image.toPath(), response.getOutputStream());
    }
}
