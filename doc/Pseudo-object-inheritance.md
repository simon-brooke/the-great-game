# Pseudo object inheritance

This is simply to document how I'm doing type inheritance for game objects, since Clojure does not provide type inheritance for records and I'm currently building game objects on Clojure records.

It's possible that I should instead build game objects on Java beans, which do have type (class) inheritance, and would work transparently; however, that isn't my current approach.