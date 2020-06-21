package org.aykhan.dataprovider.service.impl;

import lombok.AllArgsConstructor;
import org.aykhan.dataprovider.config.UserPrincipialInjectorConfig;
import org.aykhan.dataprovider.dto.comments.CommentResponse;
import org.aykhan.dataprovider.dto.post.PostRequest;
import org.aykhan.dataprovider.dto.post.PostResponse;
import org.aykhan.dataprovider.entity.PostDM;
import org.aykhan.dataprovider.entity.UserDM;
import org.aykhan.dataprovider.exception.badrequest.BadRequest;
import org.aykhan.dataprovider.exception.notfound.NotFound;
import org.aykhan.dataprovider.mapper.PostMapper;
import org.aykhan.dataprovider.repository.PostDMRepository;
import org.aykhan.dataprovider.repository.UserDMRepository;
import org.aykhan.dataprovider.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostDMRepository postDMRepository;
  private final PostMapper postMapper;
  private final UserPrincipialInjectorConfig userInjector;
  private final UserDMRepository userDMRepository;

  @Override
  public PostResponse get(PostRequest request) {
    return postMapper.postDMtoResponse(
        postDMRepository.findById(request.getId())
            .orElseThrow(() -> new NotFound("Post not found"))
    );
  }

  @Override
  public PostResponse add(PostRequest request) {
    PostDM postDM = postMapper.postReqToDM(request);
    UserDM userDM = userDMRepository
        .getByNickname(userInjector.getUser().getUsername())
        .orElseThrow(() -> new BadRequest("User should be present in the repository"));
    postDM.setAuthor(userDM);
    return postMapper.postDMtoResponse(postDMRepository
        .save(postDM));
  }

  @Override
  public PostResponse update(PostRequest request) {
    return postMapper
        .postDMtoResponse(
            postDMRepository.save(
                postDMRepository.findById(request.getId())
                    .map(dm -> {
                      dm.setBody(StringUtils.isEmpty(request.getBody()) ? dm.getBody() : request.getBody());
                      dm.setTitle(StringUtils.isEmpty(request.getTitle()) ? dm.getTitle() : request.getTitle());
                      return dm;
                    }).orElseThrow(() -> new NotFound("Post with that id does not exist"))
            )
        );
  }

  @Override
  public String delete(PostRequest request) {
    assert request.getId() != null;
    postDMRepository
        .findById(request.getId())
        .ifPresentOrElse(postDMRepository::delete, () -> {
          throw new NotFound("Post not found");
        });
    return "success";
  }

  @Override
  public List<PostResponse> getAll() {
    return postDMRepository
        .findAllByAuthor_Nickname(userInjector.getUser().getUsername())
        .stream()
        .map(postMapper::postDMtoResponse)
        .collect(toList());
  }
}
