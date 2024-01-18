import java.time.LocalDate
import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}


object PrivateExecutionContext {
  val executor = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService((executor))
}

object Main {

  import slick.jdbc.PostgresProfile.api._
  import PrivateExecutionContext._

  val AvengersAgeOfUltron = Movie(1L, "Avengers: Age Of Ultron", LocalDate.of(2016, 8, 22), 157)
  val JourneyToTheMysteriousIsland = Movie(2L, "Journey To The Mysterious Island", LocalDate.of(2012, 10, 15), 192)

  def InsertMovie(): Unit = {
    val queryDescription = SlickTables.movieTable += JourneyToTheMysteriousIsland
    val futureId: Future[Int] = Connection.db.run(queryDescription)
    futureId.onComplete {
      case Success(newMovieId) => println(s"Query was Successful, new ID is $newMovieId")
      case Failure(ex) => println(s"Query failed, reason: $ex")
    }

    Thread.sleep(1000)
  }

  def ReadAllMovies(): Unit = {
    val resultFuture: Future[Seq[Movie]] = Connection.db.run(SlickTables.movieTable.result)
    resultFuture.onComplete {
      case Success(movies) => println(s"Fetched: ${movies.mkString(",")}")
      case Failure(exception) => println(s"Fetching failed : $exception")
    }
    Thread.sleep(1000)
  }

  def ReadSomeMovies(): Unit = {
    val resultFuture: Future[Seq[Movie]] = Connection.db.run(SlickTables.movieTable.filter(_.name.like("%Journey%")).result) // same as select * from table_name where name like journey
    resultFuture.onComplete {
      case Success(movies) => println(s"Fetched: ${movies.mkString(",")}")
      case Failure(exception) => println(s"Fetching failed : $exception")
    }
    Thread.sleep(1000)
  }

  def Update(): Unit = {
    val queryDescription = SlickTables.movieTable.filter(_.id === 1L).update(AvengersAgeOfUltron.copy(lengthInMin = 177))

    val futureId: Future[Int] = Connection.db.run(queryDescription)
    futureId.onComplete {
      case Success(newMovieId) => println(s"Query was Successful, new ID is $newMovieId")
      case Failure(ex) => println(s"Query failed, reason: $ex")
    }

    Thread.sleep(1000)
  }

  def Delete(): Unit = {
    Connection.db.run(SlickTables.movieTable.filter(_.name.like("%Journey%")).delete) // same as select * from table_name
    Thread.sleep(1000)
  }

  def main(args: Array[String]): Unit = {
    InsertMovie()
    ReadAllMovies()
    ReadSomeMovies()
    Update()
    Delete()
  }
}