# MuzzApp: Graduate Android Developer

Graduate Android Developer Task: to develop a similar chat interface to that of Muzz app. 

//Space for video
![]()


#### Highlights:
- MVVM | Repository pattern | Room | Coroutines | Databinding | Testing (unit, integration and UI)

Please Note:-  

#### What's implemented?
- Message List displays the newest items at the bottom and the oldest at the top.
- Messages are distinguished by sender/receiver and their position in the chat window.
- Message has a tail if 1) most recent 2) message after it is sent by the other user 3) message after it was sent after 20 seconds.
- Item sectioning


#### Limitations
- App does not implement the up button
- App does not have a profile picture on the toolbar yet
- Testing: very limited testing:
    - I have not covered unit testing (see Issue: 1)
    - 1 x integration tests (see Issue: 2)
    - 2 - Espresso UI tests
- does not implement DI Framework like Dagger/KOIN; instead uses ServiceLocator


#### Assumptions
- assuming I will be judged in accordance with the Graduate rubric, regardless I wanted to give something better than asked for i.e. persistent storage
and 2-way databinding for live observation. I wanted to showcase some of the knowledge/skills I acquired during my time at Udacity. I certainly feel - I am a Junior (not Mid), as you would be able to tell from eye-balling my code. 


#### Given More Time: 
- would have implemented the profile picture on the Toolbar
- would have implemented a switch button instead of a menu item to switch the users
- when implementing tests, it kind of prompted me to change some of the implementation details, particularly
   how a message is sent. Currently, it uses 2-way databinding on the EditText field. From a testing perspective
   it is not ideal(ish). Although, (see issue section) this would not be an issue if I didn't face the issue mentioned. 
   But certainly requires some time for investigation and further improvements to the architecture. 
- would implement ChatListAdapter.kt instead. I noticed the list update on UI was not very smooth, that's why I didn't use it as the main adapter.
- would like to learn and implement Dagger/KOIN DI framework instead of ServiceLocator. 

#### Lessons Learnt:
- More organised commit and work: I had been working on one thing, but then also started working on another feature on the same branch. Ideally, I should've finished a piece of feature in one branch, ensure its working, commit, push,delete branch, and then move onto new branch to work on a new feature. Also, there were times, I worked directly on master branch which is 'no bueno'. 

#### Issues: 
1. Unit Testing: while implementing [ChatViewModelTest]() I am facing an issue with possibly Live-Data updates. 
At this point in time, I haven't had the chance to investigate
2. Toolbar Title: changes when the user switches the user. This currently has conflict with [ChatFragmentTest]() (error: ClassCastException) due to FragmentActivity being casted as MainActivity, and hence why the function setScreenTitle() of [ChatFragment.kt]() is commented out. This will work, but it will break the fragment test. Again to lack of time, I have not investigated it. 






