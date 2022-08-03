name := "aws-spark-reader"

version := "0.1"

scalaVersion := "2.13.8"


resolvers ++= Seq(
  "Typesafe" at "https://repo.typesafe.com/typesafe/releases/",
  "Java.net Maven2 Repository" at "https://download.java.net/maven/2/",
  "IO Spring" at "https://repo.spring.io/plugins-release/"
)

libraryDependencies ++= {
  val sparkVer = "3.3.0"
  val hadoopVer = "3.3.3"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVer % Provided,
    "org.apache.spark" %% "spark-sql" % sparkVer,
    "org.apache.hadoop" % "hadoop-mapreduce-client-core" % hadoopVer,
    "org.apache.hadoop" % "hadoop-common" % hadoopVer,
    "org.apache.hadoop" % "hadoop-aws" % hadoopVer,
    "commons-httpclient" % "commons-httpclient" % "3.1",
    "commons-io" % "commons-io" % "2.11.0",
    "com.amazonaws" % "aws-java-sdk" % "1.12.273",
    "com.google.guava" % "guava" % "31.1-jre",
    "com.typesafe" % "config" % "1.4.2"
  )
}

assembly / assemblyJarName := "aws-spark-reader"
assembly / mainClass := Some("com.djex.Application")

assembly / assemblyShadeRules := Seq(
  ShadeRule.rename("com.google.**" -> "shadeio.@1").inAll
)

assembly / assemblyMergeStrategy := {
  case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
  case m if m.startsWith("META-INF") => MergeStrategy.discard
  case PathList("org", "apache", xs@_*) => MergeStrategy.first
  case _ => MergeStrategy.first
}

run / fork := true
run / javaOptions ++= Seq(
  "-Dlog4j.debug=true",
  "-Dlog4j.configuration=log4j.properties")
outputStrategy := Some(StdoutOutput)

Test / fork := true
Test / parallelExecution := false