package error

abstract class CommonHttpError(val message: String,val status: Int) extends Exception{

}
