package com.aykhand.contentmanager.service.impl;

import com.aykhand.contentmanager.entity.FileDM;
import com.aykhand.contentmanager.exceptions.NotFound;
import com.aykhand.contentmanager.exceptions.UnableToReadFile;
import com.aykhand.contentmanager.repository.ContentRepository;
import com.aykhand.contentmanager.service.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

  private final ContentRepository contentRepository;
  @Value("${urls.my.base}")
  private String urlPrefix;

  @Override
  public String saveFile(MultipartFile file) {
    return saveFile(file, file.getOriginalFilename());
  }

  @Override
  @Transactional
  public FileDM getFileByName(String filename) {
    return contentRepository.findFirstByName(filename).orElseThrow(() -> {
      throw new NotFound("File not found");
    });
  }

  @Override
  @Transactional
  public String saveFile(MultipartFile file, String name) {
    try {
      FileDM fileDM = FileDM
          .builder()
          .content(file.getBytes())
          .name(name)
          .type(file.getContentType())
          .build();
      contentRepository
          .findFirstByName(name)
          .ifPresent(dm -> fileDM.setId(dm.getId()));
      log.info("file dm to be saved {}", fileDM);
      contentRepository.save(fileDM);
    } catch (IOException e) {
      throw new UnableToReadFile();
    }
    return String.format("%s/%s", urlPrefix, name);
  }
}
