name := "aws-spark-reader"

version := "0.1"

scalaVersion := "2.11.12"


resolvers ++= Seq(
  "Typesafe" at "https://repo.typesafe.com/typesafe/releases/",
  "Java.net Maven2 Repository" at "https://download.java.net/maven/2/",
  "IO Spring" at "https://repo.spring.io/plugins-release/"
)

libraryDependencies ++= {
  val sparkVer = "2.4.6"
  val hadoopVer = "2.7.3"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVer % Provided,
    "org.apache.spark" %% "spark-sql" % sparkVer,
    "org.apache.hadoop" % "hadoop-mapreduce-client-core" % hadoopVer,
    "org.apache.hadoop" % "hadoop-common" % hadoopVer,
    "org.apache.hadoop" % "hadoop-aws" % hadoopVer,
    "commons-httpclient" % "commons-httpclient" % "3.1",
    "commons-io" % "commons-io" % "2.6",
    "com.amazonaws" % "aws-java-sdk" % "1.7.4",
    "com.google.guava" % "guava" % "23.6-jre",
    "com.typesafe" % "config" % "1.3.2"
  )
}

assemblyJarName in assembly := "aws-spark-reader"
mainClass in assembly := Some("com.djex.Application")

assemblyShadeRules in assembly := Seq(
  ShadeRule.rename("com.google.**" -> "shadeio.@1").inAll
)

assemblyMergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
  case m if m.startsWith("META-INF") => MergeStrategy.discard
  case PathList("org", "apache", xs@_*) => MergeStrategy.first
  case _ => MergeStrategy.first
}

fork in run := true
javaOptions in run ++= Seq(
  "-Dlog4j.debug=true",
  "-Dlog4j.configuration=log4j.properties")
outputStrategy := Some(StdoutOutput)

fork in Test := true
parallelExecution in Test := false