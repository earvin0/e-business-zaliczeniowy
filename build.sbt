name := """play-scala-slick-example"""

version := "2.6.x"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"
resolvers += Resolver.jcenterRepo

resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
crossScalaVersions := Seq("2.11.12", "2.12.4")
routesImport += "utils.route.Binders._"
libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play-slick" % "3.0.3"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3"
libraryDependencies += "org.xerial"        %  "sqlite-jdbc" % "3.21.0"
//libraryDependencies += "slick.driver.SQLiteDriver" %%

//libraryDependencies += "com.h2database" % "h2" % "1.4.196"
TwirlKeys.templateImports := Seq()
libraryDependencies += specs2 % Test
libraryDependencies ++= Seq(
  "com.mohiva" %% "play-silhouette" % "5.0.5",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "5.0.5",
  "com.mohiva" %% "play-silhouette-persistence" % "5.0.5",
  "com.mohiva" %% "play-silhouette-crypto-jca" % "5.0.5",
  "org.webjars" %% "webjars-play" % "2.6.3",
  "org.webjars" % "bootstrap" % "3.3.7-1" exclude("org.webjars", "jquery"),
  "org.webjars" % "jquery" % "3.2.1",
  "net.codingwell" %% "scala-guice" % "4.1.0",
  "com.iheart" %% "ficus" % "1.4.3",
  "com.typesafe.play" %% "play-mailer" % "6.0.1",
  "com.typesafe.play" %% "play-mailer-guice" % "6.0.1",
  "com.enragedginger" %% "akka-quartz-scheduler" % "1.6.1-akka-2.5.x",
  "com.adrianhurt" %% "play-bootstrap" % "1.4-P26-B3-SNAPSHOT",
  "com.mohiva" %% "play-silhouette-testkit" % "5.0.5" % "test",
  specs2 % Test,
  ehcache,
  guice,
  filters

)
resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"