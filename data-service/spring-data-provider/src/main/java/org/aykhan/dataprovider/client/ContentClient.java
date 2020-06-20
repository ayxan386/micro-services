package org.aykhan.dataprovider.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@FeignClient(
    url = "${urls.content.base}",
    name = "content-client"
)
public interface ContentClient {
  @PostMapping(value = "/add", consumes = MULTIPART_FORM_DATA_VALUE)
  ResponseEntity<String> addFile(
      @PathVariable(name = "file") MultipartFile file,
      @RequestParam(name = "name") String name);
}
