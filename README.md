# ulna
======

Publicize what you are listening to.

# configuration

Create a file bin/env.sh (in .gitignore so as not to push) based on
the example in bin/ to set environment vars for app secret, etc.

In develoment, run ". ./bin/env.sh" before lein run or lein swank.
Before pushing to heroku, run "./bin/env-heroku.sh".

## License
==========

Copyright (C) 2011 Robert P. Levy

Distributed under the Eclipse Public License, the same as Clojure.
