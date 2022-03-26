package br.com.kwikecommerce.api.application.storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface StorageService {

    List<String> upload(Storage storage, List<MultipartFile> photos);

    String upload(Storage storage, MultipartFile multipartFile);

}
