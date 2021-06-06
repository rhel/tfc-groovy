#!/usr/bin/env groovy
package ru.kerneltrap

import com.google.common.base.Preconditions
import groovy.json.JsonSlurper
import java.nio.charset.StandardCharsets
import org.apache.commons.httpclient.HttpStatus
import org.apache.commons.io.IOUtils
//import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

//import org.apache.http.util.EntityUtils

class TFCClient {

  protected final String baseURL = 'https://app.terraform.io/api/v2'
  protected final String bearerToken
  TFCClient(String bearerToken) {
    this.bearerToken = bearerToken
  }

  TFCOrganization getOrganization(String name) {
    HttpClient httpClient
    HttpGet httpGet
    HttpResponse httpResponse
    JsonSlurper jsonSlurper

    Preconditions.checkArgument(bearerToken != null && !bearerToken.empty, 'bearerToken must not be null or empty')
    Preconditions.checkArgument(name != null && !name.empty, 'name must not be null or empty')

    httpClient = new DefaultHttpClient()
    httpGet = new HttpGet(sprintf('%s/organizations/%s', baseURL, name))
    httpGet.setHeader('Authorization', sprintf('Bearer %s', bearerToken))
    httpGet.setHeader('Content-Type', 'application/vnd.api+json')
    httpResponse = httpClient.execute(httpGet)
    if (httpResponse.statusLine.statusCode != HttpStatus.SC_OK) {
      throw new Exception(
        IOUtils.toString(
          httpResponse.entity.content,
          StandardCharsets.UTF_8
        )
      )
    }
    Object obj = new JsonSlurper().parseText(
      IOUtils.toString(
        httpResponse.entity.content,
        StandardCharsets.UTF_8
      )
    )
    echo obj
    return new TFCOrganization('example')
  }
}