package com.learn.auth.jwt.jwttokenauthorization.models.core;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements Serializable {

  private int responseCode;
  private List<String> responseMessages;
  private T items;

  public Response() {
    this.responseMessages = new ArrayList<>();
  }

  public Response(T items) {
    this.items = items;
    this.responseMessages = new ArrayList<>();
  }

  public int getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(int responseCode) {
    this.responseCode = responseCode;
  }

  public List<String> getResponseMessages() {
    return responseMessages;
  }

  public void setResponseMessages(List<String> responseMessages) {
    this.responseMessages = responseMessages;
  }

  public T getItems() {
    return items;
  }

  public void setItems(T items) {
    this.items = items;
  }
}