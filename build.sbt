import BuildHelper._

inThisBuild(
  List(
    name := "zio-playground",
    organization := "dev.zio",
    homepage := Some(url("https://github.com/saraiva132/zio-playground"))
  )
)

ThisBuild / publishTo := sonatypePublishToBundle.value

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")
addCommandAlias("testRelease", ";set every isSnapshot := false;+clean;+compile")

lazy val root = project
  .in(file("."))
  .settings(skip in publish := true)
  .settings(
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio"     % zioVersion,
      "dev.zio" %% "zio-nio" % zioNioVersion
    )
  )
