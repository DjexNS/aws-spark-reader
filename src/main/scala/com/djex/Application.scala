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
      .csv(s"s3a://dejan-dekic-test-us-east-1/dataset=data_listings/2021/08/totalListing/part-00000-tid-5332801278252446004-e9724cd7-6ccf-482a-b7d9-2c9d6add9c86-62193-1-c000.csv.gz")

    initialDataframe
      .show(1000, false)

//    println("First SparkContext:")
//    println("APP Name :" + spark.sparkContext.appName)
//    println("Deploy Mode :" + spark.sparkContext.deployMode)
//    println("Master :" + spark.sparkContext.master)

  }

}
