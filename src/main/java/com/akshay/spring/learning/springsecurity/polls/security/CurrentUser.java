package com.akshay.spring.learning.springsecurity.polls.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/*
*
* Spring security provides an annotation called @AuthenticationPrincipal to access the currently authenticated user in the controllers.

The following CurrentUser annotation is a wrapper around @AuthenticationPrincipal annotation.

We’ve created a meta-annotation so that we don’t get too much tied up of with Spring Security related annotations everywhere in our project.

This reduces the dependency on Spring Security. So if we decide to remove Spring Security from our project,

we can easily do it by simply changing the CurrentUser annotation

*/
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {
}
