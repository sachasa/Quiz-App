# Quiz-App
The JavaQuiz Microservices App is a dynamic and scalable quiz platform designed to provide users with an interactive and engaging quiz-taking experience. Leveraging the power of Java programming language and a microservices architecture, this app offers a robust and flexible solution for quiz enthusiasts.
## Setup

Building this project requires that Ivy be available to Ant. All you
need is ivy.jar in Ant's classpath (in your `$CLASSPATH`,
`$ANT_HOME/lib`, or `~/.ant/lib`).

## Dependencies

You will need to have Astyle installed and in your path for the
"format" target to work. If it's missing, that's fine. It won't affect
any other targets.

There is a "hotswap" target for replacing live code while an
application is running. You'll need the hotswap Ant extension
installed to use it. This target is to be used alongside the
"run-hotswap" target, which enables hotswapping in the JVM. You can
demo this for yourself by running "run-hotswap" in a terminal, editing
the printed string in the code, and running "hotswap" in another
terminal. The printed message in the running program should change to
the new string.

## Bundles

Take note of the sample `pom.xml` file. This is not actually for Maven
builds -- this is an Ant project afterall -- but for publishing builds
for a Maven repository. It's packed up by the "bundle" target, which
creates a `bundle.jar` containing your project's signed artifacts. To
use the "bundle" target you need to have GnuPG set up in your path, a
generated key pair, and a running `gpg-agent`, unless you like typing
your passphrase a bunch of times in a row.

## Philosophy

I hate coding absolute paths in my build script and I hate including
built files as part of the base project. My philosophy is that the
*environment* should be set up so that the tool can easily find the
external resources they need (JUnit, etc.) from the system or
dependency manager. It's the system or dependency manager that
provides the libraries. Anyone who has the proper development
environment set up -- one that works across many projects -- should be
able to clone the repository and do a build simply by running the
build program with no special arguments. There should be no need to
edit or install anything into the project space for the initial build.
