package be.playing.withrojections

import be.playing.withrojections.utils.JsonElement

import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    if (args.length != 1) throw new Exception("Usage : Main 'path/to/file.json'")

    val fileName = args(0)

    if (!fileName.endsWith(".json")) throw new Exception("Usage : Main 'path/to/file.json'")

    val content = Source.fromFile(fileName).mkString

    // Lightweight library : https://gist.github.com/piotrga/5233317
    val events = JsonElement.parse(content).get.asArray

    println("Number of events " + CountEventProjection(events))
  }
}

object CountEventProjection {
  def apply(events: List[JsonElement]) = events.map(x => 1).sum
}
