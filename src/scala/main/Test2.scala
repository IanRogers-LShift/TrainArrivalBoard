trait Display {
  def send_arrival(text : String) : Unit
  def send_following_arrivals(arrivals : Seq[String]) : Unit
}

case class FileDisplay(file : String) extends Display {

  override def send_arrival(text: String): Unit = {
    scala.tools.nsc.io.File(file).appendAll(text + "\n")
  }

  override def send_following_arrivals(arrivals: Seq[String]) : Unit = {
    scala.tools.nsc.io.File(file).appendAll(arrivals.mkString("\n") + "\n")
  }
}

case object ConsoleDisplay extends Display {

  override def send_arrival(text: String): Unit = {
    println(text)
  }

  override def send_following_arrivals(arrivals: Seq[String]) : Unit = {
    println(arrivals.mkString("\n"))
  }
}

object Test2 {

  import scalaj.http._
  import net.liftweb.json._


  def arrivals(get : String => String, lineId : String, stopPointId : String): Seq[(BigInt, String)] = {

    val url = "https://api.tfl.gov.uk/Line/" + lineId + "/Arrivals/" + stopPointId
    val json: JValue = parse(get(url))

    for { JObject(arrival) <- json
          JField("timeToStation", JInt(expected)) <- arrival
          JField("towards", JString(destination)) <- arrival }
              yield (expected, destination)
  }

  def format(in : (BigInt, String)) : String = {
    in._2 + " " + in._1 / 60 + ":" + in._1 % 60
  }

  def formattedArrivals(get : String => String, lineId : String, stopPointId : String): Seq[String] =
    arrivals(get, lineId, stopPointId).sortBy(_._1).map(format)

  def test(): Unit = {

    // generated from: https://api.tfl.gov.uk/Line/bakerloo/Arrivals/940GZZLUWLO
    val testData =
      """[{"$type":"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities","id":"-1437944078","operationType":1,"vehicleId":"204","naptanId":"940GZZLUWLO","stationName":"Waterloo Underground Station","lineId":"bakerloo","lineName":"Bakerloo","platformName":"Southbound - Platform 4","direction":"inbound","bearing":"","destinationNaptanId":"940GZZLUEAC","destinationName":"Elephant & Castle Underground Station","timestamp":"2017-10-04T13:01:07Z","timeToStation":178,"currentLocation":"Between Piccadilly Circus and Charing Cross","towards":"Elephant and Castle","expectedArrival":"2017-10-04T13:04:05Z","timeToLive":"2017-10-04T13:04:05Z","modeName":"tube","timing":{"$type":"Tfl.Api.Presentation.Entities.PredictionTiming, Tfl.Api.Presentation.Entities","countdownServerAdjustment":"00:00:00","source":"0001-01-01T00:00:00","insert":"0001-01-01T00:00:00","read":"2017-10-04T13:00:42.269Z","sent":"2017-10-04T13:01:07Z","received":"0001-01-01T00:00:00"}},{"$type":"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities","id":"1558825038","operationType":1,"vehicleId":"210","naptanId":"940GZZLUWLO","stationName":"Waterloo Underground Station","lineId":"bakerloo","lineName":"Bakerloo","platformName":"Southbound - Platform 4","direction":"inbound","bearing":"","destinationNaptanId":"940GZZLUEAC","destinationName":"Elephant & Castle Underground Station","timestamp":"2017-10-04T13:01:07Z","timeToStation":988,"currentLocation":"At Warwick Avenue Platform 2","towards":"Elephant and Castle","expectedArrival":"2017-10-04T13:17:35Z","timeToLive":"2017-10-04T13:17:35Z","modeName":"tube","timing":{"$type":"Tfl.Api.Presentation.Entities.PredictionTiming, Tfl.Api.Presentation.Entities","countdownServerAdjustment":"00:00:00","source":"0001-01-01T00:00:00","insert":"0001-01-01T00:00:00","read":"2017-10-04T13:00:42.332Z","sent":"2017-10-04T13:01:07Z","received":"0001-01-01T00:00:00"}},{"$type":"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities","id":"1955802015","operationType":1,"vehicleId":"213","naptanId":"940GZZLUWLO","stationName":"Waterloo Underground Station","lineId":"bakerloo","lineName":"Bakerloo","platformName":"Southbound - Platform 4","direction":"inbound","bearing":"","destinationNaptanId":"940GZZLUEAC","destinationName":"Elephant & Castle Underground Station","timestamp":"2017-10-04T13:01:07Z","timeToStation":1348,"currentLocation":"At Queen's Park Platform 2","towards":"Elephant and Castle","expectedArrival":"2017-10-04T13:23:35Z","timeToLive":"2017-10-04T13:23:35Z","modeName":"tube","timing":{"$type":"Tfl.Api.Presentation.Entities.PredictionTiming, Tfl.Api.Presentation.Entities","countdownServerAdjustment":"00:00:00","source":"0001-01-01T00:00:00","insert":"0001-01-01T00:00:00","read":"2017-10-04T13:00:42.347Z","sent":"2017-10-04T13:01:07Z","received":"0001-01-01T00:00:00"}},{"$type":"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities","id":"-1304210267","operationType":1,"vehicleId":"221","naptanId":"940GZZLUWLO","stationName":"Waterloo Underground Station","lineId":"bakerloo","lineName":"Bakerloo","platformName":"Southbound - Platform 4","direction":"inbound","bearing":"","destinationNaptanId":"940GZZLUEAC","destinationName":"Elephant & Castle Underground Station","timestamp":"2017-10-04T13:01:07Z","timeToStation":688,"currentLocation":"At Marylebone Platform 2","towards":"Elephant and Castle","expectedArrival":"2017-10-04T13:12:35Z","timeToLive":"2017-10-04T13:12:35Z","modeName":"tube","timing":{"$type":"Tfl.Api.Presentation.Entities.PredictionTiming, Tfl.Api.Presentation.Entities","countdownServerAdjustment":"00:00:00","source":"0001-01-01T00:00:00","insert":"0001-01-01T00:00:00","read":"2017-10-04T13:00:42.316Z","sent":"2017-10-04T13:01:07Z","received":"0001-01-01T00:00:00"}},{"$type":"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities","id":"389799736","operationType":1,"vehicleId":"222","naptanId":"940GZZLUWLO","stationName":"Waterloo Underground Station","lineId":"bakerloo","lineName":"Bakerloo","platformName":"Southbound - Platform 4","direction":"inbound","bearing":"","destinationNaptanId":"940GZZLUEAC","destinationName":"Elephant & Castle Underground Station","timestamp":"2017-10-04T13:01:07Z","timeToStation":388,"currentLocation":"At Oxford Circus Platform 3","towards":"Elephant and Castle","expectedArrival":"2017-10-04T13:07:35Z","timeToLive":"2017-10-04T13:07:35Z","modeName":"tube","timing":{"$type":"Tfl.Api.Presentation.Entities.PredictionTiming, Tfl.Api.Presentation.Entities","countdownServerAdjustment":"00:00:00","source":"0001-01-01T00:00:00","insert":"0001-01-01T00:00:00","read":"2017-10-04T13:00:42.285Z","sent":"2017-10-04T13:01:07Z","received":"0001-01-01T00:00:00"}},{"$type":"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities","id":"-1303620443","operationType":1,"vehicleId":"231","naptanId":"940GZZLUWLO","stationName":"Waterloo Underground Station","lineId":"bakerloo","lineName":"Bakerloo","platformName":"Southbound - Platform 4","direction":"inbound","bearing":"","destinationNaptanId":"940GZZLUEAC","destinationName":"Elephant & Castle Underground Station","timestamp":"2017-10-04T13:01:07Z","timeToStation":508,"currentLocation":"Between Baker Street and Regents Park","towards":"Elephant and Castle","expectedArrival":"2017-10-04T13:09:35Z","timeToLive":"2017-10-04T13:09:35Z","modeName":"tube","timing":{"$type":"Tfl.Api.Presentation.Entities.PredictionTiming, Tfl.Api.Presentation.Entities","countdownServerAdjustment":"00:00:00","source":"0001-01-01T00:00:00","insert":"0001-01-01T00:00:00","read":"2017-10-04T13:00:42.3Z","sent":"2017-10-04T13:01:07Z","received":"0001-01-01T00:00:00"}},{"$type":"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities","id":"-1435126030","operationType":1,"vehicleId":"234","naptanId":"940GZZLUWLO","stationName":"Waterloo Underground Station","lineId":"bakerloo","lineName":"Bakerloo","platformName":"Northbound - Platform 3","direction":"outbound","bearing":"","destinationNaptanId":"940GZZLUQPS","destinationName":"Queen's Park Underground Station","timestamp":"2017-10-04T13:01:07Z","timeToStation":28,"currentLocation":"Between Lambeth North and Waterloo","towards":"Queen's Park","expectedArrival":"2017-10-04T13:01:35Z","timeToLive":"2017-10-04T13:01:35Z","modeName":"tube","timing":{"$type":"Tfl.Api.Presentation.Entities.PredictionTiming, Tfl.Api.Presentation.Entities","countdownServerAdjustment":"00:00:00","source":"0001-01-01T00:00:00","insert":"0001-01-01T00:00:00","read":"2017-10-04T13:00:42.254Z","sent":"2017-10-04T13:01:07Z","received":"0001-01-01T00:00:00"}},{"$type":"Tfl.Api.Presentation.Entities.Prediction, Tfl.Api.Presentation.Entities","id":"-1031858173","operationType":1,"vehicleId":"237","naptanId":"940GZZLUWLO","stationName":"Waterloo Underground Station","lineId":"bakerloo","lineName":"Bakerloo","platformName":"Southbound - Platform 4","direction":"inbound","bearing":"","destinationNaptanId":"940GZZLUEAC","destinationName":"Elephant & Castle Underground Station","timestamp":"2017-10-04T13:01:07Z","timeToStation":1048,"currentLocation":"At Maida Vale Platform 2","towards":"Elephant and Castle","expectedArrival":"2017-10-04T13:18:35Z","timeToLive":"2017-10-04T13:18:35Z","modeName":"tube","timing":{"$type":"Tfl.Api.Presentation.Entities.PredictionTiming, Tfl.Api.Presentation.Entities","countdownServerAdjustment":"00:00:00","source":"0001-01-01T00:00:00","insert":"0001-01-01T00:00:00","read":"2017-10-04T13:00:42.347Z","sent":"2017-10-04T13:01:07Z","received":"0001-01-01T00:00:00"}}]"""

    val next :: following = formattedArrivals(_ => testData, "940GZZLUWLO", "bakerloo")

    assert(following.length == 7)
    assert(next == "Queen's Park 0:28")
    assert(following(0) == "Elephant and Castle 2:58")
    assert(following(1) == "Elephant and Castle 6:28")
  }

  def main(args : Array[String]): Unit = {

    test()

//    val display = FileDisplay("arrivals.txt")
    val display = ConsoleDisplay

    val t = new java.util.Timer()

    val task = new java.util.TimerTask {
      def run() = {
        val next :: following = formattedArrivals(Http(_).asString.body, args(0), args(1))

        display.send_arrival("Next train: " + next)
        display.send_following_arrivals(following)
      }
    }

    t.schedule(task, 0L, 10000L)
  }
}


