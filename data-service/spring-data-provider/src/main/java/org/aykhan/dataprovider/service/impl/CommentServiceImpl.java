package org.aykhan.dataprovider.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aykhan.dataprovider.config.UserPrincipialInjectorConfig;
import org.aykhan.dataprovider.dto.comments.CommentRequest;
import org.aykhan.dataprovider.dto.comments.CommentResponse;
import org.aykhan.dataprovider.entity.CommentsDM;
import org.aykhan.dataprovider.exception.notfound.NotFound;
import org.aykhan.dataprovider.exception.notfound.UserNotFound;
import org.aykhan.dataprovider.mapper.CommentMapper;
import org.aykhan.dataprovider.repository.CommentDMRepository;
import org.aykhan.dataprovider.repository.PostDMRepository;
import org.aykhan.dataprovider.repository.UserDMRepository;
import org.aykhan.dataprovider.service.CommentService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final PostDMRepository postDMRepository;
  private final UserDMRepository userDMRepository;
  private final CommentDMRepository commentDMRepository;
  private final UserPrincipialInjectorConfig injectorConfig;
  private final CommentMapper mapper;

  @Override
  public CommentResponse get(CommentRequest request) {
    return null;
  }

  @Override
  public CommentResponse add(CommentRequest request) {
    CommentsDM commentsDM = postDMRepository
        .findById(Long.valueOf(request.getPostId()))
        .map(postDM -> {
          CommentsDM dm = mapper.requestToDM(request);
          dm.setPost(postDM);
          dm.setAuthor(
              userDMRepository.getByNickname(injectorConfig.getUser().getUsername()).orElseThrow(() -> {
                throw new UserNotFound();
              }));
          return dm;
        })
        .orElseThrow(() -> {
          throw new NotFound("Post with given id could not be found");
        });
    commentDMRepository.save(commentsDM);
    return mapper.dmToResponse(commentsDM);
  }

  @Override
  public CommentResponse update(CommentRequest request) {
    return null;
  }

  @Override
  public String delete(CommentRequest request) {
    commentDMRepository
        .save(
            commentDMRepository.findById(Long.valueOf(request.getCommentId()))
                //TODO when the access levels are finally added make so that admins/moderators can delete any comment
                .filter(comment -> comment.getAuthor().getNickname().equals(injectorConfig.getUser().getUsername()))
                .map(comment -> {
                  comment.setAuthor(null);
                  comment.setPost(null);
                  return comment;
                })
                .orElseThrow(() -> {
                  throw new NotFound(("comment not found"));
                })
        );
    return "success";
  }
}
