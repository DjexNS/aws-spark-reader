package com.djex

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.sedona.sql.utils.SedonaSQLRegistrator
import org.apache.spark.serializer.KryoSerializer
import org.apache.sedona.viz.core.Serde.SedonaVizKryoRegistrator
import org.apache.sedona.viz.sql.utils.SedonaVizRegistrator
import org.apache.spark.sql.SparkSession

object Application {

  val configSpark: Config = ConfigFactory.load().getConfig("application.spark")
  val sparkCores: String = configSpark.getString("master")

  lazy val spark: SparkSession = {
    SparkSession.builder()
      .config("spark.speculation","false")
      .master(s"$sparkCores")
      .appName("AwsSparkReader")
      .config("spark.serializer", classOf[KryoSerializer].getName)
      .config("spark.kryo.registrator", classOf[SedonaVizKryoRegistrator].getName)
      .getOrCreate()
  }

  SedonaSQLRegistrator.registerAll(spark)
  SedonaVizRegistrator.registerAll(spark)

  def main(args: Array[String]): Unit = {
    val initialDataframe = spark
      .read
      .option("header", true)
      .text(s"file:///home/djex/workspace/functor/scala-spark-s3-reader/src/main/resources/testpoint.csv")

    initialDataframe
      .show(1000, false)

  }

}
