(defproject journeyman-cc/the-great-game "0.1.2-SNAPSHOT"
  :cloverage {:output "docs/cloverage"
              :codecov? true
              :emma-xml? true}
  :codox {:froboz.cloverage {:output "docs/cloverage"
                      :codecov? true
                      :html? true
                      :debug? true}
          :metadata {:doc "**TODO**: write docs"
                     :doc/format :markdown}
          :output-path "docs/codox"
          :source-uri "https://github.com/simon-brooke/the-great-game/blob/master/{filepath}#L{line}"}
  :cucumber-feature-paths ["test/features/"]
  :dependencies [;;[codox "0.10.7-cloverage"]
                 [com.taoensso/timbre "5.1.2"]
                 [environ "1.2.0"]
                 [jme-clj "0.1.13"]
;;                 [jme3-core "3.4.0-stable"]
                 [journeyman-cc/walkmap "0.1.0-SNAPSHOT"]
                 [me.raynes/fs "1.4.6"]
                 [mw-engine "0.1.6-SNAPSHOT"]
                 [org.apache.commons/commons-math3 "3.6.1"] ;; for mersenne-twister implementation
                 [org.clojure/algo.generic "0.1.3"]
                 [org.clojure/clojure "1.10.3"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [org.clojure/tools.namespace "1.0.0"]
                 [org.clojure/tools.reader "1.3.5"]
                 [org.clojurenlp/core "3.7.0"]]
  :description "Prototype code towards the great game I've been writing about for ten years, and know I will never finish."
  :license {:name "GNU General Public License,version 2.0 or (at your option) any later version"
            :url "https://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html"}
  :plugins [[lein-cloverage "1.2.2"]
            [lein-codox "0.10.7-cloverage"]
            [lein-cucumber "1.0.2"]
            [lein-gorilla "0.4.0"]]

  ;; NOTE WELL: `lein release` won't work until we have a release repository
  ;; set, which we don't!
  :release-tasks [["vcs" "assert-committed"]
                  ["change" "version" "leiningen.release/bump-version" "release"]
                  ["vcs" "commit"]
                  ["vcs" "tag" "v." "--no-sign"]
                  ["clean"]
                  ["codox"]
                  ["cloverage"]
                  ["uberjar"]
                  ["change" "version" "leiningen.release/bump-version"]
                  ["vcs" "commit"]]

  :url "https://github.com/simon-brooke/the-great-game")
