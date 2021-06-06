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

  protected final String BaseURL = "https://app.terraform.io/api/v2"
  protected final String BearerToken
  TFCClient(String bearerToken) {
    this.BearerToken = bearerToken
  }

  TFCOrganization getOrganization(String name) {
    Preconditions.checkArgument(bearerToken != null && !bearerToken.isEmpty(), "bearerToken must not be null or empty")
    Preconditions.checkArgument(name != null && !name.isEmpty(), "name must not be null or empty")

    HttpClient httpClient = new DefaultHttpClient()
    HttpGet httpGet = new HttpGet(
      sprintf("%s/organizations/%s", BaseURL, name)
    )
    httpGet.setHeader('Authorization', sprintf('Bearer %s', BearerToken))
    httpGet.setHeader('Content-Type', 'application/vnd.api+json')
    //HttpEntity httpEntity = new ByteArrayEntity(payload)
    //httpPatch.setEntity(httpEntity)
    //HttpResponse httpResponse = httpClient.execute(httpPatch)
    return TFCOrganization('example')
  }
}