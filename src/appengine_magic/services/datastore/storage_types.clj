(ns appengine-magic.services.datastore.storage-types
  (:import 
   [com.google.appengine.api.datastore Text Link Category
    Email GeoPt Blob ShortBlob]))

(def text {:enc #(Text. (str %))
           :dec #(cond
                  (nil? %) nil
                  (string? %) %
                  (instance? Text %) (.getValue %)
                  :else (throw (IllegalArgumentException. (format "unexpected value for a text field: %s" %))))})

(def category {:enc #(Category. (str %))
               :dec #(.getCategory %)})

(def email {:enc #(Email. (str %))
            :dec #(.getEmail %)})

(def geo-point {:enc (fn [x]
                       (if (and (map? x) (:latitude x) (:longitude x))
                         (GeoPt. (float (:latitude x)) (float (:longitude x)))
                         (throw (IllegalArgumentException. (format "invalid value for geo-point, must be a map with :lat and :long keys: %s" x)))))
                :dec (fn [^GeoPt p]
                       {:latitude (.getLatitude p)
                        :longitude (.getLongitude p)})})

(def link {:enc #(Link. (str %))
           :dec #(.getValue %)})


