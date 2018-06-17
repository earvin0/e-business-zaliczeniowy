// The Play plugin

resolvers += Resolver.sonatypeRepo("snapshots")
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.13")
addSbtPlugin("eu.unicredit" % "sbt-play-npm" % "0.1-SNAPSHOT")
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.2")