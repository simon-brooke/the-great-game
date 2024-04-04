(ns cc.journeyman.the-great-game.time
  (:require [clojure.string :as s]))

(def game-start-time
  "The start time of this run."
  (System/currentTimeMillis))

(def ^:const game-day-length
  "The Java clock advances in milliseconds, which is fine.
  But we need game-days to be shorter than real world days.
  A Witcher 3 game day is 1 hour 36 minutes, or 96 minutes, which is
  presumably researched. Round it up to 100 minutes for easier
  calculation."
  (* 100          ;; minutes per game day
     60           ;; seconds per minute
     1000))       ;; milliseconds per second

(defn now
  "For now, we'll use Java timestamp for time; ultimately, we need a
  concept of game-time which allows us to drive day/night cycle, seasons,
  et cetera, but what matters about time is that it is a value which
  increases."
  []
  (System/currentTimeMillis))

(def ^:const canonical-ordering-of-houses
  "The canonical ordering of religious houses."
  [:eye
   :foot
   :nose
   :hand
   :ear
   :mouth
   :stomach
   :furrow
   :plough])

(def ^:const days-of-week
  "The eight-day week of the game world. This differs from the canonical
  ordering of houses in that it omits the eye."
  (rest canonical-ordering-of-houses))

(def ^:const days-in-week
  "This world has an eight day week."
  (count days-of-week))

(def ^:const seasons-of-year
  "The ordering of seasons in the year is different from the canonical
  ordering of the houses, for reasons of the agricultural cycle."
  [:foot
   :nose
   :hand
   :ear
   :mouth
   :stomach
   :plough
   :furrow
   :eye])

(def ^:const seasons-in-year
  "Nine seasons in a year, one for each house (although the order is
  different."
  (count seasons-of-year))

(def ^:const weeks-of-season
  "To fit nine seasons of eight day weeks into 365 days, each must be of
  five weeks."
  [:first :second :third :fourth :fifth])

(def ^:const weeks-in-season
  "To fit nine seasons of eight day weeks into 365 days, each must be of
  five weeks."
  (count weeks-of-season))

(def ^:const days-in-season
  (* weeks-in-season days-in-week))

(defn game-time
  "With no arguments, the current game time. If a Java `timestamp` value is
  passed (as a `long`), the game time represented by that value."
  ([] (game-time (now)))
  ([timestamp]
   (- timestamp game-start-time)))

(defmacro day-of-year
  "The day of the year represented by this `game-time`, ignoring leap years."
  [game-time]
  `(mod (long (/ ~game-time game-day-length)) 365))

(def waiting-day?
  "Does this `game-time` represent a waiting day?"
  (memoize
    ;; we're likely to call this several times in quick succession on the
    ;; same timestamp
    (fn [game-time]
        (>=
          (day-of-year game-time)
          (* seasons-in-year weeks-in-season days-in-week)))))

(defn day
  "Day of the eight-day week represented by this `game-time`."
  [game-time]
  (let [day-of-week (mod (day-of-year game-time) days-in-week)]
    (if (waiting-day? game-time)
      (nth weeks-of-season day-of-week)
      (nth days-of-week day-of-week))))

(defn week
  "Week of season represented by this `game-time`."
  [game-time]
  (let [day-of-season (mod (day-of-year game-time) days-in-season)
        week (/ day-of-season days-in-week)]
    (if (waiting-day? game-time)
      :waiting
      (nth weeks-of-season week))))

(defn season
  [game-time]
  (let [season (int (/ (day-of-year game-time) days-in-season))]
    (if (waiting-day? game-time)
      :waiting
      (nth seasons-of-year season))))

(defn date-string
  "Return a correctly formatted date for this `game-time` in the calendar of
  the Great Place."
  [game-time]
  (s/join
    " "
    (if
      (waiting-day? game-time)
      [(s/capitalize
         (name
           (nth
             weeks-of-season
             (mod (day-of-year game-time) days-in-week))))
       "waiting day"]
      [(s/capitalize (name (week game-time)))
       (s/capitalize (name (day game-time)))
       "of the"
       (s/capitalize (name (season game-time)))])))



