import com.typesafe.sbt.SbtScalariform.scalariformSettings
import sbt._
import Keys._

object ApplicationBuild extends Build {

  val scalatest = "org.scalatest" %% "scalatest" % "2.1.5" % "test"

  lazy val plugin = Project (
    id = "plugin",
    base = file ("plugin")
  ).settings(
    Seq(
      name := "play-flyway",
      organization := "com.github.tototoshi",
      version := "1.2.2-2.2.x",
      scalaVersion := "2.10.4",
      //crossScalaVersions := scalaVersion.value :: "2.11.1" :: Nil,
      resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
      libraryDependencies ++= Seq(
        "com.typesafe.play" %% "play" % "2.2.3" % "provided",
        "org.flywaydb" % "flyway-core" % "3.2.1",
        scalatest
      ),
      scalacOptions ++= Seq("-language:_", "-deprecation")
    ) ++ scalariformSettings ++ publishingSettings :_*
  )

  val appDependencies = Seq(
    "com.h2database" % "h2" % "[1.3,)",
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "org.scalikejdbc" %% "scalikejdbc-play-plugin" % "2.2.0" % "test",
    scalatest
  )

  val playAppName = "playapp"
  val playAppVersion = "1.0-SNAPSHOT"

  lazy val playapp = play.Project(
    playAppName,
    playAppVersion,
    appDependencies,
    path = file("playapp")
  ).settings(scalariformSettings:_*)
  .settings(
    resourceDirectories in Test += baseDirectory.value / "conf",
    scalaVersion := "2.10.4"
  )
  .dependsOn(plugin)
  .aggregate(plugin)

  val publishingSettings = Seq(
    publishMavenStyle := true,
    publishTo <<= version { (v: String) => _publishTo(v) },
    publishArtifact in Test := false,
    pomExtra := _pomExtra
  )

  def _publishTo(v: String) = {
    val nexus = "https://oss.sonatype.org/"
    if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
    else Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }

  val _pomExtra =
    <url>http://github.com/tototoshi/play-flyway</url>
    <licenses>
      <license>
        <name>Apache License, Version 2.0</name>
        <url>http://github.com/tototoshi/play-flyway/blob/master/LICENSE.txt</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:tototoshi/play-flyway.git</url>
      <connection>scm:git:git@github.com:tototoshi/play-flyway.git</connection>
    </scm>
    <developers>
      <developer>
        <id>tototoshi</id>
        <name>Toshiyuki Takahashi</name>
        <url>http://tototoshi.github.com</url>
      </developer>
    </developers>

}
