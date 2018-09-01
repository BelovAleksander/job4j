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

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 29.08.18
 */
public class JSONControllerTest {
    @Test
    public void whenDoPost() throws ServletException, IOException {
        JSONController controller = new JSONController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        BufferedReader reader = mock(BufferedReader.class);


        when(request.getReader()).thenReturn(reader);
        when(reader.readLine()).thenReturn(
                "{\"firstName\" : \"Alex\", \"lastName\" : \"White\","
                        + " \"sex\" : \"male\", \"description\" : \"empty\"}");
        controller.doPost(request, response);

        assertThat(UsersStorage.getInstance().
                findByNameAndLastName("Alex", "White") != null, is(true));
    }
}
