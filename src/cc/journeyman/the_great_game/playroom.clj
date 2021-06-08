(ns cc.journeyman.the-great-game.playroom
  (require [jme-clj.core :refer :all])
  (import [com.jme3.math ColorRGBA]))

;; At present this file is just somewhere to play around with jme-clj examples

(defn init []
  (let [cube (geo "jMonkey cube" (box 1 1 1))
        mat  (unshaded-mat)]
    (set* mat :texture "ColorMap" (load-texture "textures/Monkey.jpg"))
    (set* cube :material mat)
    (add-to-root cube)
    {:cube cube}))

;; Let's create simple-update fn with no body for now.
 (defn simple-update [tpf]
   (let [{:keys [cube]} (get-state)]
     (rotate cube 0 (* 2 tpf) 0)))


;; Kills the running app var and closes its window.
;; (unbind-app #'app)

;; We define the `app` var.
(defsimpleapp app
               :opts {:show-settings?       false
                      :pause-on-lost-focus? false
                      :settings             {:title          "My JME Game"
                                             :load-defaults? true
                                             :frame-rate     60
                                             :width          800
                                             :height         600}}
               :init init
               :update simple-update)

(start app)

;; Reinitialises the running app
;;(run app
;;     (re-init init))
 
 ;; By default, there is a Fly Camera attached to the app that you can control with W, A, S and D keys.
 ;; Let's increase its movement speed. Now, you fly faster :)
 (run app
      (set* (fly-cam) :move-speed 15))


 ;; Updates the app 
(run app
     (let [{:keys [cube]} (get-state)]
       (set* cube :local-translation (add (get* cube :local-translation) 1 1 1))))

  ;; Updates the app adding a second cube
(run app
      (let [cube (geo "jMonkey cube" (box 1 1 1))
            mat  (unshaded-mat)]
        (set* mat :texture "ColorMap" (load-texture "textures/Monkey.jpg"))
        (setc cube
              :material mat
              :local-translation [-3 0 0])
        (add-to-root cube)
        (set-state :cube2 cube)))
 
 ;; We added the new cube, but it's not rotating. We need to update the simple-update fn.
 (defn simple-update [tpf]
   (let [{:keys [cube cube2]} (get-state)]
     (rotate cube 0 (* 2 tpf) 0)
     (rotate cube2 0 (* 2 tpf) 0)))
