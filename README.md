# GAMERFUL

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description

Gamerful is a video game recommendation and review app. This app lets users quickly and easily access video game ratings, reviews, descriptions, and other pertinent details. Users can leave ratings and reviews of video games that all users can access. Users can sort reviews by user type, including parent and child, to gain a clearer insight into the quality of a video game. Gamerful is an excellent tool for avid game enthusiasts or those just entering the market to make conscientious purchases for themselves, their friends, or their family.

### App Evaluation
- **Category:** Gaming / Review
- **Mobile:** This app would be primarily developed for mobile but would be viable on a computer such as Reddit. Functionality wouldn't be limited to mobile devices, however, the mobile version could have more features.
- **Story:** Recommends games by category and user interest. The user can create and view reviews on the games. Reviews are divided into what parents say and what kids say about the game.
- **Market:** Any individual could choose to use this app, although the target demographic would be parents and those not familiar with games.
- **Habit:** This app could be used frequently especially around holidays as this is when more parents purchase games for their children.
- **Scope:** First we will start with recommending games based on user input. Then this could evolve into adding and viewing reviews.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

 - [X] User can sign up to create a new account using Parse authentication. Users can add the following details:
   - [X] Profile picture
   - [X] Username
   - [X] Password
   - [X] Category: Parent or Child
 - [X] User can log in and log out of his or her account.
 - [X] The current signed-in user is persisted across app restarts.
 - [X] User will be able to see the bottom navigation bar with the following tabs:
   - [X] Home
   - [X] Search
   - [X] Review page
   - [X] Profile Page
 - [X] On the home page, the user will be able to browse through different lists of games.
 - [X] On the search page, the user will be able to search for a specific game or game review.
 - [X] On the review page, the user will be able to see and post game reviews.
 - [X] On the profile page, the user will be able to edit his/her profile, see their posted reviews, and log out.
 - [X] The user will be able to click on a game to see its details. The details page will include the following:
   - [X] Game related images
   - [X] Game title/description
   - [X] Game info (release date, publisher, user rating, ESRB rating, etc)
   - [X] Direct link for where to buy the game?
 - [X] User will be able to post a rating.
 - [X] Make an app logo/splash screen.


### 2. Screen Archetypes

* Login Screen
   * User will be able to log in with their account and also will have the option to sign up.
* Signup Screen
   * The user will be able to make a new account.
   * The user can add the following details:
   + Profile picture
   + Username
   + Password
   + Category: Parent or Child
* Home Page Screen
   * User will be able to browse through a list of different games.
   * Different categories of games will be presented to the user based on the gaming platform, ESRB rating, and other criteria.
* Search Page Screen
   * User will be presented with a variety of game genres to choose from.
   * User will be able to search for a specific game or game review.
* Review Page Screen
   * User will be able to see and post game reviews.
   * The user will also have the option to choose between what parents say and what kids say about the game.
* Profile Page Screen
   * User will be able to see their posted reviews, edit their profile, and log out.
* Details Screen
   * User will be able to see an in-depth review of a game.
* Post Review Screen
   * User will be able to create and post a comment on a specific game for other users to see.
* Edit Profile Page Screen
   * User will be able to edit their profile.

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home Tab
* Search Tab
* Reviews Tab
* Profile Tab

**Flow Navigation** (Screen to Screen)

* Login Screen
   * Home Screen
   * Signup Screen
* Signup Screen
   * Home Screen
   * Login Screen
* Navigation Screen (Can Switch between following Screens)
   * Home Screen
   * Search Tab
   * Reviews Tab
   * Profile Tab
* Home Screen
   * Details Screen
* Search Screen (Can Switch between following Screens)
   * Details Page
   * Post Review Page
* Review Page (Can Switch between following Screens)
   * Parents Reviews
   * Kids Reviews
* Profile Page
   * Edit Profile page
   * Login Screen (if logout)
* Details Page
   * Post Review Page
* Post Review Page
   * Review Page
   * Details Page
* Edit Profile Page
   * Profile Page

 
## Wireframes
<img src="https://github.com/CS388-Group-Project-App/Gamerful/blob/master/Wireframe.jpg" width=600>

### [BONUS] Digital Wireframes
<img src="https://github.com/CS388-Group-Project-App/Gamerful/blob/master/Digital%20WireFrame.PNG" width=600>

### [BONUS] Digital Mockup
<img src="https://github.com/CS388-Group-Project-App/Gamerful/blob/master/Digital1.PNG" width=600>
<img src="https://github.com/CS388-Group-Project-App/Gamerful/blob/master/Digital2.PNG" width=600>
<img src="https://github.com/CS388-Group-Project-App/Gamerful/blob/master/Digital3.PNG" width=600>

### [BONUS] Interactive Prototype
<img src="https://github.com/CS388-Group-Project-App/Gamerful/blob/master/Prototype.gif" width=600>


## Schema 
### Models
#### User

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |
   | username      | String   | stores the username of the user |
   | createdAt     | DateTime | date when a post is created (default field) |
   | password      | String   | stores the password of the user |
   | role          | Boolean  | If true, the user is signed in as a parent. If false, then the user is signed in as a child |
   | profilePic    | File     | image that user uploads as their profile picture|

#### Reviews

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | createdAt     | DateTime | date when a post is created (default field) |
   | updatedAt     | DateTime | date when post is last updated (default field) |
   | gameId        | String   | gameIds will be used to fetch game data from API |
   | comment       | String   | review of the game |
   | starRating    | Number   | number of star ratings that user posted to a game |
   | user          | Pointer to User| app user (parent or child)
   | image         | File     | image that user posts (game images)|
   
   
### Networking
#### List of network requests by screen
   - Login screen
      - (GET) query user form user table
      	```java
        ParseUser.logInBackground(username, password, new LogIncCallback() {
		@Override
		public void done(ParseUser user, ParseException e) {
			if (e==null){
			  //Save was done
			}else{
			  //Something went wrong
			  Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}

		});
        ```
	
         
   - Sign up screen
      - (POST) add user to user table
        ```java
        User user = new User();
        user.setUsername(username);
        user.setRole(role);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                        if (e == null) {
                // Hooray! Let them use the app now.
                        } else {
                                //Sign-up didn't succeed. Look at the ParseException
                                // to figure out what went wrong
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                }
        });
         ```

   - Edit user profile screen
      - (POST) update user information on user tabel
        ```swift
        ParseQuery<User> query = ParseQuery.getQuery(User.class);
        query.whereEqualTo(User.KEY_USERNAME, ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<User>() {
                @Override
                public void done(List<User> user, ParseException e) {
                        if (e == null) {
                                // User profile updated
                        }
                        else {
                                // Issue updateing user profile
                        }
                }
        });
         ```

   - Add review screen
      - (POST) add review to reviews table
        ```java
        Reviews review = new Review();

        review.setUser(currentUser);
        review.setGameId(gameId);
        review.setStarRating(starRating);
        review.setComment(comment);
        review.setImage(image);

        review.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                        if(e == null) {
                                // review posted
                        }
                        else {
                                // something went wrong
                        }
                }
        });
         ```
        

   - User page
      - (GET) query all reviews posted by user
        ```java
        ParseQuery<Reviews> query = ParseQuery.getQuery(Reviews.class);
        query.include(Reviews.KEY_USER);

        query.findInBackground(new FindCallback<Post>() {
                @Override
                public void done(List<Reviews> review, ParseException e) {
                        if(e == null) {
                                // Got list of all user reviews
                        }
                        else {
                                // Someting went worng
                        }
                }
        });
         ```

   - Reviews page
      - (GET) query all the reviews for a given game
        ```java
        ParseQuery<Reviews> query = ParseQuery.getQuery(Reviews.class);
        query.whereEqualTo("gameId", gameId);

        query.findInBackground(new FindCallback<Post>() {
                @Override
                public void done(List<Reviews> review, ParseException e) {
                        if(e == null) {
                                // Got list of all game reviews
                        }
                        else {
                                // Someting went worng
                        }
                }
        });
         ```

#### [OPTIONAL:] Existing API Endpoints
##### RAWG Api

- Base URL - [https://rawg.io/api](https://rawg.io/api)

   | HTTP Verbs | Endpoint     | Description |
   | ---------- | ------------ | ------------|
   | `GET`      | /games/lists/recent-games   | gets list of top games this week |
   | `GET`      | /games/lists/recent-games-future | gets list of top games upcoming week |
   | `GET`      | /games/lists/main | gets top-rated games |
   | `GET`      | /games/lists/popular   | gets all-time popular games |
   | `GET`      | /games?parent_platforms=1   | gets list of pc games |
   | `GET`      | /games?parent_platforms=2   | gets list of PS games |
   | `GET`      | /games?parent_platforms=3 | gets list of X Box games |
   | `GET`      | /genres     | gets list of all available genres |
   | `GET`      | /search?search={SEARCH_QUERY} | gets a list of games based on passed query |
   | `GET`      | /games/{GAME_ID}     | get details of a specific game |
   | `GET`      | /games/{GAME_ID}/stores     | get list of direct purchase link for a game |
   
### Sprint 1

**Required Must-have Stories**

 - [X] User can sign up to create a new account using Parse authentication. Users can add the following details:
   - [X] Profile picture
   - [X] Username
   - [X] Password
   - [X] Category: Parent or Child
 - [X] User can log in and log out of his or her account.
 - [X] The current signed-in user is persisted across app restarts.
 - [X] User will be able to see the bottom navigation bar with the following tabs:
   - [X] Home
   - [X] Search
   - [X] Review page
   - [X] Profile Page
 - [X] Make an app logo/splash screen.

<img src="https://github.com/CS388-Group-Project-App/Gamerful/blob/master/Sprint1-Walkthorugh.gif" width=350>

### Sprint 2

**Required Must-have Stories**

 - [X] On the home page user will be able to browse through different lists of games.
 - [X] On the profile page the user will be able to edit his/her profile, see their posted reviews, and log out.
 - [X] User will be able to click on a game to see its details. The details page will include the following:
   - [X] Game related images
   - [X] Game title/description
   - [X] Game info (release date, publisher, user rating, ESRB rating, etc)
   - [X] Direct link for where to buy the game?

<img src="https://github.com/CS388-Group-Project-App/Gamerful/blob/master/Sprint2-Walkthorugh.gif" width=350>

### Sprint 3

**Required Must-have Stories**

 - [X] From the detail page a user can navigate to the compose page to be able to create a review on the game they navigated from. They can input the following:
   - [X] Game Title
   - [X] Star Rating
   - [X] Comment
   - [X] Upload a game image
 - [X] On the review page the user will be able to see posted reviews from all users filtered as either posted from kids or posted from parents with the following contents:
   - [X] Game Title
   - [X] Review's user's username and profile picture
   - [X] Star Rating
   - [X] Comment
   - [X] Uploaded Game Image, if any
- [X] From the search page a user can create a search to get relevant results for:
   - [X] Reviews
   - [X] Games

<img src="https://github.com/CS388-Group-Project-App/Gamerful/blob/master/Sprint3-Walkthorugh.gif" width=350>

### Sprint 4

**Optional Nice-to-have Stories**

 - [X] Add loading animations
   - [X] Add loading animation for the login button
   - [X] Add loading animation for the signup button
   - [X] Add loading animation for submit review button
 - [X] Add video clip for games
   - [X] Update game model to get clip URL
   - [X] Add new fragment with video view to display game clip
 - [X] Add Infinite Scroll/Pagination to the reviews page

<img src="https://github.com/CS388-Group-Project-App/Gamerful/blob/master/Sprint4-Walkthorugh.gif" width=350>
   
