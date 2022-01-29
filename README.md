# GoogleBooksBrowser

### Capstone project by Bikramjeet Singh

## Overview
This is an Android application meant to enable users to browse the Google Books online database. It provides capabilities for searching, organizing and keeping record of the users book preferences.

## Key Features
* Search books by title or ISBN
* Organize books as
    * Read
    * Reading
    * Want to Read
    * Not interested
* Store data persistently on mobile device
* Offline mode, wherein users can view their list of saved books but not search online

## Software Components
* 4 Activities
    * MainScreen
    * SearchResultsScreen
    * MyBooksScreen
    * BookDetailsScreen
* Services used when making HTTP requests
* Content Providers used to store and retrieve the users data from an SQLite DB on the device
* Broadcast Receiver to toggle between online and offline modes upon connectivity change
* Intents used to bind the above components together

## Web Service
The web service used is Google’s Books API. An example of a search performed using this web service is `https://www.googleapis.com/books/v1/volumes?q=the+witcher`.
