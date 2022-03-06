# Pinguin  Coding Challenge

## Content
- Story planning algorithm
- API Documentation

## Story planning algorithm
The story planning algorithm works like the following
- Get the list of stories which are unassigned
- Sort the list of stories in ascending order by story points
- Divide stories by the number of developers to assign maximum story per week for one developer
- For each developer, use the sliding window algorithm to pick the optimized list of stories based on story points.

## API Documentation

## Stories API

### Get Stories 
This API is created to get list of stories

#### API Signature
GET - {http/s}://{server}:{port}/api/stories?title={title}

#### API Sample
GET - https://localhost:8080/api/stories?title=""

#### Response data
[
{
"issueId": "3d4e858f-a756-4c75-8f10-b28593b61f17",
"title": "neq",
"description": "asd",
"points": 2,
"developer": {
"developerId": "8a5697e8-ea63-4d1c-b2e0-cfe31ab201ef",
"name": "sid"
},
"storyStatus": "NEW",
"creationDate": "2022-03-04T04:36:17.823+00:00"
}... ]

### Get Story
This API is created to get single story

#### API Signature
GET - {http/s}://{server}:{port}/api/stories/{storyId}

#### API Sample
GET - http://localhost:8080/pinguin/api/stories/3d4e858f-a756-4c75-8f10-b28593b61f17

#### Response data
{
"issueId": "3d4e858f-a756-4c75-8f10-b28593b61f17",
"title": "neq",
"description": "asd",
"points": 2,
"developer": {
"developerId": "8a5697e8-ea63-4d1c-b2e0-cfe31ab201ef",
"name": "sid"
},
"storyStatus": "NEW",
"creationDate": "2022-03-04T04:36:17.823+00:00"
}

### Create Story
This API is created to create single story

#### API Signature
POST - {http/s}://{server}:{port}/api/stories/

#### API Sample
POST - http://localhost:8080/pinguin/api/stories/
Request Body:
{
"title": "hello",
"description": "hello world",
"storyStatus": "NEW",
"points":2
}

#### Response data
{
"issueId": "e6f8d190-b2a7-4135-a426-1de094b535b5",
"title": "hello",
"description": "hello world",
"points": 2,
"developer": null,
"storyStatus": "NEW",
"creationDate": "2022-03-06T13:14:27.465+00:00"
}

### Update Story
This API is created to update a single story

#### API Signature
PATCH - {http/s}://{server}:{port}/api/stories/

#### API Sample
PATCH - http://localhost:8080/pinguin/api/stories/
Request Body:
{
"issueId": "e6f8d190-b2a7-4135-a426-1de094b535b5"
"title": "hello updated",
}

#### Response data
{
"issueId": "e6f8d190-b2a7-4135-a426-1de094b535b5",
"title": "hello updated",
"description": "hello world",
"points": 2,
"developer": null,
"storyStatus": "NEW",
"creationDate": "2022-03-06T13:14:27.465+00:00"
}

### Delete Story
This API is created to delete a single story

#### API Signature
DELETE - {http/s}://{server}:{port}/api/stories/{storyId}

#### API Sample
DELETE - http://localhost:8080/pinguin/api/stories/e6f8d190-b2a7-4135-a426-1de094b535b5

#### Response data
None

## Bugs API

### Get Bugs
This API is created to get list of bugs

#### API Signature
GET - {http/s}://{server}:{port}/api/bugs?title={title}

#### API Sample
GET - https://localhost:8080/api/bugs?title=""

#### Response data
[
{
"issueId": "280d6138-4f6d-4e57-90bd-447fd48922f6",
"title": "hello",
"description": "hello world",
"developer": {
"developerId": "8a5697e8-ea63-4d1c-b2e0-cfe31ab201ef",
"name": "sid"
},
"bugStatus": "NEW",
"priority": "CRITICAL",
"creationDate": "2022-03-04T09:30:04.641+00:00"
},... ]

### Get Bug
This API is created to get single story

#### API Signature
GET - {http/s}://{server}:{port}/api/bugs/{bugId}

#### API Sample
GET - http://localhost:8080/pinguin/api/bugs/3d4e858f-a756-4c75-8f10-b28593b61f17

#### Response data
{
"issueId": "280d6138-4f6d-4e57-90bd-447fd48922f6",
"title": "hello",
"description": "hello world",
"developer": {
"developerId": "8a5697e8-ea63-4d1c-b2e0-cfe31ab201ef",
"name": "sid"
},
"bugStatus": "NEW",
"priority": "CRITICAL",
"creationDate": "2022-03-04T09:30:04.641+00:00"
}

### Create Bug
This API is created to create single bug

#### API Signature
POST - {http/s}://{server}:{port}/api/bugs/

#### API Sample
POST - http://localhost:8080/pinguin/api/bugs/
Request Body:
{
"title": "hello",
"description": "world",
"bugStatus": "NEW",
"priority": "CRITICAL"
}

#### Response data
{
"issueId": "2391fa63-cd50-4668-916e-d5f6cc8e0048",
"title": "hello",
"description": "world",
"developer": null,
"bugStatus": "NEW",
"priority": "CRITICAL",
"creationDate": "2022-03-06T13:20:40.748+00:00"
}

### Update Bug
This API is created to update a single bug

#### API Signature
PATCH - {http/s}://{server}:{port}/api/bugs/

#### API Sample
PATCH - http://localhost:8080/pinguin/api/bugs/
Request Body:
{
"issueId": "2391fa63-cd50-4668-916e-d5f6cc8e0048"
"title": "hello updated",
}

#### Response data
{
"issueId": "2391fa63-cd50-4668-916e-d5f6cc8e0048",
"title": "hello updated",
"description": "world",
"developer": null,
"bugStatus": "NEW",
"priority": "CRITICAL",
"creationDate": "2022-03-06T13:20:40.748+00:00"
}


### Delete Bug
This API is created to delete a single bug

#### API Signature
DELETE - {http/s}://{server}:{port}/api/bugs/{bugId}

#### API Sample
DELETE - http://localhost:8080/pinguin/api/bugs/e6f8d190-b2a7-4135-a426-1de094b535b5

#### Response data
None

## Developer API

### Get Developers
This API is created to get list of developers

#### API Signature
GET - {http/s}://{server}:{port}/api/developers?name={title}

#### API Sample
GET - https://localhost:8080/api/developers?name=""

#### Response data
[
{
"developerId": "8a5697e8-ea63-4d1c-b2e0-cfe31ab201ef",
"name": "sid"
},... ]

### Get Developer
This API is created to get single developer

#### API Signature
GET - {http/s}://{server}:{port}/api/developers/{developerId}

#### API Sample
GET - http://localhost:8080/pinguin/api/developers/8a5697e8-ea63-4d1c-b2e0-cfe31ab201ef

#### Response data
{
"developerId": "8a5697e8-ea63-4d1c-b2e0-cfe31ab201ef",
"name": "sid"
}

### Create Developer
This API is created to create single developer

#### API Signature
POST - {http/s}://{server}:{port}/api/developers/

#### API Sample
POST - http://localhost:8080/pinguin/api/developers/
Request Body:
{
"name": "sid"
}

#### Response data
{
"developerId": "8a5697e8-ea63-4d1c-b2e0-cfe31ab201ef",
"name": "sid"
}

### Update Developer
This API is created to update a single developer

#### API Signature
PATCH - {http/s}://{server}:{port}/api/developers/

#### API Sample
PATCH - http://localhost:8080/pinguin/api/developers/
Request Body:
{
"developerId": "7cbd797e-208a-4860-a8e5-9221a15e61f8",
"name": "siddharth"
}

#### Response data
{
"developerId": "7cbd797e-208a-4860-a8e5-9221a15e61f8",
"name": "siddharth"
}


### Delete Developer
This API is created to delete a single developer

#### API Signature
DELETE - {http/s}://{server}:{port}/api/developers/{developerId}

#### API Sample
DELETE - http://localhost:8080/pinguin/api/developers/7cbd797e-208a-4860-a8e5-9221a15e61f8

#### Response data
None

## Plan API

### Get Plan
This API is created to get developer plan list

#### API Signature
GET - {http/s}://{server}:{port}/api/stories/plan

#### API Sample
GET - http://localhost:8080/pinguin/api/stories/plan

#### Response data
[
{
"weekNumber": 1,
"developerWeekPlanList": [
{
"developerId": "8a5697e8-ea63-4d1c-b2e0-cfe31ab201ef",
"name": "sid",
"storyList": [
{
"issueId": "e6f8d190-b2a7-4135-a426-1de094b535b5",
"title": "hello",
"description": "hello world",
"points": 2,
"developer": null,
"storyStatus": "NEW",
"creationDate": "2022-03-06T13:14:27.465+00:00"
}
]
},
{
"developerId": "32215325-fad2-4d6a-840f-ab67ecf786a4",
"name": "sdsd",
"storyList": [
{
"issueId": "d3c9db7f-85c1-41c2-a8db-38dc65ef5fe9",
"title": "sidddhartth",
"description": "sadsadadsa",
"points": 4,
"developer": null,
"storyStatus": "ESTIMATED",
"creationDate": "2022-03-06T13:01:34.007+00:00"
}
]
}
]
}
]