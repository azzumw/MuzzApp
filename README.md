# MuzzApp: Graduate Android Developer

Graduate Android Developer Task: to develop a similar chat interface to that of Muzz app. HAPPY CODING and REVIEWING! =]

![](/app/muzz_vid.gif)

#### Highlights:

- MVVM | Repository pattern | Room | Coroutines | Databinding | Testing


#### What's implemented?

- Message List displays the newest items at the bottom and the oldest at the top.
- Messages are distinguished by sender/receiver and their position in the chat window.
- Message has a tail if 1) most recent 2) message after it is sent by the other user 3) message
  after it was sent after 20 seconds.
- Item sectioning
- Testing:
  - Unit: 
      - [ChatViewModelTest](https://github.com/azzumw/MuzzApp/blob/master/app/src/test/java/com/example/muzzapp/ui/chat/ChatViewModelTest.kt)
      - [ChatDatabaseTests](https://github.com/azzumw/MuzzApp/blob/master/app/src/androidTest/java/com/example/muzzapp/database/ChatDatabaseTests.kt)
  - Integration: 
      - [ChatFragmentTest](https://github.com/azzumw/MuzzApp/blob/master/app/src/androidTest/java/com/example/muzzapp/ui/chat/ChatFragmentTest.kt) - 1 test implemented however issue (see Issues section)
  - UI: 
      - [MainActivityTests](https://github.com/azzumw/MuzzApp/blob/master/app/src/androidTest/java/com/example/muzzapp/MainActivityTests.kt) - three UI tests.


#### Limitations

- App does not implement the up button
- App does not have a profile picture on the toolbar yet
- Does not take in to account orientation completely yet
- EditText field does not auto-wrap message text
- does not implement DI Framework like Dagger/KOIN; instead uses ServiceLocator

#### Assumptions

- assuming I will be judged in accordance with the Graduate rubric, regardless I wanted to give
  something better than asked for i.e. persistent storageand 2-way databinding for live observation. 
  I wanted to showcase some of the knowledge/skills I acquired during my time at Udacity. I certainly 
  feel - I am a Junior (not Mid), as you would be able to tell from eye-balling my code.
- Although, not required but I went ahead with implementing Repository pattern to help me test my viewmodel, repository and database in isolation. 
- assuming, I have managed to implement databinding, room persistence library, moderator will forgive me on my short comings on UI implementation, and some code smell around switching user. I feel I may be able simplify it further. 

#### Given More Time:

- would spend more time on the UI
- would have implemented the profile picture on the Toolbar
- would try to copy the chat interface to the T! (I did use Figma eye dropper to get the exact chat bubble colours)
- would implement [Save State module for ViewMode](https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-savedstate) like I have implemented in one of my own projects [here](https://github.com/azzumw/CapstoneProject/blob/master/app/src/main/java/com/example/android/politicalpreparedness/representative/RepresentativeViewModel.kt)
- when implementing tests, it kind of prompted me to change some of the implementation details,
  particularly how a message is sent. Currently, it uses 2-way databinding on the EditText field. From a testing
  perspective, in my opinion, it is not ideal(ish)[I could be wrong]. I would like to have more control over the arguments I am passing. Certainly requires some time for investigation and further improvements to the architecture.
- I did try to implement [ListAdapter](https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter) instead. However,I noticed the list update on UI was not very smooth,that's why I have removed it from my final version of the app. 
- would have liked to implement Dagger/KOIN DI framework instead of ServiceLocator.
- written more tests (e.g. UI tests using Custom Drawable matchers to verify message is sent/received by the correct user)

#### Lessons Learnt:

- More organised commit and work: I had been working on one thing, but then also started working on
  another feature on the same branch. Ideally, I should've finished a piece of feature in one
  branch, ensure its working, commit, push,delete branch, and then move onto new branch to work on a
  new feature. Also, there were times, I worked directly on master branch which is 'no bueno'.

#### Issues:

1. Switch button issue with ChatFragmentTest: This is currently in conflict with [ChatFragmentTest](https://github.com/azzumw/MuzzApp/blob/master/app/src/androidTest/java/com/example/muzzapp/ui/chat/ChatFragmentTest.kt)(error: NPE). When launching pure fragment (with no activity attached, switcher variable is set to null and hence NPE). Comment out ln 54-67 [ChatFragment.kt](https://github.com/azzumw/MuzzApp/blob/master/app/src/main/java/com/example/muzzapp/ui/chat/ChatFragment.kt) to pass the test.


## More Projects:
- [Capstone Project](https://github.com/azzumw/CapstoneProject)
- [Location Reminder App](https://github.com/azzumw/Project4)
- [Asteroid Radar](https://github.com/azzumw/AsteroidRadar)
- [Shoe Inventory](https://github.com/azzumw/Udacity22/tree/main/ShoeInventory)






