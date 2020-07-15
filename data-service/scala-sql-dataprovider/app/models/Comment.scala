package models

import dto.CommentDTO
import play.api.libs.json.Json
import scalikejdbc._

case class Comment(
                    id: Long,
                    body: Option[String] = None,
                    postId: Option[Long] = None,
                    authorId: Option[Long] = None) {

  def save()(implicit session: DBSession = Comment.autoSession): Comment = Comment.save(this)(session)

  def destroy()(implicit session: DBSession = Comment.autoSession): Int = Comment.destroy(this)(session)

}


object Comment extends SQLSyntaxSupport[Comment] {

  override val tableName = "t_comment"

  override val columns = Seq("id", "body", "post_id", "author_id")

  def apply(c: SyntaxProvider[Comment])(rs: WrappedResultSet): Comment = apply(c.resultName)(rs)

  def apply(c: ResultName[Comment])(rs: WrappedResultSet): Comment = new Comment(
    id = rs.get(c.id),
    body = rs.get(c.body),
    postId = rs.get(c.postId),
    authorId = rs.get(c.authorId)
  )

  def fromDTO(dto: CommentDTO): Comment = new Comment(id = dto.id, body = dto.body, authorId = dto.authorId, postId = dto.postId)

  val c = Comment.syntax("tc")

  implicit val writes = Json.writes[Comment]

  override val autoSession = AutoSession

  def opt(m: SyntaxProvider[Comment])(rs: WrappedResultSet): Option[Comment] =
    rs.longOpt(m.resultName.id).map(_ => Comment(m)(rs))

  def find(id: Long)(implicit session: DBSession = autoSession): Option[Comment] = {
    withSQL {
      select.from(Comment as c).where.eq(c.id, id)
    }.map(Comment(c.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Comment] = {
    withSQL(select.from(Comment as c)).map(Comment(c.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Comment as c)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Comment] = {
    withSQL {
      select.from(Comment as c).where.append(where)
    }.map(Comment(c.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Comment] = {
    withSQL {
      select.from(Comment as c).where.append(where)
    }.map(Comment(c.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Comment as c).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
              id: Long,
              body: Option[String] = None,
              postId: Option[Long] = None,
              authorId: Option[Long] = None)(implicit session: DBSession = autoSession): Comment = {
    withSQL {
      insert.into(Comment).namedValues(
        column.id -> id,
        column.body -> body,
        column.postId -> postId,
        column.authorId -> authorId
      )
    }.update.apply()

    Comment(
      id = id,
      body = body,
      postId = postId,
      authorId = authorId)
  }

  def batchInsert(entities: Seq[Comment])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'id -> entity.id,
        'body -> entity.body,
        'postId -> entity.postId,
        'authorId -> entity.authorId))
    SQL(
      """insert into t_comment(
      id,
      body,
      post_id,
      author_id
    ) values (
      {id},
      {body},
      {postId},
      {authorId}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Comment)(implicit session: DBSession = autoSession): Comment = {
    withSQL {
      update(Comment).set(
        column.id -> entity.id,
        column.body -> entity.body,
        column.postId -> entity.postId,
        column.authorId -> entity.authorId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Comment)(implicit session: DBSession = autoSession): Int = {
    withSQL {
      delete.from(Comment).where.eq(column.id, entity.id)
    }.update.apply()
  }

}
