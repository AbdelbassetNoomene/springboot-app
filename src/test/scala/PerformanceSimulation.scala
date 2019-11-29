import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import scala.concurrent.duration._

class PerformanceSimulation extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("http://localhost:8080")

  object ListUsers {
    val get: ChainBuilder = exec(http("users")
      .get("/users"))
  }

  val usersScenario: ScenarioBuilder = scenario("users-scenario")
    .exec(ListUsers.get)

  setUp(usersScenario.inject(
    incrementUsersPerSec(10)
      .times(3)
      .eachLevelLasting(5 seconds)
      .separatedByRampsLasting(5 seconds)
      .startingFrom(5)
  )).protocols(httpProtocol)
    .assertions(global.successfulRequests.percent.is(100))

}
