# Movie Notifier Specifications
This document describes the idea, the functionalities and business rules that are part of Movie Notifier. Use this document to understand the system and get a better idea of the usage of the REST Api. Exact api call specifications are described in the `swagger.yaml` file.

## Abstract
Thea idea behind movie notifier is that cinema enthusiasts (users) can be notified when their favorite movie is available in their local cinema to get the best spots on premiere evening.

After a user registers they can configure in what way they want to be notified when a new movie showing is available. Current options are the notification types Facebook Messenger and Email.

Next, a user can set up a watcher. A watcher will keep it's eye on new showings for a specific movie. When a new showing is available the user will immediately be notified of the new showing through the notification type's they have configured earlyer. 

Since users are mostly after specific showings the user can configure filters. These filters make sure the user gets only useful notifications about showings they actually want to go and see. 

## Domains
The whole system can be divided in two primary domains: users and notifications

### User
A user represents a cinema enthusiast that uses the system. A user looks like this:
```json
{
    "id": "5995f25708813b0001f99393",
    "name": "cinemaenthousia",
    "email": "enthousiast@example.com",
    "password": "abcd1234",
    "apikey": "rSE0cKPgwwTnxnSVx9EzJGPUhS6qcdP6TChk5NWOZ8escVCbA194fMurxJJPE51z",
    "fcm-registration-tokens": []
}
```
The fields `name`, `email`, and `password` are provided by the user at registration. Later the user can update these fields. The user is uniquely identified by its `id` that is generated when the user is registered. The `apikey` is used to authenticate the user in all requets that need to be authenticated. 

There are some extra rules for userdata that apply to all users:
- [x] `name`, `email`, `phonenumber`, `password` and `notifications` are required and thus cannot be null.
- [x] `name` must be between 4 and 16 characters and only contains letters (a-z) and numbers (0-9), but no capital letters (A-Z). The first 4 characters must always be letters.
- [x] `email` must contain a valid email address. This field is optional. When the field is set to an empty string the user will receive no notifications over email.
- [x] `password` must at least be 8 characters long and may only contain the letters (`a-z`), capital letters (`A-Z`), numbers (`0-9`) and the following special characters: `!@#$%^&*()_-+={}[]:;?><.,`
- [x] `fcm-registration-tokens` is a list of firebase-cloud-messaging device registration tokens. Notifications are sent to all tokens in this list.

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
        "cinemaid": 12,
        "startafter": 1504800000000,
        "startbefore": 1507392000000,
        "ov": "yes",
        "nl": "no",
        "imax": "no-preference",
        "hfr": "no",
        "laser": "yes",
        "4dx": "no",
        "screenx": "no",
        "dolbycinema": "no",
        "dolbyatmos": "no-preference",
        "3d": "no-preference",
        "4k": "no-preference"
    }
}
```
The watcher is uniquely identified by its `id`. The `userid` is the id of the user that owns of this watcher. The `name` is the textual description of the watcher that is specified by the user.

The movie that is watched by this watcher is specified as a numerical id in the field `movieid`. The movieid corresponds with the movie id of given to movies by [pathe.nl](https://pathe.nl).

`begin` and `end` specify in what period this watcher is enabled. These fields are necessary to stop watchers from watching forever. There are some rules that apply to these fields that are specified below.

All fields within the `filters` property are used to filter out new movie showings from getting notifications to the user. `cinemaid` specifies the cinema the showing is shown at. For all filters a preference must be specified.

This `cinemaid` filter selects a cinema. This id references a cinema from the `GET /cinemas/` list. At this moment there is no check weather or not the cinema is valid! This filter is required as you can only watch showings for a specific movie in a specific cinema. In previous versions this string was a concatenation of the cinema chain id and the cinema number. For example `PATHE12`. 

The following other parameters are supported:
* `startafter` Showings that start later than this timestamp 
* `startbefore` Showings that start before this timestamp
* `ov` Showing with original spoken audio
* `nl` Showing with dutch spoken audio
* `imax` Showing on a IMAX display. Weather or not the movie has imax aspect ratio is not supported by this filter.
* `hfr` Showing with High Frame Rate
* `laser` Showing with Laser projector
* `4dx` Showing with 4DX Experience
* `screenx` Showing with ScreenX Experience
* `dolbycinema` Showing with Dolby Cinema audio and display 
* `dolbyatmos` Showing with Dolby Atmos audio
* `3d` Showing in 3D
* `4k` Showing in 4K resolution

All boolean true/false filters can have three options:
* `yes` filter out all showings without this feature
* `no` filter out all showings with this feature
* `no-preference` do not filter showings on this feature.

To support watcher sharing it is possible to retrieve watchers details by id without being the owner. 

Some extra rules about the watcher data that apply to all watchers from all users:
- [x] Watchers can only be created by authenticated users.
- [x] Every field within a watcher is required. 
- [x] The `name` of the watcher must be between 3 and 50 characters.
- [x] Users can only create watchers with their own `userid`.
- [x] All fields can be updated by the owner (user with the same `userid`) except the watcher `id`. But since the `userid` can only be the one of the user that is executing the request this field cannot be changed.
- [x] All users can retrieve a watcher with any valid apikey and the id. 
- [x] The field `userid` will be omitted when a user retrieves a watcher by its id with an apikey that does not match the userid of the watcher because of privacy.
- [x] The `end` timestamp must be later than the `begin` timestamp
- [x] The `filters.startafter` timestamp must be before than the `filters.startbefore` timestamp
- [ ] A user can have no more than 10 watchers that have overlapping `begin` to `end` periods to make sure there are never too many watchers watching at the same time.  
- [x] The time between `begin` and `end` must be shorter than one month to make sure watchers do not run too long.
- [x] The time between `filters.startbefore` and `filters.startafter` must be 2 weeks or shorter to prevent an endless stream of notifications.


## Technical api details
Some technical details about the usage of the api:
* All timestamps used in this system use Unix Time: milliseconds since January 1, 1970.
* When updating a model fields that do not need to be updated can be omitted.
* Fields that are optional can be omitted to not specify them. With a `string` or `object` they can also be set to `null` to not specify them. A number or timestamp field can be set to value `0` to not specify them.