
# spark-aws-reader
A simple Scala Spark application, with the proper dependencies for Scala 2.11.12 and Spark 2.4.6, that reads from AWS S3 buckets.

-------------------------------------------
### Connecting to S3
In order to connect to S3, you have to put a core-site.xml file in the /src/main/resources folder.


core-site.xml file should like something like this:

```<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
    <property>
        <name>fs.s3a.access.key</name>
        <value>accesskeystring</value>
    </property>
    <property>
        <name>fs.s3a.secret.key</name>
        <value>secretkeystring</value>
    </property>
</configuration>
```

**Never commit your core-site.xml file to github!**