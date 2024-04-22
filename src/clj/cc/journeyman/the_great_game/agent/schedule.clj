(ns cc.journeyman.the-great-game.agent.schedule
  "Schedules of plans for actors in the game, in order that they may have
   daily and seasonal patterns of behaviour.")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;
;;;; Schedules: things agents plan to do.
;;;;
;;;; This program is free software; you can redistribute it and/or
;;;; modify it under the terms of the GNU General Public License
;;;; as published by the Free Software Foundation; either version 2
;;;; of the License, or (at your option) any later version.
;;;;
;;;; This program is distributed in the hope that it will be useful,
;;;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;;;; GNU General Public License for more details.
;;;;
;;;; You should have received a copy of the GNU General Public License
;;;; along with this program; if not, write to the Free Software
;;;; Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
;;;; USA.
;;;;
;;;; SPDX-FileCopyrightText: 2024 Simon Brooke <simon@journeyman.cc>
;;;; SPDX-License-Identifier: GPL-2.0-or-later
;;;;
;;;; Copyright (C) 2024 Simon Brooke
;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; TODO: I don't have a good handle yet on when a new scheduled task can
;; interrupt an existing scheduled task. It's highly undesirable that
;; uncompleted scheduled tasks should be left on the queue. The simplest
;; solution is to arrange the schedule such that, under notmal circumstances,
;; no scheduled task will interrupt another. But if a scheduled task is 
;; interrupted by an attack, say, or a conversation, and then continued, 
;; there's a chance of overrunning the start of the next.
;;
;; Perhaps I need to give scheduled tasks the equivalent of a watchdog timer,
;; but that makes them much more sophisticated objects than I wanted them to 
;; be.

;; NOTE: this assumes that a world contains a key `:time` whose values are 
;; a map with at least the keys 
;; 1. `:day`, whose value is an integer representing the current day of the
;;    year, and
;; 2. `minute`, whose value is an integer representing the current minute of
;;    the day.
;; it probably also includes a `:year`, but that isn't needed here. 

;; (def default-human-schedule
;;   "A sample schedule for a human actor. This assumes that each of:
;;    1. `find-food`;
;;    2. `goto-market`;
;;    3. `help-with-harvest`;
;;    3. `perform-craft`
;;    4. `sleep-until-dawn`
;;    Are plans, which is to say, functions of three arguments, an `actor`,
;;    a `world` and a `circle`."
;;   {:annual {32 {:daily {1020 (fn [a w c] (attend-festival a w c :imbolc))}}
;;             122 {:daily {1020 (fn [a w c] (attend-festival a w c :bealtaine))}}
;;             210 {:daily {480 help-with-harvest}}
;;             211 {:daily {480 help-with-harvest}}
;;             212 {:daily {480 help-with-harvest}}
;;             213 {:daily {480 help-with-harvest}}
;;             214 {:daily {480 help-with-harvest
;;                          1020 (fn [a w c](attend-festival a w c :lughnasadh))}}
;;             306 {:daily {1020 (fn [a w c] (attend-festival a w c :samhain))}}}
;;    :daily {420 find-food
;;            720 find-food
;;            1020 find-food
;;            1320 sleep-until-dawn}})

(defn plan-scheduled-action [actor world circle]
  "Return the scheduled plan for the current time in this `world` from the
   schedule of this `actor`, provided the `actor is in an appropriate `circle`"
  (case circle
    (:active :pending) (let [s (:schedule actor)
                             d (or (:daily (-> s :annual (-> world :time :day)))
                                   (:daily s))]
                         (when d (d (-> world :time :minute))))))