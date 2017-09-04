(def project 'hangul-utils)
(def version "0.1.1-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src"}
          :source-paths #{"test"}
          :dependencies '[[org.clojure/clojure "1.9.0-alpha17" :scope "provided"]
                          [adzerk/boot-test "RELEASE" :scope "test"]
                          [adzerk/bootlaces "0.1.13" :scope "test"]])

(require '[adzerk.boot-test :refer [test]]
         '[adzerk.bootlaces :refer [bootlaces! build-jar push-repo]])

(task-options!
 pom {:project     project
      :version     version
      :description "For splitting and Korean unicode characters (syllables, jaeum, moeum) in Clojure"
      :url         "http://github.com/sooheon/hangul-utils"
      :scm         {:url "https://github.com/sooheon/hangul-utils"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}})

(bootlaces! version)

(deftask build
  "Build and install the project locally."
  []
  (comp (pom) (jar) (install)))

(require '[adzerk.boot-test :refer [test]])
