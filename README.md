# ulna
======

Publicize what you are listening to.

# configuration

For heroku deployment with custom configuration, don't use the master branch for 
development. Create an env.conf based on env.example.conf. In the master branch,
set .gitignore to allow adding config/env.conf. Don't push master to github, just
heroku. When pushing to heroku, merge the changes from dev into master and then
push master.

## License
==========

Copyright (C) 2011 Robert P. Levy

Distributed under the Eclipse Public License, the same as Clojure.
