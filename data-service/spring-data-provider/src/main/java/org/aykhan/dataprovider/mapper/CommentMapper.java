package org.aykhan.dataprovider.mapper;

import org.aykhan.dataprovider.dto.comments.CommentRequest;
import org.aykhan.dataprovider.dto.comments.CommentResponse;
import org.aykhan.dataprovider.entity.CommentsDM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE, uses = {UserMapper.class})
public interface CommentMapper {
  CommentsDM requestToDM(CommentRequest request);

  @Mapping(source = "post.id", target = "postId")
  @Mapping(source = "id", target = "commentId")
  CommentResponse dmToResponse(CommentsDM dm);
}
