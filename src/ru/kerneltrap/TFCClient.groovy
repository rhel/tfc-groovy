#!/usr/bin/env groovy

package ru.kerneltrap

import com.google.common.base.Preconditions
import groovy.json.JsonBuilder
import java.nio.charset.StandardCharsets
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.entity.ByteArrayEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils

class TFCClient {

  protected final String baseURL = "https://app.terraform.io/api/v2"
  protected final String bearerToken
  TFCClient(String bearerToken) {
    this.bearerToken = bearerToken
  }

  TFCOrganization getOrganization(String name) {
    Preconditions.checkArgument(bearerToken != null && !bearerToken.empty, "bearerToken must not be null or empty")
    Preconditions.checkArgument(name != null && !name.empty, "name must not be null or empty")

    HttpClient httpClient = new DefaultHttpClient()
    HttpGet httpGet = new HttpGet(
      sprintf("%s/organizations/%s", baseURL, name)
    )
    httpGet.setHeader('Authorization', sprintf('Bearer %s', bearerToken))
    httpGet.setHeader('Content-Type', 'application/vnd.api+json')
    HttpResponse httpResponse = httpClient.execute(httpGet)
    return new TFCOrganization('example')
  }
}