package GoGetters.GoGetter.util;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class BlobStorage {
    @Value("${spring.cloud.azure.storage.blob.account-name}")
    private String accountName;

    @Value("${spring.cloud.azure.storage.blob.account-key}")
    private String accountKey;

    @Value("${spring.cloud.azure.storage.blob.endpoint}")
    private String endpoint;

    private String blobContainerName = "picture";

    private BlobContainerClient blobContainer;

    @PostConstruct
    public void makeBlobContainer(){
        log.info("account Name {}",accountName);
        String constr="AccountName="+accountName
                +";AccountKey="+accountKey
                +";EndpointSuffix=core.windows.net;" +
                "DefaultEndpointsProtocol=https;";
        log.info("storage blob name :{}",accountName);
        this.blobContainer=new BlobContainerClientBuilder()
                .connectionString(constr)
                .containerName(blobContainerName)
                .buildClient();
    }
    public String uploadFile(MultipartFile file) throws  IOException {
        //파일 이름 중복방지
        UUID uuid = UUID.randomUUID();
        String uploadImageName = uuid.toString() +"_"+ file.getOriginalFilename();
        BlobClient blob = blobContainer.getBlobClient(uploadImageName);
        blob.upload(file.getInputStream(), file.getSize(), true);

        return blob.getBlobName();
    }

    public String getFileUrl(String blobName) {
        BlobClient blob = blobContainer.getBlobClient(blobName);
        return blob.getBlobUrl();
    }

    public Boolean deleteBlob(String blobName) {

        BlobClient blob = blobContainer.getBlobClient(blobName);
        blob.delete();
        return true;
    }

}
