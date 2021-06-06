#!/usr/bin/env groovy

package ru.kerneltrap

import com.google.common.base.Preconditions

class TFCOrganization {

  public final String Name
  TFCOrganization(String name) {
    this.Name = name
  }
}