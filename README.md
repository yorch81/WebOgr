# WebOgr #

## Description ##
Web Tool for import and export ESRI Shapefiles to Databases.

## Requirements ##
* [Java](https://www.java.com/es/download/)
* [Spark](http://www.sparkjava.com/)
* [ogr2ogr](http://www.gdal.org/ogr2ogr.html)

## Developer Documentation ##
Execute mvn javadoc:javadoc.

## Installation ##
Create configuration file with the next structure:

~~~

dbtype=1 MSSQLSERVER 2 MYSQL 3 POSTGIS
hostname=localhost (or localhost\INSTANCE_OF_SQL_SERVER)
user= (User of SQL Server/MYSQL/PostGis)
password= (Password of User)
dbname= (DataBase Name)
dbport= (DataBase Port)
port=8080 (Web Tool Port)
basedir= (Directory Base)
appuser= (Application User)
apppassword= (Application Password)

~~~

Generate and execute jar.

## Notes ##
This application is a wrapper of ogr2ogr.

## References ##
http://www.gdal.org/ogr2ogr.html

## Donate ##
[![Donate](https://img.shields.io/badge/Donate-PayPal-green.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=GXT4C7UZ3HFA8)

P.D. Let's go play !!!







