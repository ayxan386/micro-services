package service

import com.google.inject.ImplementedBy
import dto.PostDTO
import service.impl.PostServiceImpl

@ImplementedBy(classOf[PostServiceImpl])
trait PostService extends CrudService[PostDTO, PostDTO] {
  def getAllPaged(page: Int, pageSize: Int): Seq[PostDTO]
}
