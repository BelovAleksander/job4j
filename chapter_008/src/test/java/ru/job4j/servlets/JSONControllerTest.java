package ru.job4j.servlets;

import org.junit.Test;
import ru.job4j.logic.UsersStorage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 04.09.18
 */
public class JSONControllerTest {
    private JSONController controller = new JSONController();
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private BufferedReader reader = mock(BufferedReader.class);

    @Test
    public void whenDoPost() throws ServletException, IOException {
        when(request.getReader()).thenReturn(reader);
        when(reader.readLine()).thenReturn(
                "{\"firstName\" : \"Alex\", \"lastName\" : \"White\","
                        + " \"sex\" : \"male\", \"description\" : \"empty\"}");
        controller.doPost(request, response);

        assertThat(UsersStorage.getInstance().findAll().values().iterator().next().getFirstName(), is("Alex"));
    }

    @Test
    public void whenDoGet() throws ServletException, IOException {
        controller.doGet(request, response);

        verify(response).setContentType("text/html");
        verify(request).setAttribute("users", UsersStorage.getInstance().findAll());
    }
}
