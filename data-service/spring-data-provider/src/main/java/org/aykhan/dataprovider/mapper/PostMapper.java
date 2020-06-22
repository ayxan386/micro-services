package org.aykhan.dataprovider.mapper;

import org.aykhan.dataprovider.dto.post.PostRequest;
import org.aykhan.dataprovider.dto.post.PostResponse;
import org.aykhan.dataprovider.entity.PostDM;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, uses = {UserMapper.class, CommentMapper.class}, componentModel = "spring")
public interface PostMapper {
  PostResponse postDMtoResponse(PostDM byId);

  PostDM postReqToDM(PostRequest postRequest);
}
