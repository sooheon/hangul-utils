(ns hangul-utils.core-test
  (:require [clojure.test :refer :all]
            [hangul-utils.core :refer :all]))

(deftest syllable-recognition
  (is (korean-syllable? \힣))
  (is (korean-syllable? \가)))

(deftest deconstructing
  (is (= (deconstruct \한) '(\ㅎ \ㅏ \ㄴ)))
  (is (= (deconstruct \가) '(\ㄱ \ㅏ)))
  (is (= (deconstruct \ㄱ) '(\ㄱ)))

  (is (= (deconstruct-str "안녕하세요")
         '((\ㅇ \ㅏ \ㄴ) (\ㄴ \ㅕ \ㅇ) (\ㅎ \ㅏ) (\ㅅ \ㅔ) (\ㅇ \ㅛ)))))

(deftest constructing
  (is (= \강 (construct [\ㄱ \ㅏ \ㅇ])))
  (is (= \가 (construct [\ㄱ \ㅏ])))
  (is (= (construct-str (deconstruct-str "반갑습니다"))
         "반갑습니다")))

(deftest string-to-string-transformation
  (is (= "ㅇㅏㄴ" (alphabetize "안")))
  (is (= "ㅇㅏㄴㄴㅕㅇㅎㅏㅅㅔㅇㅛ" (alphabetize "안녕하세요")))
  (is (= "안녕하세요" (syllabize "ㅇㅏㄴㄴㅕㅇㅎㅏㅅㅔㅇㅛ")))
  (is (= "안" (syllabize "ㅇㅏㄴ")))
  (is (= (alphabetize "안 녕 하 십 니 까") "ㅇㅏㄴ ㄴㅕㅇ ㅎㅏ ㅅㅣㅂ ㄴㅣ ㄲㅏ"))
  (is (= (alphabetize "안 녕!111") "ㅇㅏㄴ ㄴㅕㅇ!111"))
  (is (= (alphabetize "아ㅏㅏㅏㅏㅏ이고") "ㅇㅏㅏㅏㅏㅏㅏㅇㅣㄱㅗ"))
  (is (= (syllabize "ㅇㅏㅏㅏㅏㅏㅏㅇㅣㄱㅗ") "아ㅏㅏㅏㅏㅏ이고")))
