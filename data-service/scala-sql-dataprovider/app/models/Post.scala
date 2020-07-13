package models

import java.time.ZonedDateTime

import play.api.libs.json.Json
import scalikejdbc._

case class Post(
                 id: Long,
                 attachment: Option[String] = None,
                 body: Option[String] = None,
                 title: Option[String] = None,
                 authorId: Option[Long] = None,
                 author: Option[User] = None,
                 comments: Seq[Comment] = Nil,
                 createdOn: Option[ZonedDateTime] = None,
                 updatedOn: Option[ZonedDateTime] = None) {

  def save()(implicit session: DBSession = Post.autoSession): Post = Post.save(this)(session)

  def destroy()(implicit session: DBSession = Post.autoSession): Int = Post.destroy(this)(session)

}


object Post extends SQLSyntaxSupport[Post] {

  override val tableName = "t_post"

  override val columns = Seq("id", "attachment", "body", "title", "author_id", "created_on", "updated_on")

  def apply(p: SyntaxProvider[Post])(rs: WrappedResultSet): Post = apply(p.resultName)(rs)

  def apply(p: ResultName[Post])(rs: WrappedResultSet): Post = new Post(
    id = rs.get(p.id),
    attachment = rs.get(p.attachment),
    body = rs.get(p.body),
    title = rs.get(p.title),
    authorId = rs.get(p.authorId),
    createdOn = rs.get(p.createdOn),
    updatedOn = rs.get(p.updatedOn)
  )

  def apply(p: SyntaxProvider[Post], u: SyntaxProvider[User])(rs: WrappedResultSet): Post
  = apply(p)(rs).copy(author = Option(User(u)(rs)))

  implicit val writes = Json.writes[Post]

  val p = Post.syntax("tp")

  override val autoSession = AutoSession

  def find(id: Long)(implicit session: DBSession = autoSession): Option[Post] = {
    withSQL {
      select.from(Post as p).where.eq(p.id, id)
    }.map(Post(p.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Post] = {
    val (u, c) = (User.syntax("u"), Comment.syntax("u"))
    withSQL(
      select
        .from(Post as p)
        .leftJoin(User as u)
        .on(u.id, p.authorId)
        .leftJoin(Comment as c)
        .on(c.postId, p.id))
      .one(Post(p, u))
      .toMany(Comment.opt(c))
      .map { (post: Post, comments) => post.copy(comments = comments) }
      .list.apply()
  }

  def findAllPaged(page: Int, pageSize: Int)(implicit session: DBSession = autoSession): List[Post] = {
    val (u, c) = (User.syntax("u"), Comment.syntax("u"))
    withSQL(
      select
        .from(Post as p)
        .leftJoin(User as u)
        .on(u.id, p.authorId)
        .leftJoin(Comment as c)
        .on(c.postId, p.id)
        .offset(page * pageSize)
        .limit(pageSize))
      .one(Post(p, u))
      .toMany(Comment.opt(c))
      .map { (post: Post, comments) => post.copy(comments = comments) }
      .list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Post as p)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Post] = {
    withSQL {
      select.from(Post as p).where.append(where)
    }.map(Post(p.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Post] = {
    withSQL {
      select.from(Post as p).where.append(where)
    }.map(Post(p.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Post as p).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
              id: Long,
              attachment: Option[String] = None,
              body: Option[String] = None,
              title: Option[String] = None,
              authorId: Option[Long] = None,
              createdOn: Option[ZonedDateTime] = None,
              updatedOn: Option[ZonedDateTime] = None)(implicit session: DBSession = autoSession): Post = {
    withSQL {
      insert.into(Post).namedValues(
        column.id -> id,
        column.attachment -> attachment,
        column.body -> body,
        column.title -> title,
        column.authorId -> authorId,
        column.createdOn -> createdOn,
        column.updatedOn -> updatedOn
      )
    }.update.apply()

    Post(
      id = id,
      attachment = attachment,
      body = body,
      title = title,
      authorId = authorId,
      createdOn = createdOn,
      updatedOn = updatedOn)
  }

  def batchInsert(entities: Seq[Post])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'id -> entity.id,
        'attachment -> entity.attachment,
        'body -> entity.body,
        'title -> entity.title,
        'authorId -> entity.authorId,
        'createdOn -> entity.createdOn,
        'updatedOn -> entity.updatedOn))
    SQL(
      """insert into t_post(
      id,
      attachment,
      body,
      title,
      author_id,
      created_on,
      updated_on
    ) values (
      {id},
      {attachment},
      {body},
      {title},
      {authorId},
      {createdOn},
      {updatedOn}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: Post)(implicit session: DBSession = autoSession): Post = {
    withSQL {
      update(Post).set(
        column.id -> entity.id,
        column.attachment -> entity.attachment,
        column.body -> entity.body,
        column.title -> entity.title,
        column.authorId -> entity.authorId,
        column.createdOn -> entity.createdOn,
        column.updatedOn -> entity.updatedOn
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Post)(implicit session: DBSession = autoSession): Int = {
    withSQL {
      delete.from(Post).where.eq(column.id, entity.id)
    }.update.apply()
  }

}
