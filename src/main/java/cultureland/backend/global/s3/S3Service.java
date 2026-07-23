package cultureland.backend.global.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.region}")
    private String region;

    public String uploadPoster(MultipartFile file) {
        validateFile(file);

        String key = createObjectKey(file);

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();

        try {
            s3Client.putObject(
                    request,
                    RequestBody.fromInputStream(
                            file.getInputStream(),
                            file.getSize()
                    )
            );
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "포스터 이미지 업로드에 실패했습니다.",
                    exception
            );
        }

        return createFileUrl(key);
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "포스터 이미지는 필수입니다."
            );
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "포스터 이미지는 10MB 이하만 업로드할 수 있습니다."
            );
        }

        String contentType = file.getContentType();

        if (contentType == null
                || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException(
                    "JPG, PNG, WEBP 이미지만 업로드할 수 있습니다."
            );
        }
    }

    private String createObjectKey(MultipartFile file) {
        String extension = getExtension(
                file.getOriginalFilename()
        );

        return "posters/"
                + UUID.randomUUID()
                + extension;
    }

    private String getExtension(String originalFilename) {
        if (originalFilename == null
                || !originalFilename.contains(".")) {
            return "";
        }

        String extension = originalFilename.substring(
                originalFilename.lastIndexOf(".")
        ).toLowerCase();

        return switch (extension) {
            case ".jpg", ".jpeg", ".png", ".webp" -> extension;
            default -> "";
        };
    }

    private String createFileUrl(String key) {
        return String.format(
                "https://%s.s3.%s.amazonaws.com/%s",
                bucket,
                region,
                key
        );
    }
}