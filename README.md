# Android Navigation Architecture Component Demo

My article covering this repo: [Navigation Architecture Component for the Rest of Us](https://proandroiddev.com/navigation-architecture-component-for-the-rest-of-us-faafa890e5)

This project contains a comprehensive sample of using the [Navigation Architecture Component](https://developer.android.com/topic/libraries/architecture/navigation/) by way of a simple Notes app--no domain knowledge needed to understand the purpose of this app.

## Functionality included
1) List notes
2) View single note
3) Edit a note
4) Delete a note
5) Deep link to a note
6) Animate a transition between screens
7) Connecting `Nav Drawer` with your navigation graph.
8) Configuring type-safe arguments between navigation components (in this case, Fragments)

## Design Pattern
MVVM (Model-View-ViewModel) is used to make the architecture cleaner, more readable and maintainable. Notes are stored and managed in memory within the domain layer (this sample is about Navigation and not full-blown [Clean Architecture](https://github.com/jshvarts/ConductorMVP) after all).   

## Bonus
[This PR](https://github.com/jshvarts/NotesNavigation/commit/0744acff8d33708b72c852b71a8831a395ab22e2) contains steps needed to migrate from support library to AndroidX namespace. For more details, see [AndroidX Refactoring docs](https://developer.android.com/topic/libraries/support-library/refactor)

## Screenshots

![List of notes](docs/note-list.png?raw=true)

![Note detail](docs/note-detail.png?raw=true)

## License

    Copyright 2018 James Shvarts

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

