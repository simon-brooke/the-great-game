(ns cc.journeyman.the-great-game.lore.digester
  (:require [org.clojurenlp.core :refer [pos-tag sentenize split-sentences 
                                         tag-ner tokenize word
                                         ]]))
