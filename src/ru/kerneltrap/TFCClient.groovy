#!/usr/bin/env groovy
package ru.kerneltrap

import com.google.common.base.Preconditions
//import groovy.json.JsonBuilder
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

    Preconditions.checkArgument(bearerToken != null && !bearerToken.empty, 'bearerToken must not be null or empty')
    Preconditions.checkArgument(name != null && !name.empty, 'name must not be null or empty')

    httpClient = new DefaultHttpClient()
    httpGet = new HttpGet(sprintf('%s/organizations/%s', baseURL, name))
    httpGet.setHeader('Authorization', sprintf('Bearer %s', bearerToken))
    httpGet.setHeader('Content-Type', 'application/vnd.api+json')
    httpResponse = httpClient.execute(httpGet)
    if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
      throw new RuntimeException(
        IOUtils.toString(
          httpResponse.getStatusLine().getContent(), 
          StandardCharsets.UTF_8
        )
      )
    }
    return new TFCOrganization('example')
  }

}
