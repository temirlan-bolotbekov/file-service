import com.demo.servlets.FileDownloadServlet;
import com.demo.servlets.FileUploadServlet;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import static org.assertj.core.api.Assertions.assertThat;


public class FileDownloadServletTest {

    private final FileDownloadServlet downloadServlet = new FileDownloadServlet();
    private final FileUploadServlet uploadServlet = new FileUploadServlet();
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockHttpServletRequest request = new MockHttpServletRequest();

    @Test
    public void givenJsonResponseAndStatusOkOfUGetServlet() throws Exception {
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

        request.setMethod("GET");
        request.setContentType("multipart/form-data");
        request.addParameter("id", "1");
        downloadServlet.doGet(request, response);

        assertThat(response.getContentType()).isEqualTo("application/octet-stream;charset=UTF-8");
        assertThat(response.getStatus()).isEqualTo(200);

    }


}
