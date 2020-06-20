package org.aykhan.dataprovider.service;

import org.aykhan.dataprovider.dto.post.PostRequest;
import org.aykhan.dataprovider.dto.post.PostResponse;

import java.util.List;

public interface PostService extends CrudService<PostRequest, PostResponse> {
  List<PostResponse> getAll();
}
