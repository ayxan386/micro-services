package org.aykhan.dataprovider.mapper;

import org.aykhan.dataprovider.dto.comments.CommentResponse;
import org.aykhan.dataprovider.dto.post.PostRequest;
import org.aykhan.dataprovider.dto.post.PostResponse;
import org.aykhan.dataprovider.entity.CommentsDM;
import org.aykhan.dataprovider.entity.PostDM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, uses = {UserMapper.class, CommentMapper.class}, componentModel = "spring")
public interface PostMapper {
  PostResponse postDMtoResponse(PostDM byId);

  PostDM postReqToDM(PostRequest postRequest);
}
