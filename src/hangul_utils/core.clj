(ns hangul-utils.core
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [clojure.spec.alpha :as s]))

;; The unicode codepoints for Hangul syllables are determined using the
;; following equation:

(defn korean-syllable?
  [c]
  (<= 0xAC00 (int c) 0xD7A3))

(def ^:private initial-jaeums
  [\ㄱ \ㄲ \ㄴ \ㄷ \ㄸ \ㄹ \ㅁ \ㅂ \ㅃ \ㅅ \ㅆ \ㅇ \ㅈ \ㅉ \ㅊ \ㅋ \ㅌ \ㅍ \ㅎ])

(def ^:private initial? (set initial-jaeums))

(def ^:private medial-moeums
  [\ㅏ \ㅐ \ㅑ \ㅒ \ㅓ \ㅔ \ㅕ \ㅖ \ㅗ \ㅘ \ㅙ \ㅚ \ㅛ \ㅜ \ㅝ \ㅞ \ㅟ \ㅠ \ㅡ
   \ㅢ \ㅣ])

(def ^:private medial? (set medial-moeums))

(def ^:private final-jaeums
  [nil \ㄱ \ㄲ \ㄳ \ㄴ \ㄵ \ㄶ \ㄷ \ㄹ \ㄺ \ㄻ \ㄼ \ㄽ \ㄾ \ㄿ \ㅀ \ㅁ \ㅂ \ㅄ
   \ㅅ \ㅆ \ㅇ \ㅈ \ㅊ \ㅋ \ㅌ \ㅍ \ㅎ])

(def ^:private final? (set final-jaeums))

(defn deconstruct
  "Takes a single Korean syllable char and deconstructs it into its constituent
  jamo char: 강 => [ㄱ ㅏ ㅇ]"
  [c]
  (let [codepoint (int c)
        diff (- codepoint 0xAC00)
        i (Math/floorDiv diff 588)
        m (Math/floorDiv (rem diff 588) 28)
        f (rem diff 28)]
    (if (neg? diff)
      [(char codepoint)]
      (->> (map (partial get)
                [initial-jaeums medial-moeums final-jaeums]
                [i m f])
           (remove nil?)
           vec))))

(defn construct
  "Takes a vector of valid jamo chars and constructs a syllable char."
  [[i m f]]
  (if (and (not m) (not f))
    i
    (char
     (+ 0xAC00
        (* 28 21 (.indexOf initial-jaeums i))
        (* 28 (.indexOf medial-moeums m))
        (.indexOf final-jaeums f)))))

(defn deconstruct-str
  "Splits a string of Korean syllables into a sequence of Korean jamo"
  [s]
  (reduce (fn [coll c]
            (if (korean-syllable? c)
              (conj coll (deconstruct c))
              (conj coll [c])))
          []
          s))

(defn construct-str
  "Takes a collection of vectors of jamo and joins them into a string of syllables.
  [[ㄱ ㅏ ㅇ] [ㅅ ㅏ ㄴ]] => \"강산\""
  [c]
  (apply str (map construct c)))

(defn alphabetize
  "Takes a Korean text string and returns a string of the deconstructed
  alphabet. Ignores (passes along) non-valid Korean characters."
  [s]
  (apply str (flatten (deconstruct-str s))))

(defn syllabize
  "Takes a string of Korean alphabets, and reconstructs Korean text. The initial
  value for the reduce fn is a vector containing the accumulated result, the
  current syllable under consideration, and the most recent consonant in
  limbo (to be classified as initial or final).

  Each new char read in from the input string is either added to the current
  syllable vector or sent into limbo, and can trigger the syllable to be conj'd
  onto the accumulator once it's fully constructed.

  The cond branches could use more cleanup."
  [s]
  (let [[acc syl limbo]
        (reduce
         (fn [[acc syl limbo] c]
           (cond
             (and (empty? syl) (initial? c))     [acc [c] nil]
             (and (= 1 (count syl)) (not limbo)) (if (medial? c) [acc (conj syl c) nil]
                                                     [(conj acc syl) [c] nil])
             (and (not limbo) (final? c))        [acc syl c]
             (and limbo (initial? c))            [(conj acc (conj syl limbo)) [c] nil]
             (and limbo (medial? c))             [(conj acc syl) [limbo c] nil]
             :else                               [(conj acc (conj syl limbo) [c]) [] nil]))
         [[] [] nil]
         s)]
    (construct-str (conj acc (conj syl limbo)))))
