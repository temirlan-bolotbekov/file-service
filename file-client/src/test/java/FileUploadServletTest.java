import com.demo.filters.UploadFilter;
import com.demo.servlets.FileUploadServlet;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.*;

import static org.assertj.core.api.Assertions.assertThat;

public class FileUploadServletTest {

    private final FileUploadServlet servlet = new FileUploadServlet();
    MockHttpServletResponse response = new MockHttpServletResponse();
    MockHttpServletRequest request = new MockHttpServletRequest();
    UploadFilter filter = new UploadFilter();

    @Test
    public void givenJsonResponseAndStatusOkOfUploadServlet() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "testFile.txt",
                "testFile.txt",
                "multipart/form-data",
                new ClassPathResource("testFile.txt").getInputStream());
        MockPart part = new MockPart("file", file.getOriginalFilename(), file.getBytes());
        request.setMethod("POST");
        request.setContentType("multipart/form-data");
        request.addPart(part);

        String json = """
                {"id":1,"size":"64 Bytes","name":"testFile.txt"}""";

        servlet.doPost(request, response);
        assertThat(response.getContentAsString()).isEqualTo(json);
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void givenJsonResponseIncorrectTypeFile() throws Exception {

        MockFilterChain filterChain = new MockFilterChain();
        MockMultipartFile file = new MockMultipartFile(
                "testFile.txt",
                "testFile.docx",
                "multipart/form-data",
                new ClassPathResource("testFile.docx").getInputStream());
        MockPart part = new MockPart("file", file.getOriginalFilename(), file.getBytes());
        request.setMethod("POST");
        request.setContentType("multipart/form-data");
        request.addPart(part);
        filter.doFilter(request, response, filterChain);

        assertThat(
                response.getContentAsString())
                .isEqualTo("Incorrect files, files may be 'txt' and 'csv' extension and no more 100kb");
        assertThat(response.getStatus()).isEqualTo(200);
    }

}
