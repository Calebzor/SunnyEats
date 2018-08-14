# SunnyEats

## Description
Android app for weather info and nearby restaurants

## Download
### Hockeyapp
[![Hockeyapp](https://chart.googleapis.com/chart?cht=qr&chl=https%3A%2F%2Frink.hockeyapp.net%2Fapps%2F778d3ad77d3942c28ef32fb0ec9381c6&chs=256x256)](https://rink.hockeyapp.net/apps/778d3ad77d3942c28ef32fb0ec9381c6)

## CI
### CircleCI
[![CircleCI](https://circleci.com/gh/Calebzor/SunnyEats.svg?style=shield&circle-token=40a4339bd2eef365634c7457ad95ce19c21bcb37)](https://circleci.com/gh/Calebzor/SunnyEats)

## Quality
### Sonar
[![Quality Gate](https://sonarcloud.io/api/badges/gate?key=SunnyEats)](https://sonarcloud.io/dashboard/index/SunnyEats)

### Architecture, Code Design and Tech Stack Overview

The project follows [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) 
by Robert C. Martin (inspired by, among others, Hexagonal and Onion architectures). Instead of 
presenters, view models (and live data objects) are being used, as that plays more nicely with 
Android live cycles and configuration changes. Entity classes constitute rich domain model, 
besides of just storing data they as well provide behavior (having methods like `fetch`, 
`send` etc.), hence initial state of them should be always modeled as "unknown" so that initial 
actions, like `fetch`, can be called on them and differentiation between unknown (e.g. 
not-fetched yet) state and an empty (e.g. no data) state can be made.

The codebase foundations have been built following [SOLID](https://en.wikipedia.org/wiki/SOLID_
(object-oriented_design)) Object-Oriented design principles. The complexity is being split into 
single-responsibility units of code (both at the class and method level). Dependency inversion 
principle, assisted by Dagger 2 (elaborated further on below), is being followed so that 
dependencies on implementation details from the outer layers are not escalating throughout the 
codebase. Implementation details include also Android specific dependencies as they are more 
tricky to mock in JVM unit tests and as well it promotes lower coupling and better code design in general.

The code is packaged by feature. At root level of each feature package the core business logic is
 being placed. Further implementation or technology specific details are being placed in their 
 subpackages, including classes specific to UI and the backend API. For truly app generic 
 concepts, which are used at the application level or meant to be reused across many different 
 features, the `app` package has been created.


State is stored only in [Reductor](https://github.com/Yarikx/reductor) store, being a single 
source of truth, with controlled access (modifications dispatched as pure functions, read-only 
access of substates exposed to relevant parts of client code – promoting encapsulation).

The code design follows dependency injection principle, employing [Dagger 2](https://google.github.io/dagger/) in [the recommended way of using it on Android](https://google.github
.io/dagger/android.html). Primarily one application scope is being used and that should be 
optimal for these two modules (with their expected memory footprints) and constitutes a natural 
approach considering application's global Reductor store being used to store the application 
state. Subcomponents are currently just being used for extending dependency graph. Dagger 
singletons are being used only when it's strongly justified, promoting stability through 
micro-restarts (getting clean new instance – less stateful, hence simpler app) and better memory 
footprint (not holding on to no longer needed objects). Small and simple modules are created per 
set of related concrete implementation dependencies – which promotes greater flexibility with 
swapping concrete dependencies in the future or providing fake implementations in particular 
automated UI (functional) test scenarios. 


RxJava 2 is used for asynchronous composable processing chains passed across the inner layers of 
the architecture stack. Where at each layer specific processing, being part of responsibility of 
a given layer, is added.

To better model nullability and get support from compiler when dealing with the absent ("empty") 
state, `Optional` is being used. The implementation used is the one present in the 
Lightweight-Stream-API library (described more broadly in the [Java 8 section](#java8)). 


The app follow single activity architecture in order to make migration to the [navigation 
architecture component](https://developer.android.com/topic/libraries/architecture/navigation/) easier. 

### State Container

Related state modifications are dispatched from use case specific interactors (Clean 
Architecture), as opposed to state global middlewares, which promotes encapsulation. Value 
(immutable) objects (powered by AutoValue) are being used across the app. [PCollections](https://pcollections.org/) (persistent and immutable collections) are being used everywhere 
inside the state container (in the reducers). Even when the data is coming from the outside, it's
 being placed inside of a persistent and immutable collection. PCollections are being used 
 everywhere through standard Java collections interfaces to not escalate dependency on them. 


### Java 8<a name="java8"/>

The code makes use of Java 8 new language features, especially lambdas, with the support of the 
desugar tool built in the Android Studio toolchain. Some Java 8 language APIs are being used 
through backport libraries. Those include [ThreeTen](https://github.com/JakeWharton/ThreeTenABP/)
 (date-time) and [Lightweight-Stream-API](https://github.com/aNNiMON/Lightweight-Stream-API). The
  latter besides pure backport features adds some additional operators and support for custom 
  operators. It's advised to best avoid using them for smooth transition to the original Java 8 
  API in the future (when min API level can be upgraded to 24).


### Test Automation

Tests follow [Testing Pyramid](https://testing.googleblog.com/2015/04/just-say-no-to-more-end-to-end-tests.html),

where:

1) most of them are isolated unit tests (testing one thing in a single unit of code with its 
dependencies mocked),
2) some integration tests (integration between just two real instances), (planned)
3) and a few UI functional E2E Espresso tests (which usually fake relevant backend API responses 
to simulate test scenario). (planned)


The unit and integration tests are super fast running locally on a dev machine in a JVM (can be 
used efficiently for TDD). Espresso UI tests are slightly slower as they require pushing APKs to emulator and instrumenting the test run.


## Additional information
https://github.com/Calebzor/SunnyEats/issues/14

Gif of app in action:
![](https://github.com/Calebzor/SunnyEats/blob/master/etc/app.gif)

## Credits

Icon made by https://www.flaticon.com/authors/smashicons from www.flaticon.com
