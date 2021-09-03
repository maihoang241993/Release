package cpm.web.service.files;

import org.springframework.core.io.Resource;

public interface FileStorageService {

    Resource loadFileAsResource(String fileName);
    void createFiles(String fileName);
}
