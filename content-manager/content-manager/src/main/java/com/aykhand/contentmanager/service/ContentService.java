package com.aykhand.contentmanager.service;

import com.aykhand.contentmanager.entity.FileDM;
import org.springframework.web.multipart.MultipartFile;

public interface ContentService {

  String saveFile(MultipartFile file);

  FileDM getFileByName(String filename);

  String saveFile(MultipartFile file, String name);
}
