package com.aykhand.contentmanager.controller;

import com.aykhand.contentmanager.entity.FileDM;
import com.aykhand.contentmanager.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/content")
public class ContentController {


  private final ContentService contentService;

  @PostMapping(value = "/save")
  public ResponseEntity<String> addFile(@RequestParam(name = "file") MultipartFile file) {
    return ResponseEntity.ok(contentService.saveFile(file));
  }

  @PostMapping(value = "/add")
  public ResponseEntity<String> addFile(
      @RequestParam(name = "file") MultipartFile file,
      @RequestParam(name = "name") String name) {
    return ResponseEntity.ok(contentService.saveFile(file, name));
  }

  @GetMapping(value = "/{filename}")
  public ResponseEntity<Resource> getFile(@PathVariable(name = "filename") String filename) {
    FileDM file = contentService.getFileByName(filename);
    return ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(file.getType()))
        .header(CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
        .body(new ByteArrayResource(file.getContent()));
  }

}
