# MuzzApp: Graduate Android Developer

Graduate Android Developer Task: to develop a similar chat interface to that of Muzz app.

![]()

#### Highlights:

- MVVM | Repository pattern | Room | Coroutines | Databinding | Testing


#### What's implemented?

- Message List displays the newest items at the bottom and the oldest at the top.
- Messages are distinguished by sender/receiver and their position in the chat window.
- Message has a tail if 1) most recent 2) message after it is sent by the other user 3) message
  after it was sent after 20 seconds.
- Item sectioning
- Testing:
  - Unit: [ChatViewModelTest](https://github.com/azzumw/MuzzApp/blob/master/app/src/test/java/com/example/muzzapp/ui/chat/ChatViewModelTest.kt)
  - Integration: [ChatFragmentTest](https://github.com/azzumw/MuzzApp/blob/master/app/src/androidTest/java/com/example/muzzapp/ui/chat/ChatFragmentTest.kt) - 1 test implemented, however, it faces issue with the setting up of title of the action bar from the fragment which is casting the activity prop value as MainActivity. 
  - UI: [MainActivityTests](https://github.com/azzumw/MuzzApp/blob/master/app/src/androidTest/java/com/example/muzzapp/MainActivityTests.kt) - three UI tests.

<p align="center">
  <img src="https://github.com/azzumw/MuzzApp/blob/master/app/muzz_sc.png" />
</p>


#### Limitations

- App does not implement the up button
- App does not have a profile picture on the toolbar yet
- EditText field does not auto-wrap message text
- does not implement DI Framework like Dagger/KOIN; instead uses ServiceLocator

#### Assumptions

- assuming I will be judged in accordance with the Graduate rubric, regardless I wanted to give
  something better than asked for i.e. persistent storage
  and 2-way databinding for live observation. I wanted to showcase some of the knowledge/skills I
  acquired during my time at Udacity. I certainly feel - I am a Junior (not Mid), as you would be
  able to tell from eye-balling my code.


#### Given More Time:

- would spend more time on the UI
- would have implemented the profile picture on the Toolbar
- would have implemented a switch button instead of a menu item to switch the users
- would try to copy the chat interface to the T!
- when implementing tests, it kind of prompted me to change some of the implementation details,
  particularly how a message is sent. Currently, it uses 2-way databinding on the EditText field. From a testing
  perspective, in my opinion, it is not ideal(ish)[I could be wrong]. Although, (see issue section) this would not be an issue if I didn't face
  the issue mentioned. But certainly requires some time for investigation and further improvements to the architecture.
- I did try to implement [ListAdapter](https://developer.android.com/reference/androidx/recyclerview/widget/ListAdapter) instead. However,I noticed the list update on UI was not very smooth,that's why I have removed it from my final version of the app. 
- would have liked to implement Dagger/KOIN DI framework instead of ServiceLocator.
- written more tests

#### Lessons Learnt:

- More organised commit and work: I had been working on one thing, but then also started working on
  another feature on the same branch. Ideally, I should've finished a piece of feature in one
  branch, ensure its working, commit, push,delete branch, and then move onto new branch to work on a
  new feature. Also, there were times, I worked directly on master branch which is 'no bueno'.

#### Issues:

1. Toolbar Title: changes when the user switches the user. This currently has conflict
   with [ChatFragmentTest](https://github.com/azzumw/MuzzApp/blob/master/app/src/androidTest/java/com/example/muzzapp/ui/chat/ChatFragmentTest.kt) (error: ClassCastException) due to FragmentActivity being casted as MainActivity, and hence why the function setScreenTitle() of [ChatFragment.kt](https://github.com/azzumw/MuzzApp/blob/master/app/src/main/java/com/example/muzzapp/ui/chat/ChatFragment.kt) is commented out. This will work, but it will break the fragment test. Again due to the lack of time, I have not investigated it. 






