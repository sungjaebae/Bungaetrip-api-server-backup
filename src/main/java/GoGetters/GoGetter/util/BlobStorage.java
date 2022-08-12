package GoGetters.GoGetter.util;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class BlobStorage {
    @Value("${spring.cloud.azure.storage.blob.account-name}")
    private String accountName;

    @Value("${spring.cloud.azure.storage.blob.account-key}")
    private String accountKey;

    @Value("${spring.cloud.azure.storage.blob.endpoint}")
    private String endpoint;

    private String blobContainer = "picture";

    public String uploadFile(MultipartFile file) throws IOException, IOException {

        String constr="AccountName="+accountName
                +";AccountKey="+accountKey
                +";EndpointSuffix=core.windows.net;" +
                "DefaultEndpointsProtocol=https;";
        BlobContainerClient container=new BlobContainerClientBuilder()
                .connectionString(constr)
                .containerName(blobContainer)
                .buildClient();
        //파일 이름 중복방지
        UUID uuid = UUID.randomUUID();
        String uploadImageName = uuid.toString() +"_"+ file.getOriginalFilename();
        BlobClient blob = container.getBlobClient(uploadImageName);
        blob.upload(file.getInputStream(), file.getSize(), true);

        return blob.getBlobUrl();
    }
}
