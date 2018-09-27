package ru.job4j.servlets;

import org.junit.Test;
import org.mockito.Mockito;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.mockito.Mockito.*;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 27.09.18
 */
public class JSONControllerTest {
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private PrintWriter writer = mock(PrintWriter.class);


    @Test
    public void whenDoPostThreeTimesWithEqualsUsersThenUserAddedOnlyOneTimes() throws IOException {
        JSONController controller = new JSONController();
        String jsonString =
                "{\"firstName\" : \"Alex\", \"lastName\" : \"White\", \"sex\" : \"Male\", \"description\" : \"empty\"}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonString)));
        when(response.getWriter()).thenReturn(writer);
        when(response.getWriter().append(jsonString)).thenReturn(writer);

        controller.doPost(request, response);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonString)));
        controller.doPost(request, response);
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(jsonString)));
        controller.doPost(request, response);


        verify(writer, Mockito.times(1)).flush();
    }
}
