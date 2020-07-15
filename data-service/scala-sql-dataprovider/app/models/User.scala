package models

import dto.UserDTO
import play.api.libs.json.Json
import scalikejdbc._

case class User(
                 id: Long,
                 email: Option[String] = None,
                 name: Option[String] = None,
                 nickname: Option[String] = None,
                 profilePicture: Option[String] = None,
                 surname: Option[String] = None) {

  def save()(implicit session: DBSession = User.autoSession): User = User.save(this)(session)

  def destroy()(implicit session: DBSession = User.autoSession): Int = User.destroy(this)(session)

}


object User extends SQLSyntaxSupport[User] {

  def fromDTO(dto: UserDTO): User = {
    new User(
      id = dto.id,
      email = dto.email,
      name = dto.name,
      nickname = dto.nickname,
      profilePicture = dto.profilePicture,
      surname = dto.surname
    )
  }

  def fromDTO(dto: Option[UserDTO]): Option[User] = {
    dto match {
      case Some(d) => Option(fromDTO(d))
      case None => None
    }
  }


  override val tableName = "t_user"

  override val columns = Seq("id", "email", "name", "nickname", "profile_picture", "surname")

  implicit val writes = Json.writes[User]

  def apply(u: SyntaxProvider[User])(rs: WrappedResultSet): User = apply(u.resultName)(rs)

  def apply(u: ResultName[User])(rs: WrappedResultSet): User = new User(
    id = rs.get(u.id),
    email = rs.get(u.email),
    name = rs.get(u.name),
    nickname = rs.get(u.nickname),
    profilePicture = rs.get(u.profilePicture),
    surname = rs.get(u.surname)
  )

  val u = User.syntax("tu")

  override val autoSession = AutoSession

  def find(id: Long)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.eq(u.id, id)
    }.map(User(u.resultName)).single.apply()
  }

  def findByUsername(username: String)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.eq(u.nickname, username)
    }.map(User(u.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[User] = {
    withSQL(select.from(User as u)).map(User(u.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(User as u)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[User] = {
    withSQL {
      select.from(User as u).where.append(where)
    }.map(User(u.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[User] = {
    withSQL {
      select.from(User as u).where.append(where)
    }.map(User(u.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(User as u).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
              id: Long,
              email: Option[String] = None,
              name: Option[String] = None,
              nickname: Option[String] = None,
              profilePicture: Option[String] = None,
              surname: Option[String] = None)(implicit session: DBSession = autoSession): User = {
    withSQL {
      insert.into(User).namedValues(
        column.id -> id,
        column.email -> email,
        column.name -> name,
        column.nickname -> nickname,
        column.profilePicture -> profilePicture,
        column.surname -> surname
      )
    }.update.apply()

    User(
      id = id,
      email = email,
      name = name,
      nickname = nickname,
      profilePicture = profilePicture,
      surname = surname)
  }

  def batchInsert(entities: Seq[User])(implicit session: DBSession = autoSession): List[Int] = {
    val params: Seq[Seq[(Symbol, Any)]] = entities.map(entity =>
      Seq(
        'id -> entity.id,
        'email -> entity.email,
        'name -> entity.name,
        'nickname -> entity.nickname,
        'profilePicture -> entity.profilePicture,
        'surname -> entity.surname))
    SQL(
      """insert into t_user(
      id,
      email,
      name,
      nickname,
      profile_picture,
      surname
    ) values (
      {id},
      {email},
      {name},
      {nickname},
      {profilePicture},
      {surname}
    )""").batchByName(params: _*).apply[List]()
  }

  def save(entity: User)(implicit session: DBSession = autoSession): User = {
    withSQL {
      update(User).set(
        column.id -> entity.id,
        column.email -> entity.email,
        column.name -> entity.name,
        column.nickname -> entity.nickname,
        column.profilePicture -> entity.profilePicture,
        column.surname -> entity.surname
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: User)(implicit session: DBSession = autoSession): Int = {
    withSQL {
      delete.from(User).where.eq(column.id, entity.id)
    }.update.apply()
  }

}
