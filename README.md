# GAMERFUL

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description

Gamerful is a game recommendation and review app. This app gives you fast and easy access to details and add reviews of different variety of games. This app is also a great tool for any parents who wants to buy games for their children, as our platform provides reviews from perspective of both parents and children.

### App Evaluation
- **Category:** Gaming / Review
- **Mobile:** This app would be primarily developed for mobile but would be viable on the computer such as reddit. Functionality wouldn't be limited to mobile devices, however the mobile version could have more features.
- **Story:** Recommends games by category and user intrest. The user can create and view reviews on the games. Reviews are divided into what parents say and what kids say about the game.
- **Market:** Any individual could choose to use this app, although the target demographic would be parents and those not familiar with games.
- **Habit:** This app could be used frequently especially around holidays as this is when more parents purchase games for their children.
- **Scope:** First we will start with recommending games based on user input. Then this could evolve into adding and viewing reviews.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

 * User can sign up to create a new account using Parse authentication. User can add the following details:
   + Profile picture
   + Username
   + Password
   + Category: Parent or Child
 * User can log in and log out of his or her account.
 * The current signed in user is persisted across app restarts.
 * User will be able to see bottom navigation bar with following tabs:
   + Home
   + Search
   + Review page
   + Profile Page
 * In home page user will be able to browse different through list of games.
 * In search page user will be able to search for a specific game or game review.
 * On review page user will be able to see or post game review.
 * On profile page user will be able to edit his/her profile, see their posted reviews and logout.
 * User will be able to click on a game to see its details. Details page will include the following:
   + Game related images
   + Game title / description
   + YouTube trailer
   + Game info (release date, publisher, user rating, ESRB rating, etc)
   + Direct link for where to buy the game?
   + User will be able to post a rating.
 * Make an app logo.


**Optional Nice-to-have Stories**

* User will be able to use voice search in search bar.
* Youtube or Twitch gameplay videos available to watch for games.

### 2. Screen Archetypes

* Login Screen
   * User will be able to login with their account and also will have the option to signup.
* Signup Screen
   * User will be able to make a new account.
   * User can add the following details:
   + Profile picture
   + Username
   + Password
   + Category: Parent or Child
* Home Page Screen
   * User will be able to browse different through list of games.
   * Different categories of games will be presented to the user based on the gaming platform.
* Search Page Screen
   * User will be presented with different variety of game genres to choose from.
   * User will be able to search for a specific game or game review.
* Review Page Screen
   * User will be able to see or post game review.
   * User will also have the option to choose between what parents say and what kids say about the game.
* Profile Page Screen
   * User will be able to edit his/her profile, see their posted reviews and logout.   

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home Tab
* Search Tab
* Reviews Tab
* Profile Tab

**Flow Navigation** (Screen to Screen)

* [list first screen here]
   * [list screen navigation here]
   * ...
* [list second screen here]
   * [list screen navigation here]
   * ...

## Wireframes
<img src="https://github.com/CS388-Group-Project-App/Gamerful/blob/master/Wireframe.jpg" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
