# ZiNotes

## Short Description
ZiNotes is a mobile app designed for Chinese language learners who want to keep track of the characters they study.
Users can store each character along with its pronunciation, tone, definitions, HSK level, stroke count, and even draw the character right on the screen.
It’s a digital notebook - but for your Hanzi journey!

## Domain Details
### Pronounciation
The pinyin pronounciation.
### Tone
The number corresponding to the tone used in the pronounciation.
### Radical
The number corresponding to the radical.
### Stroke Count
The number of strokes used in writing.
### HSK Level
The HSK level (1-6+), if applicable.
### Definitions
List of definitions in whichever language or languages you preffer.
### Drawing (Simplified/Traditional)
Drawing made by hand (or uploaded), corresponding to the simplified and/or traditional variant of the character.

## CRUD
### Create
Found a new Hanzi out in the wild and want to add it to your list? Simply tap the "+" button, fill in the details you want to add and there you have it! It's that easy!
### Read
Watch as your list grows with each Hanzi you add! Tap on any character in the list to see its detailed view containing all the info.
### Update
Discovered a new definition? Tap the UPDATE button from the detailed view of a character and edit any field you see fit.
### Delete
Added a duplicate by mistake? Or got a personal vendetta against a particular Hanzi? Whichever the reason, you can easily get rid of it by pressing the DELETE button from its detailed view. Don't worry, you'll be asked for confirmation to avoid any unfortunate events!

## Persistance Details
Any changes made on the list (adding or deleting a character) or on individual characters (updating one or more fields) are persisted locally as well as on the server.

## Offline Mode
When offline, the app functions on a local version of the storage, so all changes are applied on the local database and recorded in a queue. When back online, the the queue gets sent to the server to apply changes, then the local version syncs up to the server. 
If conflicts arise while syncing, take the "Server First" approach, and disregard any conflicting local changes.
### Create, Update, Delete
All changes are immediately applied to the local storage and visible to the user, so the app is interactive. Every action is added one by one in a queue, so when back online, the changes are applied to the server database in the same order they were performed.
### Read
The user sees the most recent version of the list kept in the local storage. When back online, the local storage is synced to the server database.


