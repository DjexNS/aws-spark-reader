package com.djex

import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession

object Application {

  val configSpark: Config = ConfigFactory.load().getConfig("application.spark")
  val sparkCores: String = configSpark.getString("master")

  lazy val spark: SparkSession = {
    SparkSession.builder().config("spark.speculation","false").master(s"$sparkCores").appName("AwsSparkReader").getOrCreate()
  }

  def main(args: Array[String]): Unit = {
    val initialDataframe = spark
      .read
      .option("header", true)
      .csv(s"s3a://BUCKET-NAME/FILENAME")

    initialDataframe
      .show(1000, false)
  }

}
