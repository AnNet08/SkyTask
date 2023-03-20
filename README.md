# Sky Cats News

## Review the designs
### List Screen
Firstly, I did not quite understand what was the idea of the first news. Maybe it was planned something like a carousel with different news, but in this case, it should be another JSON-response with corousele_news_list for instance. Or every news item which reaches the top of the screen during swiping should become large. I decide to leave the design as it is and only the first news in the list will be shown on a large scale because I think this aspect should be discussed with a designer for example.
Although I think it is just a typo, I changed the header on this page (it was crossed out on the wireframe)

### Story Screen
I decided to add a header with the button Back just to make navigation easier for the user.

## Sructure
The project was written on Jetpack Compose and I tried to develop it on Clean Architecture principles and used MVI pattern and Hilt for DI.
Pattern MVI: presentation layer is divided into store (like view model) and screen. Data is transferred between these layers by using State.

## Data
I mocked feed by adding assets files with JSON response and it can be easily replaced with real API later in GetStoryJsonStorage and GetNewsListJsonStorage because both storages use getJsonDataFromAsset from JsonUtils.kt now.
Also, it is easy to add a new type of feed or some additional  API,  you just have to create new Storage and call it in the same Repository.

Currently, I filtered type advert in the UseCase , however, the other option can be just not to show it on the NewsListScreen. I used library Voyager for navigation and when the user taps on the story item it will navigate him to StoryScreen. In this case, the story should be chosen by id, but I did not have such data in json-response so I just show always the first story, but it can be easily changed  in GetStoryJsonStorage (when we will have API ).

## UI
I use Coil to work with images and I've added to sample_list.json one image url just for demonstration. Actually, we can just use AsyncImage and not create a component such as NewsImage, but I think this component can be useful when we will have real data. 
On the NewsList Screen, I use a shimmer placeholder to make the process of data loading more user-friendly and I create some compose components and visualiser to make the creation of UI easier.


