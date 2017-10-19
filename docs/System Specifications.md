# Movie Notifier Specifications
This document describes the idea, the functionalities and business rules that are part of Movie Notifier. Use this document to understand the system and get a better idea of the usage of the REST Api. Exact api call specifications are described in the `swagger.yaml` file.

## Abstract
Thea idea behind movie notifier is that cinema enthousiasts (users) can be notified when their favorite movie is available in their local cinema to get the best spots on premiere evening.

After a user registers they can configure in what way they want to be notified when a new movie showing is available. Current options are the notification types Facebook Messenger and Email.

Next, a user can set up a watcher. A watcher will keep it's eye on new showings for a specific movie. When a new showing is available the user will immediately be notified of the new showing through the notification type's they have configured earlyer. 

Since users are mostly after specific showings the user can configure filters. These filters make sure the user gets only usefull notifications about showings they actually want to go and see. 

## Domains
The whole system can be devided in two primary domains: users and notifications

### User
A user represents a cinema enthousiast that uses the system. A user looks like this:
```json
{
    "id": "5995f25708813b0001f99393",
    "name": "cinemaenthousia",
    "email": "enthousiast@example.com",
    "phonenumber": "+31698765432",
    "password": "abcd1234",
    "notifications": [
        "FBM"
    ],
    "apikey": "rSE0cKPgwwTnxnSVx9EzJGPUhS6qcdP6TChk5NWOZ8escVCbA194fMurxJJPE51z"
}
```
The fields `name`, `email`, `phonenumber` and `password` are provided by the user at registration. Later the user can update these fields. The user is uniquely identified by its `id` that is generated when the user is registered. The `apikey` is used to authenticate the user in all requets that need to be authenticated. 

There are some extra rules for userdata that apply to all users:
- [x] `name`, `email`, `phonenumber`, `password` and `notifications` are required and thus cannot be null.
- [x] `name` must be between 4 and 16 charcters and only contains letters (a-z) and numbers (0-9), but no capital letters (A-Z). The first 4 characters must always be letters.
- [x] `email` must contain a valid email address
- [x] `phonenumber` must be a valid Global Number as described in RFC 3966 section 5.1.4. So always in the format of `+[countrycode][phonenumber]`.
- [x] `notifications` can be an empty array but this results in receiving no notifications.
- [x] `password` must at least be 8 characters long and may only contain the letters (`a-z`), capital letters (`A-Z`), numbers (`0-9`) and the following special characters: `!@#$%^&*()_-+={}[]:;?><.,`
- [x] Items in the `notifications` array must be one of the following: `FBM` (facebook messenger), `MIL` (email) or `SMS`. Not all users are allowed to enable all types of notifications. The user will be informed of these restrictions when they try to enable one of the restricted notifications. Currently there is no way to get these restrictions upfront.

Notifications through facebook messenger use the user phone number. If the user has not connected it's phone number to a facebook messenger account the notification will not arrive. 

### Watchers
Users can register watchers. A watcher looks like this:
```json
{
    "id": "599416e108813b00012de818",
    "userid": "5995f25708813b0001f99393",
    "name": "IT",
    "movieid": 123456,
    "begin": 1504800000000,
    "end": 1507392000000,
    "filters": {
        "cinemaid": "PATHE12",
        "startafter": 1504800000000,
        "startbefore": 1507392000000,
        "ov": "yes",
        "nl": "no",
        "imax": "no-preference",
        "hfr": "no",
        "laser": "yes",
        "dbox": "no",
        "4dx": "no",
        "dolbycinema": "no",
        "dolbyatmos": "no-preference",
        "3d": "no-preference",
        "4k": "no-preference"
    }
}
```
The watcher is uniquely identified by its `id`. The `userid` is the id of the user that owns of this watcher. The `name` is the textual description of the watcher that is specified by the user.

The movie that is watched by this watcher is specified as a numerical id in the field `movieid`. At this moment the movieid corresponds with the movie id of given to movies by [pathe.nl](https://pathe.nl). Later this will be changed to use the movie index of IMDB.

`begin` and `end` specify in what period this watcher is enabled. These fields are necessary to stop watchers from watching forever. There are some rules that apply to these fields that are specified below.

All fields witin the `filters` property are used to filter out new movie showings from getting notifications to the user. `cinemaid` specifies the cinema the showing is shown at. For all filters a preference must be specified.

This `cinemaid` filter consists of two parts: the cinema chain id and the cinema number. At this moment only Pathe Cinema is supported (id `PATHE`), but later Vue, Kinepolis and others might be added. Cinemec is part of Pathe.:) The cinema chain id and the cinema number are concatinated together as the string representation as `cinemaid`. At this moment there is no check weather or not the cinema is valid! This filter is required as you can only watch showings for a specific movie in a specific cinema.

The following other parameters are supported:
* `startafter` Showings that start later than this timestamp 
* `startbefore` Showings that start before this timestamp
* `ov` Showing with original spoken audio
* `nl` Showing with dutch spoken audio
* `imax` Showing on a IMAX display. Weather or not the movie has imax aspect ratio is not supported by this filter.
* `hfr` Showing wiht High Frame Rate (48 fps)
* `laser` Showing with Laser projector
* `dbox` Showing with D-Box Experience
* `4dx` Showing with 4DX Experience
* `dolbycinema` Showing with Dolby Cinema audio and display 
* `dolbyatmos` Showing with Dolby Atmos audio
* `3d` Showign in 3D
* `4k` Showing in 4K resolution

All boolean true/false filters can have three options:
* `yes` filter out all showings without this feature
* `no` filter out all showings with this feature
* `no-preference` do not filter showings on this feature.

To support watcher sharing it is possible to retreve watchers details by id without being the owner. 

Some extra rules about the watcher data that apply to all watchers from all users:
- [x] Watchers can only be created by authenticated users.
- [x] Every field within a watcher is required. 
- [x] The `name` of the watcher must be between 3 and 50 characters.
- [x] Users can only create watchers with their own `userid`.
- [x] All fields can be updated by the owner (user with the same `userid`) except the watcher `id`. But since the `userid` can only be the one of the user that is executing the request this field cannot be changed.
- [x] All users can retreve a watcher with any valid apikey and the id. 
- [x] The field `userid` will be ommitted when a user retreves a watcher by its id with an apikey that does not match the userid of the watcher because of privacy.
- [x] The `end` timestamp must be later than the `begin` timestamp
- [x] The `filters.startafter` timestamp must be before than the `filters.startbefore` timestamp
- [ ] A user can have no more than 10 watchers that have overlapping `begin` to `end` periods to make sure there are never too many watchers watching at the same time.  
- [x] The time between `begin` and `end` must be shorter than one month to make sure watchers do not run too long.
- [x] The time between `filters.startbefore` and `filters.startafter` must be 2 weeks or shorter to prevent an endless stream of notifications.


## Technical api details
Some technical details about the usage of the api:
* All timestamps used in this system use Unix Time: milliseconds since January 1, 1970.
* When updating a model fields that do not need to be updated can be omitted.
* Fields that are optional can be ommitted to not specifie them. With a `string` or `object` they can also be set to `null` to not specify them. A number or timestamp field can be set to value `0` to not specify them.