(ns clj-rapportive.core
  (:require [org.httpkit.client :as http]
            [clojure.data.json :as json]
            )
  (:gen-class :main true)
  )

(defn generate-rand-seq
  ([r size offset]
    (take size (repeatedly #(+ offset (rand-int r)))))
  ([r size]
    (generate-rand-seq size 0)))

(defn generate-random-string [size]
  (apply str
    (map #(char %) (generate-rand-seq 26 size 65))))

(defn- sync-http-get-call [url & options]
    @(http/get url (first options))
)

(defn- get-session-token [rapportive-user]
  (let [random-email (format "%s@gmail.com" (generate-random-string 7))
        {:keys [body error]} (sync-http-get-call "https://rapportive.com/login_status"
                                        { :query-params { :user_email rapportive-user}})]
        (if-not error
          ((json/read-str body) "session_token")
          (println error)
        )
    )
)

(defn- get-contact-info [session-token contact-email]
  (if-not (nil? contact-email)
    (let [{:keys [body error status]} @(http/get
                                        (format "https://profiles.rapportive.com/contacts/email/%s" contact-email)
                                        { :headers {"X-Session-Token" session-token}})]
      (if-not error
          body
         (println error))
    )
  )
)

(defn- print-usage []
  (println "Usage: java -jar clj-rapportive-0.1.0-SNAPSHOT-standalone.jar <rapportive-useremail> <email>")
  )

(defn -main[& args]
  (if (< (count args) 2)
    (print-usage)
    (println (get-contact-info (get-session-token (first args)) (second args))))
 )
