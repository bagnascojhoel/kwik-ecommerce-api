package br.com.kwikecommerce.api.application.storage;

import br.com.kwikecommerce.api.application.exceptionhandling.BadRequestException;
import br.com.kwikecommerce.api.application.message.MessageProperty;
import br.com.kwikecommerce.api.application.logging.LogService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;


@Component
public record StorageUtil(
    LogService logService,
    AwsProperties awsProperties
) {

    private static final Map<String, String> relations = Map.of(
        IMAGE_PNG_VALUE, ".png",
        IMAGE_JPEG_VALUE, ".jpg"
    );

    public String discoverExtension(MultipartFile file) {
        var contentType = file.getContentType();
        var cannotDiscoverExtension = isNull(contentType) || !relations.containsKey(contentType);
        if (cannotDiscoverExtension) {
            logService.logError(MessageProperty.use("log.storage.unknown-content-type", file.getContentType()));
            throw new BadRequestException(MessageProperty.of("e.file.extension-discovery-failed"));
        }

        return relations.get(contentType);
    }

}
