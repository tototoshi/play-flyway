/*
 * Copyright 2013 Toshiyuki Takahashi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.tototoshi.play2.flyway

import play.api._

class ConfigReader(app: Application) extends UrlParser {

  private def getAllDatabaseNames: Seq[String] = (for {
    config <- app.configuration.getConfig("db").toList
    dbName <- config.subKeys
  } yield {
    dbName
  }).distinct

  def getDatabaseConfigurations: Map[String, DatabaseConfiguration] = {
    (for {
      dbName <- getAllDatabaseNames
    } yield {
      val (url, parsedUser, parsedPass) = app.configuration.getString(s"db.${dbName}.url").map(parseUrl(_)).getOrElse(
        throw new MigrationConfigurationException(s"db.${dbName}.url is not set.")
      )
      val driver = app.configuration.getString(s"db.${dbName}.driver").getOrElse(
        throw new MigrationConfigurationException(s"db.${dbName}.driver is not set.")
      )
      val user = parsedUser.orElse(app.configuration.getString(s"db.${dbName}.user")).orNull
      val password = parsedPass
        .orElse(app.configuration.getString(s"db.${dbName}.password"))
        .orElse(app.configuration.getString(s"db.${dbName}.pass"))
        .orNull
      dbName -> DatabaseConfiguration(driver, url, user, password)
    }).toMap

  }

  def getOutOfOrder: Boolean = {
    app.configuration.getBoolean("flyway.out-of-order-migrations") getOrElse true
  }

}
