name := "set"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies := List(
  "org.scalactic"  %% "scalactic"  % "3.0.5",
  "org.typelevel"  %% "cats-core"  % "1.6.0",
  "org.scalatest"  %% "scalatest"  % "3.0.5"  % Test,
  "org.scalacheck" %% "scalacheck" % "1.14.0" % Test
)