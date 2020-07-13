package service

trait CrudService[REQ, RES] {

  def getAll: Seq[RES]

  def getById(req: REQ): RES

  def add(req: REQ): RES

  def update(req: REQ): RES

  def delete(req: REQ): RES

}
