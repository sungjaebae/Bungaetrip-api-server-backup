//package GoGetters.GoGetter.api;
//
//
//import com.azure.storage.blob.BlobClient;
//import com.azure.storage.blob.BlobContainerClient;
//import com.azure.storage.blob.BlobContainerClientBuilder;
//import com.azure.storage.blob.BlobServiceClientBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.WritableResource;
//import org.springframework.util.StreamUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.nio.charset.Charset;
//
//@RestController
//@RequestMapping("/blob")
//public class BlobController {
//    @Value("${spring.cloud.azure.storage.blob.account-name}")
//    private String accountName;
//
//    @Value("${spring.cloud.azure.storage.blob.account-key}")
//    private String accountKey;
//
//    @Value("${spring.cloud.azure.storage.blob.endpoint}")
//    private String endpoint;
//
//
//    @Value("azure-blob://gogetters/test")
//    private Resource blobFile;
//
//    @GetMapping("/readBlobFile")
//    public String readBlobFile() throws IOException {
//        return StreamUtils.copyToString(
//                this.blobFile.getInputStream(),
//                Charset.defaultCharset());
//    }
//
//    @PostMapping("/writeBlobFile")
//    public String writeBlobFile(@RequestBody String data) throws IOException {
//        try (OutputStream os = ((WritableResource) this.blobFile).getOutputStream()) {
//            os.write(data.getBytes());
//        }
//        return "file was updated";
//    }
//
//    @PostMapping("/upload")
//    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
//        String constr="AccountName="+accountName
//                +";AccountKey="+accountKey
//                +";EndpointSuffix=core.windows.net;" +
//                "DefaultEndpointsProtocol=https;";
//        BlobContainerClient container=new BlobContainerClientBuilder()
//                .connectionString(constr)
//                .containerName("gogetters")
//                .buildClient();
//
//        BlobClient blob = container.getBlobClient(file.getOriginalFilename());
//        blob.upload(file.getInputStream(), file.getSize(), true);
//        return "ok";
//    }
//}