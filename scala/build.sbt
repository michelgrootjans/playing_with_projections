name := "scala"

version := "1.0"

scalaVersion := "2.12.2"

mainClass in (Compile,run) := Some("be.playing.withrojections.Main")

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"