package org.aykhan.dataprovider.service.impl;

import lombok.AllArgsConstructor;
import org.aykhan.dataprovider.config.UserPrincipialInjectorConfig;
import org.aykhan.dataprovider.dto.LogDTO;
import org.aykhan.dataprovider.dto.post.PostRequest;
import org.aykhan.dataprovider.dto.post.PostResponse;
import org.aykhan.dataprovider.entity.PostDM;
import org.aykhan.dataprovider.entity.UserDM;
import org.aykhan.dataprovider.exception.badrequest.BadRequest;
import org.aykhan.dataprovider.exception.notfound.NotFound;
import org.aykhan.dataprovider.mapper.PostMapper;
import org.aykhan.dataprovider.repository.PostDMRepository;
import org.aykhan.dataprovider.repository.UserDMRepository;
import org.aykhan.dataprovider.service.LogService;
import org.aykhan.dataprovider.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostDMRepository postDMRepository;
  private final PostMapper postMapper;
  private final UserPrincipialInjectorConfig userInjector;
  private final UserDMRepository userDMRepository;
  private final LogService logService;

  @Override
  public PostResponse get(PostRequest request) {
    return postMapper.postDMtoResponse(
        postDMRepository.findById(request.getId())
            .orElseThrow(() -> new NotFound("Post not found"))
    );
  }

  @Override
  public PostResponse add(PostRequest request) {
    request.setId(null);
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
    Optional.ofNullable(request.getId())
        .ifPresentOrElse(id -> postDMRepository
                .findById(id)
                .ifPresentOrElse(postDMRepository::delete, () -> {
                  throw new NotFound("Post not found");
                }),
            () -> {
              throw new BadRequest("Post id is not provided");
            });
    return "success";
  }

  @Override
  public List<PostResponse> getAllMine() {
    return postDMRepository
        .findAllByAuthor_Nickname(userInjector.getUser().getUsername())
        .stream()
        .map(postMapper::postDMtoResponse)
        .collect(toList());
  }

  @Override
  public List<PostResponse> getAll(int page, int pageSize) {
    logService.log(
        LogDTO
            .builder()
            .source("data-provider")
            .message(String.format("POST getAll with page: %d, pageSize: %d", page, pageSize))
            .build()
    );
    Sort sort = Sort.by(Sort.Direction.ASC, "updatedOn");
    return Optional.of(postDMRepository.findAll(PageRequest.of(page, pageSize, sort))
        .toList()
        .stream()
        .map(postMapper::postDMtoResponse)
        .collect(toList()))
        .filter(postResponses -> !postResponses.isEmpty())
        .orElseThrow(() -> {
          throw new NotFound("page is empty");
        });
  }


}
