import com.demo.servlets.FileUploadServlet;
import com.demo.servlets.FilesGetServlet;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import static org.assertj.core.api.Assertions.assertThat;

public class FilesGetServletTest {
    private final FilesGetServlet getServlet = new FilesGetServlet();
    private final FileUploadServlet uploadServlet = new FileUploadServlet();
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockHttpServletRequest request = new MockHttpServletRequest();

    @Test
    public void givenJsonResultsAndStatusOk() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "testFile.txt",
                "testFile.txt",
                "multipart/form-data",
                new ClassPathResource("testFile.txt").getInputStream());
        MockPart part = new MockPart("file",file.getOriginalFilename(), file.getBytes());
        request.setMethod("POST");
        request.setContentType("multipart/form-data");
        request.addPart(part);
        uploadServlet.doPost(request, response);

        String json = """
                {"id":1,"size":"64 Bytesd","name":"testFile.txt"}""";

        request.setMethod("GET");
        getServlet.doGet(request, response);

        assertThat(response.getContentAsString()).isEqualTo(json);
        assertThat(response.getStatus()).isEqualTo(200);
    }
}